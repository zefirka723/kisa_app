package mosecom.service.licensereport;

import mosecom.dao.licensereport.LicenseReportRepository;
import mosecom.model.licencereport.LicenseReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LicenseReportServiceImpl {

    @Autowired
    private LicenseReportRepository licenseReportRepository;

    public List<LicenseReport> findLicenseReportsList() {
        return licenseReportRepository.findAll();
    }

    public LicenseReport findReportById(int id) {
        return licenseReportRepository.getOne(id);
    }

}


