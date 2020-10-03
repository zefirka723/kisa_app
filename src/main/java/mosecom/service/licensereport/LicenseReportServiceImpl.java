package mosecom.service.licensereport;

import mosecom.dao.AttachmentRepository;
import mosecom.dao.DocumentTypeRepository;
import mosecom.dao.inspections.DocumentRepository;
import mosecom.dao.licensereport.LicenseReportRepository;
import mosecom.dictionaries.DocTypes;
import mosecom.model.Attachment;
import mosecom.model.licencereport.LicenseReport;
import mosecom.service.AttachmentServiceImpl;
import mosecom.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class LicenseReportServiceImpl {

    @Autowired
    private LicenseReportRepository licenseReportRepository;

//    @Autowired
//    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentServiceImpl attachmentService;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private LicenseServiceImpl licenseService;

    @Value("${upload.path}")
    private String uploadPath;

    public List<LicenseReport> findLicenseReportsList() {
        return licenseReportRepository.findAll();
    }

    public LicenseReport findReportById(int id) {
        LicenseReport report = licenseReportRepository.getOne(id);
        report.setAttachments(attachmentService.findAllByFileSetId(report.getFileSetId()));
        return report;
    }

    public LicenseReport findOneById(int id) {
        return licenseReportRepository.getOne(id);
    }

    public LicenseReport save(LicenseReport licenseReport) {
        licenseReport.setFileSetId(attachmentService.getNextFileSetId());
        return licenseReportRepository.save(licenseReport);
    }

    @Transactional
    public void update(LicenseReport licenseReport, MultipartFile [] files) throws IOException, ParseException {
        LicenseReport reportForSave = licenseReportRepository.getOne(licenseReport.getId());
        reportForSave.setComments(licenseReport.getComments());

        if (licenseReport.getAttachments() == null) {
            if (attachmentService.findAllByFileSetId(reportForSave.getFileSetId()) != null) {
                for (Attachment a: attachmentService.findAllByFileSetId(reportForSave.getFileSetId())) {
                    attachmentService.delete(a);
                }
            }
        }
        else {
            Set<Integer> keepDocumentsIds = licenseReport.getAttachments().stream().map(d -> d.getId()).collect(Collectors.toSet());
            for (Attachment a : attachmentService.findAllByFileSetId(reportForSave.getFileSetId())) {
                if (!keepDocumentsIds.contains(a.getId())) {
                    attachmentService.delete(a);
                }
            }
        }
        // все прикрепленные файлы добавляем в документы
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // Настройка пути под PROD
                    //TODO: переделать это и вообще все типы
                    String folderByType = "LICENSE";

                    uploadPath = uploadPath + folderByType
                            + "/" + licenseService.findLicenceByLicenseId(reportForSave.getLicenseDocId()).getLicenseNumber();

                    if (reportForSave.getReportName().getId() != 6) { // отчёты "Прочее"
                        uploadPath = uploadPath + "/LicenseReports/" + reportForSave.getDate().toString().split(" ", 2)[0];
                    }
                    java.io.File uploadDir = new java.io.File(uploadPath);


                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    //String newFilePath = UUID.randomUUID().toString();
                    file.transferTo(new java.io.File(uploadDir + "/" + file.getOriginalFilename()));
                    //newFilePath));

                    Attachment attachment = new Attachment();
                    //attachment.setWell(well);
                    attachment.setDocumentType(documentTypeRepository.getOne(DocTypes.LICENSE_REPORT.getId()));
                    attachment.setFileContentType(file.getContentType());
                    attachment.setFileContentType(file.getContentType());

                    // TODO: оптимизировать эти поля
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setFilePath(uploadDir + "/");
                    //+ file.getOriginalFilename()); // было newFilePath
                    attachment.setFileSize(file.getSize());
                    //attachment.setDocId(docType.getId());
                    attachment.setFileSetId(reportForSave.getFileSetId());
                    attachmentService.save(attachment);
                    //well.getAttachments().add(attachment);
                }
            }
        }

        licenseReportRepository.save(reportForSave);
    }

}


