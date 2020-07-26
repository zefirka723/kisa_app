package mosecom.service;

import mosecom.dao.AttachmentRepository;
import mosecom.dao.PluggingActRepository;
import mosecom.model.Attachment;
import mosecom.model.licencereport.PluggingAct;
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
public class PluggingActServiceImpl {

    @Autowired
    PluggingActRepository pluggingActRepository;

    @Autowired
    AttachmentRepository attachmentRepository;



    @Value("${upload.path}" + "PRIMARYDOCS/GW/WELLS")
    private String uploadPath;

    @Transactional
    public void save(PluggingAct act,
                     MultipartFile[] files) throws IllegalStateException, IOException {

        // удаляем все удаленные из интерфейса файлы
//        if (act.getAttachment() != null) {
//            Set<Integer> keepDocumentsIds = act.getAttachment().stream().map(d -> d.getId()).collect(Collectors.toSet());
//            List<Attachment> attachmentsFromDB = attachmentRepository.findAllByFileSetId(act.getFileSetId());
//
//            attachmentsFromDB.removeIf(d -> keepDocumentsIds.contains(d.getId()));
//            attachmentsFromDB.stream().forEach(a -> attachmentRepository.delete(a));
//        }

        // новые файлы
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {

                    if (act.getFileSetId() == null) {
                        act.setFileSetId(attachmentRepository.getNextFileSetId());
                    }
                    pluggingActRepository.save(act);

                    String folderByType = "LIQUIDATION";

                    java.io.File uploadDir = new java.io.File(uploadPath + "/" + act.getWellId() + "/" + folderByType);

                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    file.transferTo(new java.io.File(uploadDir + "/" + file.getOriginalFilename()));

                    Attachment attachment = new Attachment();
                    attachment.setFileSetId(act.getFileSetId());
                    attachment.setFileContentType(file.getContentType());
                    attachment.setFileContentType(file.getContentType());

                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setFilePath(uploadDir + "/");
                    attachment.setFileSize(file.getSize());
                    attachmentRepository.save(attachment);
                }
            }
        }
    }

    public PluggingAct findOneByWellId (int wellId) {
        return pluggingActRepository.findOneByWellId(wellId);
    }

}
