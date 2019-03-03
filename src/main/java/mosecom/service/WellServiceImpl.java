package mosecom.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mosecom.dao.ConstructionTypeRepository;
import mosecom.dao.DocumentTypeRepository;
import mosecom.dao.WellRepository;
import mosecom.dao.WellsDocumentRepository;
import mosecom.dto.WellFullProjection;
import mosecom.dto.WellProjection;
import mosecom.dto.WellsConstructionProjection;
import mosecom.model.ConstructionType;
import mosecom.model.Well;
import mosecom.model.WellsConstruction;
import mosecom.model.WellsDocument;


@Service
public class WellServiceImpl implements WellService {

	private static final int DEFAULT_DOCUMENT_TYPE_ID = 1;

	@Autowired
	private WellRepository wellRepository;

	@Autowired
	private WellsDocumentRepository wellsDocumentRepository;

	@Autowired
	private ConstructionTypeRepository constructionTypeRepository;

	@Autowired
	private DocumentTypeRepository documentTypeRepository;


    @Value("${upload.path}")
    private String uploadPath;

	@Override
	public List<WellProjection> getWellsList() {
		return wellRepository.findWellsList();
	}

	@Override
	public Well getWell(int id) {
		return wellRepository.findOne(id);
	}

	@Override
	public List<ConstructionType> getAllConstructionTypes() {
		return constructionTypeRepository.findAll();
	}

	@Override
	@Transactional
	public Well save(WellFullProjection dto, MultipartFile[] files) throws IllegalStateException, IOException {
		Well well;
		if (dto.getId() != null && dto.getId() > 0) {
			well = wellRepository.findOne(dto.getId());
		} else {
			well = new Well();
		}

		// переносим изменения в well
		well.setWellName(dto.getWellName());
		well.setWellCollar(dto.getWellCollar());
		well.setDrilledDate(dto.getDrilledDate());

		// удаляем все удаленные из интерфейса документы
		if (dto.getDocuments() == null) {
			well.getDocuments().clear();
		} else {
			Set<Integer> keepDocumentsIds = dto.getDocuments().stream().map(d -> d.getId()).collect(Collectors.toSet());
			well.getDocuments().removeIf(d -> !keepDocumentsIds.contains(d.getId()));
		}

		// все прикрепленные файлы добавляем в документы
		if(files != null) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
		            File uploadDir = new File(uploadPath + "/" + well.getId().toString());
		            if(!uploadDir.exists()) {
		                uploadDir.mkdirs();
		            }

		            //String newFilePath = UUID.randomUUID().toString();
		            file.transferTo(new File(uploadDir + "/" + file.getOriginalFilename()));
							//newFilePath));

		            WellsDocument doc = new WellsDocument();
		            doc.setWell(well);
		            doc.setDocumentType(documentTypeRepository.findOne(DEFAULT_DOCUMENT_TYPE_ID));
		            doc.setFileContentType(file.getContentType());

		            // TODO: оптимизировать эти поля
		            doc.setFileName(file.getOriginalFilename());
		            doc.setFilePath(uploadDir + "/");
							//+ file.getOriginalFilename()); // было newFilePath

					doc.setFileSize(file.getSize());

		            well.getDocuments().add(doc);
				}
			}
        }

		// переносим все изменения конструкций
		well.getConstructions().clear();
		if (dto.getConstructions() != null) {
			dto.getConstructions().stream().forEach(c -> well.getConstructions().add(convertWellConstruction(well, c)));
		}

		wellRepository.save(well);
		return well;
	}

	private WellsConstruction convertWellConstruction(Well well, WellsConstructionProjection dto) {
		WellsConstruction construction = new WellsConstruction();
		construction.setId(dto.getId());
		construction.setWell(well);
		construction.setConstructionType(constructionTypeRepository.findOne(dto.getConstructionTypeId()));
		construction.setDiameter(dto.getDiameter());
		construction.setDepthFrom(dto.getDepthFrom());
		construction.setDepthTo(dto.getDepthTo());
		return construction;
	}

	@Override
	public WellsDocument getWellDocument(int id) {
		return wellsDocumentRepository.findOne(id);
	}
}
