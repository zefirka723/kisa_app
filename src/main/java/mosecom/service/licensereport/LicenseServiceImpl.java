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
    
    public List<License> findAll() {
        return licenseRepository.findAll();
    }

    public License findLicenceByLicenseId(int id) {
        return licenseRepository.getOne(id);
    }


    public License findLicenseByNumber(String licenseNumber) {
        return licenseRepository.findByLicenseNumber(licenseNumber);
    }

}


