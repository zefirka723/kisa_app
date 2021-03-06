package mosecom.service.welldoc;

import mosecom.dao.*;
import mosecom.dictionaries.DocTypes;
import mosecom.dto.*;
import mosecom.model.*;
import mosecom.service.AttachmentServiceImpl;
import mosecom.service.licensereport.WaterDepthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class WellServiceImpl implements WellService {

    @Autowired
    private WellRepository wellRepository;

//    @Autowired
//    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentServiceImpl attachmentService;

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

    @Autowired
    private WaterDepthServiceImpl waterDepthService;

    @Autowired
    private WellsDepthRepository wellsDepthRepository;

    @Value("${upload.path}" + "PRIMARYDOCS/GW/Wells/")
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

    public List<Horisont> getAllHorisontsByOrder() {
        return horisontRepository.findAllByOrderNotNullOrderByOrderAsc();
    }

    @Override
    public List<MovedType> getAllMovedTypes() {
        return movedTypeRepository.findAll();
    }

    public Well prepareWellForWellDoc(int id) {
        Well well = wellRepository.getOne(id);
        if (well.getDrilledDate() != null) {
            well.setDepth(wellsDepthRepository.findOneByWellIdAndDate(well.getId(), well.getDrilledDate()));
        }
        return well;
    }


    @Transactional
    public Well save(WellFullProjection dto, MultipartFile[] files, DocTypes docType
    ) throws IllegalStateException, IOException, ParseException {
        Well well;
        if (dto.getId() != null && dto.getId() > 0) {
            well = wellRepository.getOne(dto.getId());
        } else {
            well = new Well();
        }

        // Переносим изменения в well
        well.setWellName(dto.getWellName());
        well.setWellCollar(dto.getWellCollar());
        well.setDrilledDate(dto.getDrilledDate());
        well.setMoved(dto.getMoved());

        // Делаем документ
        switch (docType) {
            case RECCARD:
                Reccard reccard = new Reccard();
                //reccard.setDocType(3001);
                reccard.setDocDate(dto.getReccard().getDocDate());
                if (well.getReccard() != null) {
                    reccard.setId(dto.getReccard().getId());
                }
                reccard.setWell(well);
                well.setReccard(reccard);
                break;

            case PASSPORT:
                Passport passport = new Passport();
                //passport.setDocType(3002);
                passport.setDocDate(dto.getPassport().getDocDate());
                if (well.getPassport() != null) {
                    passport.setId(dto.getPassport().getId());
                }
                passport.setWell(well);
                well.setPassport(passport);
                break;

            case DESCRIPTION:
                if (dto.getHorId() != null) {
                    well.setHorId(dto.getHorId()); // горизонт из блока "Водоносный горизонт"
                    waterDepthService.saveChanges(dto.getDepthsList(), dto.getId());
                } else { // выпиливаем значение из БД, если оно было очищено при редактировании
                    well.setHorId(null);
                    // Это работает, но пока выключено, потому что нет фронтовой валидации и можно случайно
                    // снести что-то полезное
//                    if (dto.getDepthsList() != null) {
//                        waterDepthService.clearWaterDepths(dto.getDepthsList());
//                    }
                }

                Description description = new Description();
                //description.setDocType(3007);
                description.setId(well.getDescription() != null ?
                        well.getDescription().getId() :
                        null);
                description.setWell(well);
                well.setDescription(description);
                break;
        }


        // пишем глубину

        /* Версия ДО решения искать глубину по дате:

        if (dto.getDepth().getWellDepth() != null) {
            WellsDepth depth = new WellsDepth();
            depth.setId(dto.getDepth().getId());
            depth.setWellDepth(dto.getDepth().getWellDepth());
            if (docType == DocTypes.PASSPORT) {
                depth.setDate(dto.getPassport().getDocDate());
            } else {
                depth.setDate(dto.getDrilledDate());
            }
            depth.setWell(well);
            well.setDepth(depth);
        }
        */

        /* Новая версия */
        if (dto.getDepth().getWellDepth() != null) {
            WellsDepth depth = new WellsDepth();
            depth.setId(dto.getDepth().getId());
            depth.setWellDepth(dto.getDepth().getWellDepth());
            depth.setDate(dto.getDrilledDate());
            depth.setWellId(well.getId());
            wellsDepthRepository.save(depth);
        }


        // удаляем все удаленные из интерфейса файлы
        //if (dto.getDocuments() == null) {
        if(dto.getAttachments() == null) {
            if (well.getAttachments() != null) {
                well.getAttachments().stream().forEach(a -> attachmentService.delete(a));
                well.getAttachments().clear();
            }
        } else {
            Set<Integer> keepDocumentsIds = dto.getAttachments().stream().map(d -> d.getId()).collect(Collectors.toSet());

            for (Attachment a: well.getAttachments()) {
                if (!keepDocumentsIds.contains(a.getId())) {
                    attachmentService.delete(a);
                }
            }
            well.getAttachments().removeIf(d -> !keepDocumentsIds.contains(d.getId()));
        }

        // все прикрепленные файлы добавляем в документы
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // Настройка пути под PROD
                    //TODO: переделать это и вообще все типы
                    String folderByType;
                    switch (docType) {
                        case RECCARD:
                            folderByType = "RECCARD";
                            break;
                        case PASSPORT:
                            folderByType = "PASSPORT";
                            break;
                        case DESCRIPTION:
                            folderByType = "DESCRIPTION";
                            break;
                        default:
                            folderByType = "OTHER";
                    }

                    java.io.File uploadDir = new java.io.File(uploadPath + "/" + dto.getId().toString() + "/" + folderByType);

                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    //String newFilePath = UUID.randomUUID().toString();
                    file.transferTo(new java.io.File(uploadDir + "/" + file.getOriginalFilename()));
                    //newFilePath));

                    Attachment attachment = new Attachment();
                    attachment.setWell(well);
                    attachment.setDocumentType(documentTypeRepository.getOne(docType.getId())); //documentTypeRepository.getOne(DEFAULT_DOCUMENT_TYPE_ID));
                    attachment.setFileContentType(file.getContentType());
                    attachment.setFileContentType(file.getContentType());

                    // TODO: оптимизировать эти поля
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setFilePath(uploadDir + "/");
                    //+ file.getOriginalFilename()); // было newFilePath
                    attachment.setFileSize(file.getSize());
                    attachment.setDocId(docType.getId());

                    well.getAttachments().add(attachment);
                }
            }
        }

        if (docType == DocTypes.RECCARD || docType == DocTypes.PASSPORT) {
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
        if (well.getGeologies() != null) {
            well.getGeologies().clear();
        }
        if (dto.getGeologies() != null) {
            dto.getGeologies().stream().forEach(g -> well.getGeologies().add(convertWellGeology(well, g)));
        }


        wellRepository.save(well);
        return well;
}


    private WellssStressTest convertStressTests(Well well, WellsStressTestProjection dto) {
        if (dto.getId() == null && dto.getDepression() == null) {
            return null;
        } else {
            WellssStressTest stressTest = new WellssStressTest();
            stressTest.setId(dto.getId());
            stressTest.setWell(well);
            stressTest.setDepression(dto.getDepression());
            stressTest.setFlowRate(dto.getFlowRate() * 86.4f);
            stressTest.setStressDate(dto.getStressDate());
            stressTest.setWaterDepth(dto.getWaterDepth());
            return stressTest;
        }
    }

    private WellsConstruction convertWellConstruction(Well well, WellsConstructionProjection dto) {
        if (dto.getId() == null && dto.getDepthFrom() == null) {
            return null;
        } else {
            WellsConstruction construction = new WellsConstruction();
            construction.setId(dto.getId());
            construction.setWell(well);
            construction.setConstructionType(constructionTypeRepository.getOne(dto.getConstructionTypeId()));
            construction.setDiametr(diametrRepository.getOne(dto.getDiametrId()));
            construction.setDepthFrom(dto.getDepthFrom());
            construction.setDepthTo(dto.getDepthTo());
            return construction;
        }
    }

    private WellsGeology convertWellGeology(Well well, WellsGeologyProjection dto) {
        if (dto.getId() == null && dto.getHorisontId() == null) {
            return null;
        } else {
            WellsGeology geology = new WellsGeology();
            geology.setId(dto.getId());
            geology.setWell(well);
            geology.setHorisont(horisontRepository.getOne(dto.getHorisontId()));
            geology.setBotElev(dto.getBotElev());
            return geology;
        }
    }


    @Override
    public Attachment getWellDocument(int id) {
        return attachmentService.getOne(id);
    }
}