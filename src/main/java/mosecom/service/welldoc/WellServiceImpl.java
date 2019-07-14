package mosecom.service.welldoc;

import mosecom.dao.*;
import mosecom.dictionaries.DocTypes;
import mosecom.dto.*;
import mosecom.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
    private AttachmentRepository attachmentRepository;

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
    public List<MovedType> getAllMovedTypes() {
        return movedTypeRepository.findAll();
    }

    @Override
    @Transactional
    public Well save(WellFullProjection dto, MultipartFile[] files, int docType) throws IllegalStateException, IOException {
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

        // Делаем документ
        switch (docType) {
            case 3001:
                Reccard reccard = new Reccard();
                reccard.setDocType(3001);
                reccard.setDocDate(dto.getReccard().getDocDate());
                if (well.getReccard() != null) {
                    reccard.setId(//well.getReccard() != null ?
                            dto.getReccard().getId());
                    //: null);
                }
                reccard.setWell(well);
                well.setReccard(reccard);
                break;

            case 3002:
                Passport passport = new Passport();
                passport.setDocType(3002);
                passport.setDocDate(dto.getPassport().getDocDate());
                if (well.getPassport() != null) {
                    passport.setId(//well.getPassport() != null ?
                            dto.getPassport().getId());
                    //: null);
                }
                passport.setWell(well);
                well.setPassport(passport);
                break;

            case 3007:
                Description description = new Description();
                description.setDocType(3007);
//                description.setDocDate(dto.getDescription().getDocDate());
                description.setId(well.getDescription() != null ?
                        well.getDescription().getId() :
                        null);
//                if (well.getDescription() != null) {
//                    description.setId(dto.getDescription().getId());
//                }
                description.setWell(well);
                well.setDescription(description);
                break;
        }

//        switch (dto.getWellDoc().getDocType()) {
//            case 3001: // Учётка
//                WellsDoc wellsDoc = new WellsDoc();
//                wellsDoc.setDocDate(dto.getWellDoc().getDocDate());
//                wellsDoc.setDocType(3001);
//                wellsDoc.setId(well.getWellDoc() != null ?
//                        dto.getWellDoc().getId() :
//                        null);
//                wellsDoc.setWell(well);
//                well.getWellsDocList().add(wellsDoc);
//                break;

//            case 3002: // Паспорт
//                Passport wellsPassport = new Passport();
//                wellsPassport.setDocDate(dto.getWellPassport().getDocDate());
//                wellsPassport.setDocType(cardType);
//                wellsPassport.setId(well.getWellPassport() != null ?
//                        dto.getWellPassport().getId() :
//                        null);
//                wellsPassport.setWell(well);
//                well.setWellPassport(wellsPassport);
//                break;
//
//            case 3007: // Геол. описание
//                Description wellsDescription = new Description();
//                wellsDescription.setDocDate(dto.getWellDescription().getDocDate());
//                wellsDescription.setDocType(cardType);
//                wellsDescription.setId(well.getWellsDescription() != null ?
//                        dto.getWellDescription().getId() :
//                        null);
//                wellsDescription.setWell(well);
//                well.setWellsDescription(wellsDescription);
        //       }


        // пишем глубину
        if (dto.getDepth().getWellDepth() != null) {
            WellsDepth depth = new WellsDepth();
            depth.setId(dto.getDepth().getId());
            depth.setWellDepth(dto.getDepth().getWellDepth());
            if (docType == 3002) {
                depth.setDate(dto.getPassport().getDocDate());
            } else {
                depth.setDate(dto.getDrilledDate());
            }
            depth.setWell(well);
            well.setDepth(depth);
        }

        // удаляем все удаленные из интерфейса файлы
        if (dto.getDocuments() == null) {
            if (well.getAttachments() != null) {
                well.getAttachments().clear();
            }
        } else {
            Set<Integer> keepDocumentsIds = dto.getDocuments().stream().map(d -> d.getId()).collect(Collectors.toSet());
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
                        case 3001:
                            folderByType = "RECCARD";
                            break;
                        case 3002:
                            folderByType = "PASSPORT";
                            break;
                        case 3007:
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
                    attachment.setDocumentType(documentTypeRepository.getOne(docType)); //documentTypeRepository.getOne(DEFAULT_DOCUMENT_TYPE_ID));
                    attachment.setFileContentType(file.getContentType());
                    attachment.setFileContentType(file.getContentType());

                    // TODO: оптимизировать эти поля
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setFilePath(uploadDir + "/");
                    //+ file.getOriginalFilename()); // было newFilePath
                    attachment.setFileSize(file.getSize());
                    //           attachment.setDocumentType(3001);
                    attachment.setDocId(docType);

                    well.getAttachments().add(attachment);
                }
            }
        }

        if (docType == 3001 || docType == 3002) {
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

    // НОВОЕ СОХРАНЕНИЕ
    @Transactional
    public Well save(WellFullProjection dto, MultipartFile[] files, DocTypes docType) throws IllegalStateException, IOException {
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

        // Делаем документ
        switch (docType) {
            case RECCARD:
                Reccard reccard = new Reccard();
                reccard.setDocType(3001);
                reccard.setDocDate(dto.getReccard().getDocDate());
                if (well.getReccard() != null) {
                    reccard.setId(//well.getReccard() != null ?
                            dto.getReccard().getId());
                    //: null);
                }
                reccard.setWell(well);
                well.setReccard(reccard);
                break;

            case PASSPORT:
                Passport passport = new Passport();
                passport.setDocType(3002);
                passport.setDocDate(dto.getPassport().getDocDate());
                if (well.getPassport() != null) {
                    passport.setId(//well.getPassport() != null ?
                            dto.getPassport().getId());
                    //: null);
                }
                passport.setWell(well);
                well.setPassport(passport);
                break;

            case DESCRIPTION:
                Description description = new Description();
                description.setDocType(3007);
//                description.setDocDate(dto.getDescription().getDocDate());
                description.setId(well.getDescription() != null ?
                        well.getDescription().getId() :
                        null);
//                if (well.getDescription() != null) {
//                    description.setId(dto.getDescription().getId());
//                }
                description.setWell(well);
                well.setDescription(description);
                break;
        }


        // пишем глубину
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

        // удаляем все удаленные из интерфейса файлы
        if (dto.getDocuments() == null) {
            if (well.getAttachments() != null) {
                well.getAttachments().clear();
            }
        } else {
            Set<Integer> keepDocumentsIds = dto.getDocuments().stream().map(d -> d.getId()).collect(Collectors.toSet());
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
                    //           attachment.setDocumentType(3001);
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
    public Attachment getWellDocument(int id) {
        return attachmentRepository.getOne(id);
    }
}


