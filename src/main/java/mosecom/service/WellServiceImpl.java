package mosecom.service;

import mosecom.dao.*;
import mosecom.dto.*;
import mosecom.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class WellServiceImpl implements WellService {

    private static final int DEFAULT_DOCUMENT_TYPE_ID = 3001;

    @Autowired
    private WellRepository wellRepository;

    @Autowired
    private WellsDocumentRepository wellsDocumentRepository;

    @Autowired
    private ConstructionTypeRepository constructionTypeRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private DiametrRepository diametrRepository;

    @Autowired
    private HorisontRepository horisontRepository;

    @Autowired
    private MovedTypeRepository movedTypeRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public List<WellProjection> getWellsList() {
        return wellRepository.findWellsList();
    }

    @Override
    public Well getWell(int id) {
        return wellRepository.getOne(id);
    }

    @Override
    public List<ConstructionType> getAllConstructionTypes() {
        return constructionTypeRepository.findAll();
    }

    @Override
    public List<Diametr> getAllDiametrs() {
        return diametrRepository.findAll();
    }

    @Override
    public List<Horisont> getAllHorisonts() {
        return horisontRepository.findAll();
    }

    @Override
    public List<MovedType> getAllMovedTypes() { return movedTypeRepository.findAll(); }

    @Override
    @Transactional
    public Well save(WellFullProjection dto, MultipartFile[] files, int cardType) throws IllegalStateException, IOException {
        Well well;
        if (dto.getId() != null && dto.getId() > 0) {
            well = wellRepository.getOne(dto.getId());
        } else {
            well = new Well();
        }


        // переносим изменения в well
        well.setWellName(dto.getWellName());
        well.setWellCollar(dto.getWellCollar());
        well.setDrilledDate(dto.getDrilledDate());

        // Делаем документ
        switch (cardType) {
            case 3001: // Учётка
                WellsDoc wellsDoc = new WellsDoc();
                wellsDoc.setDocDate(dto.getWellDoc().getDocDate());
                wellsDoc.setDocType(cardType);
                wellsDoc.setId(well.getWellDoc() != null ?
                        dto.getWellDoc().getId() :
                        null);
                wellsDoc.setWell(well);
                well.setWellDoc(wellsDoc);
                break;

            case 3002: // Паспорт
                WellsPassport wellsPassport = new WellsPassport();
                wellsPassport.setDocDate(dto.getWellPassport().getDocDate());
                wellsPassport.setDocType(cardType);
                wellsPassport.setId(well.getWellPassport() != null ?
                        dto.getWellPassport().getId() :
                        null);
                wellsPassport.setWell(well);
                well.setWellPassport(wellsPassport);
                break;

            case 3007: // Геол. описание
                WellsDescription wellsDescription = new WellsDescription();
                wellsDescription.setDocDate(dto.getWellDescription().getDocDate());
                wellsDescription.setDocType(cardType);
                wellsDescription.setId(well.getWellsDescription() != null ?
                        dto.getWellDescription().getId() :
                        null);
                wellsDescription.setWell(well);
                well.setWellsDescription(wellsDescription);
        }


        // пишем глубину
        if(dto.getDepth().getWellDepth() != null) {
            WellsDepth depth = new WellsDepth();
            depth.setId(dto.getDepth().getId());
            depth.setWellDepth(dto.getDepth().getWellDepth());
            if(cardType == 3002) {
                depth.setDate(dto.getWellPassport().getDocDate());
            }
            else {
                depth.setDate(dto.getDrilledDate());
            }
            depth.setWell(well);
            well.setDepth(depth);
        }

        // удаляем все удаленные из интерфейса документы
        if (dto.getDocuments() == null) {
            if(well.getDocuments() != null) {
                well.getDocuments().clear();
            }
        } else {
            Set<Integer> keepDocumentsIds = dto.getDocuments().stream().map(d -> d.getId()).collect(Collectors.toSet());
            well.getDocuments().removeIf(d -> !keepDocumentsIds.contains(d.getId()));
        }

        // все прикрепленные файлы добавляем в документы
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // Настройка пути под PROD
                    File uploadDir = new File(uploadPath + "/" + dto.getId().toString()+"/RegistationCard");
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    //String newFilePath = UUID.randomUUID().toString();
                    file.transferTo(new File(uploadDir + "/" + file.getOriginalFilename()));
                    //newFilePath));

                    WellsDocument doc = new WellsDocument();
                    doc.setWell(well);
                    doc.setDocumentType(documentTypeRepository.getOne(DEFAULT_DOCUMENT_TYPE_ID));
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

        if(cardType == 3001 || cardType == 3002) {
            // переносим все изменения конструкций
            if (well.getDrilledDate() != null) {
                well.getConstructions().clear();
            }
            if (dto.getConstructions() != null) {
                dto.getConstructions().stream().forEach(c -> {
                    well.getConstructions().add(convertWellConstruction(well, c));
                });
            }

            // переносим все отчкачули
            if (well.getStressTests() != null) {
                well.getStressTests().clear();
            }
            if (dto.getStressTests() != null) {
                dto.getStressTests().stream().forEach(s -> well.getStressTests().add(convertStressTests(well, s)));
            }
        }



        // переносим все изменения геологии
       if(well.getGeologies() != null) {
           well.getGeologies().clear();
       }
        if (dto.getGeologies() != null) {
            dto.getGeologies().stream().forEach(g -> well.getGeologies().add(convertWellGeology(well, g)));
        }


//        }// переносим все изменения глубин
//        if (dto.getDepth() != null) {
//            WellsDepth savingDepth = new WellsDepth();
//            if (well.getDepth() != null) {
//                savingDepth = well.getDepth();
//            }
//            WellsDepthProjection depth = dto.getDepth();
//            savingDepth.setWellDepth(depth.getWellDepth());
//            savingDepth.setDate(depth.getDate());
//            savingDepth.setWell(well);
//            well.setDepth(savingDepth);
//        }//
//



        wellRepository.save(well);
        return well;
    }

    private WellsDoc convertWellDoc(Well well, WellsDocProjection dto, int cardType) {
        WellsDoc doc = new WellsDoc();
        doc.setId(dto.getId());
        doc.setDocType(cardType);
        doc.setWell(well);
        return doc;
    }


    private WellssStressTest convertStressTests(Well well, WellsStressTestProjection dto) {
        WellssStressTest stressTest = new WellssStressTest();
        stressTest.setId(dto.getId());
        stressTest.setWell(well);
        stressTest.setDepression(dto.getDepression());
        stressTest.setFlowRate(dto.getFlowRate() * 86.4f);
        stressTest.setStressDate(dto.getStressDate());
        stressTest.setWaterDepth(dto.getWaterDepth());
        return stressTest;
    }

    private WellsConstruction convertWellConstruction(Well well, WellsConstructionProjection dto) {
        WellsConstruction construction = new WellsConstruction();
        construction.setId(dto.getId());
        construction.setWell(well);
        construction.setConstructionType(constructionTypeRepository.getOne(dto.getConstructionTypeId()));
        construction.setDiametr(diametrRepository.getOne(dto.getDiametrId()));
        construction.setDepthFrom(dto.getDepthFrom());
        construction.setDepthTo(dto.getDepthTo());
        return construction;
    }


    private WellsGeology convertWellGeology(Well well, WellsGeologyProjection dto) {
        WellsGeology geology = new WellsGeology();
        geology.setId(dto.getId());
        geology.setWell(well);
        geology.setHorisont(horisontRepository.getOne(dto.getHorisontId()));
        geology.setBotElev(dto.getBotElev());
        return geology;
    }


    @Override
    public WellsDocument getWellDocument(int id) {
        return wellsDocumentRepository.getOne(id);
    }
}

//    private WellsDepth convertWellDepths(Well well, WellsDepthProjection dto) {
//        WellsDepth depth = new WellsDepth();
//        depth.setId(dto.getId());
//        depth.setWell(well);
//        depth.setDate(dto.getDate());
//        depth.setWellDepth(dto.getWellDepth());
//        return depth;
//    }



