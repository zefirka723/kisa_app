package mosecom.service.licensereport;

import mosecom.dao.licensereport.FlowrateRepository;
import mosecom.dao.licensereport.LicenseReportRepository;
import mosecom.dao.licensereport.LicenseRepository;
import mosecom.dao.licensereport.LicenseToWellsRepository;
import mosecom.dto.licensereport.LicenseProjection;
import mosecom.dto.licensereport.LicenseReportProjection;
import mosecom.model.licencereport.License;
import mosecom.model.licencereport.LicenseReport;
import mosecom.model.licencereport.LicenseToWells;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;


@Service
public class LicenseServiceImpl {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LicenseToWellsRepository licenseToWellsRepository;

    @Autowired
    private LicenseReportRepository licenseReportRepository;

    @Autowired
    private FlowrateRepository flowrateRepository;

    public List<LicenseProjection> getLicenseList() {
        return licenseRepository.findLicenseList();
    }

    public License getLicense(int id) {
        return licenseRepository.getOne(id);
    }

    public List<LicenseToWells> getLinksList(License license) {
        return licenseToWellsRepository.findAllByLicense(license);
    }

    public LicenseReportProjection findByLicenseId(int id) {
        return licenseReportRepository.findByLicense_Id(id);
    }

    @Transactional
    public License save(LicenseReportProjection dto,
                        int license_id,
                        MultipartFile[] file) throws IllegalStateException, IOException {
        LicenseReport report;
        if (dto.getId() != null) {
            report = licenseReportRepository.getOne(dto.getId());
        } else {
            report = new LicenseReport();
        }
        report.setDate(dto.getDate());
        report.setLs(dto.getLs());
        report.setTp(dto.getTp());
        report.setLicense(licenseRepository.getOne(license_id));
        licenseReportRepository.save(report);
        return licenseRepository.getOne(license_id);
    }

//    @Value("${upload.path}")
//    private String uploadPath;


}


