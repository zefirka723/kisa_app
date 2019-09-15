package mosecom.service.licensereport;

import mosecom.dao.AttachmentRepository;
import mosecom.dao.licensereport.LicenseReportRepository;
import mosecom.model.licencereport.LicenseReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LicenseReportServiceImpl {

    @Autowired
    private LicenseReportRepository licenseReportRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    public List<LicenseReport> findLicenseReportsList() {
        return licenseReportRepository.findAll();
    }

    public LicenseReport findReportById(int id) {
        LicenseReport report = licenseReportRepository.getOne(id);
        report.setAttachments(attachmentRepository.findAllByFileSetId(report.getFileSetId()));
        return report;
    }

}


