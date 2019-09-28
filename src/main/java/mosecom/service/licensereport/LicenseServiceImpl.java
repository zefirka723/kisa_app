package mosecom.service.licensereport;

import mosecom.dao.AttachmentRepository;
import mosecom.dao.inspections.DocumentRepository;
import mosecom.dao.licensereport.FlowrateRepository;
import mosecom.dao.licensereport.LicenseReportRepository;
import mosecom.dao.licensereport.LicenseRepository;
import mosecom.dao.licensereport.LicenseToWellsRepository;
import mosecom.dto.licensereport.LicenseProjection;
import mosecom.model.Attachment;
import mosecom.model.licencereport.License;
import mosecom.model.licencereport.LicenseReport;
import mosecom.model.licencereport.LicenseToWells;
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
public class LicenseServiceImpl {

    @Value("${upload.path}" + "LICENSE_REPORTS/")
    private String uploadPath;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LicenseToWellsRepository licenseToWellsRepository;

    @Autowired
    private LicenseReportRepository licenseReportRepository;

    @Autowired
    private FlowrateRepository flowrateRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private DocumentRepository documentRepository;


    public List<LicenseProjection> getLicenseList() {
        return licenseRepository.findLicenseList();
    }

    public License getLicense(int id) {
        return licenseRepository.getOne(id);
    }

    public List<LicenseToWells> getLinksList(License license) {
        return licenseToWellsRepository.findAllByLicense(license);
    }

//    public LicenseReportProjection findByLicenseId(int id) {
//        return licenseReportRepository.findByLicense_Id(id);
//    }

    public LicenseReport findReportByLicenseId(int id) {
        return licenseReportRepository.findByLicense_Id(id);
    }

    public License findLicenceByLicenseId(int id) {
        return licenseRepository.getOne(id);
    }

    @Transactional
    public void save(LicenseReport report,
                     String licenseNumber,
                     MultipartFile[] files) throws IllegalStateException, IOException {

//        LicenseReport savingReport = report.getId() != null ?
//                licenseReportRepository.getOne(report.getId())
//                : new LicenseReport();

        report.setLicense(licenseRepository.findByLicenseNumber(licenseNumber));

        if (report.getFileSetId() == null) {
            report.setFileSetId(licenseReportRepository.getNextFileSetId());
        }


        // Файлы
        // удаляем все удаленные из интерфейса файлы
        if(report.getAttachments() != null) {

            Set<Integer> keepDocumentsIds = report.getAttachments().stream().map(d -> d.getId()).collect(Collectors.toSet());
            List<Attachment> attachmentsFromDB = attachmentRepository.findAllByFileSetId(report.getFileSetId());

            attachmentsFromDB.removeIf(d -> keepDocumentsIds.contains(d.getId()));
            attachmentsFromDB.stream().forEach(a -> attachmentRepository.delete(a));
        }

        // новые файлы

        licenseReportRepository.save(report);
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String folderByType = "REPORTS";

                    java.io.File uploadDir = new java.io.File(uploadPath + "/" + report.getId().toString() + "/" + folderByType);

                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    file.transferTo(new java.io.File(uploadDir + "/" + file.getOriginalFilename()));

                    Attachment attachment = new Attachment();
                    attachment.setFileSetId(report.getFileSetId());
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

    public int getReportFileSetId(int reportId) {
        return licenseReportRepository.getOne(reportId).getFileSetId();
    }

    public License findLicenseByNumber(String licenseNumber) {
        return licenseRepository.findByLicenseNumber(licenseNumber);
    }

//    @Value("${upload.path}")
//    private String uploadPath;


}


