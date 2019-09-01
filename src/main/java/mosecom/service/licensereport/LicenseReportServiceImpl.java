package mosecom.service.licensereport;

import mosecom.dao.licensereport.LicenseReportRepository;
import mosecom.dao.licensereport.LicenseToWellsRepository;
import mosecom.dto.licensereport.LicenseReportProjection;
import mosecom.model.licencereport.LicenseReport;
import mosecom.model.licencereport.LicenseToWells;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LicenseReportServiceImpl implements LicenseReportService {

    @Autowired
    private LicenseReportRepository licenseReportRepository;

    @Autowired
    private LicenseToWellsRepository licenseToWellsRepository;

    @Override
    public List<LicenseReportProjection> getLicenseReportList() {
        return licenseReportRepository.findLicenseReportsList();
    }

    @Override
    public LicenseReport getLicenceReport(int id) {
        return licenseReportRepository.getOne(id);
    }

    @Override
    public LicenseReport save(LicenseReportProjection licenseReportProjection, MultipartFile[] file) throws IllegalStateException, IOException {
        //TODO: сделать сохранение карточки
        return null;
    }

    //  ЭТО ДИЧЬ!
    public List<LicenseToWells> getLinkById (int id) {
        return licenseToWellsRepository.findAll().stream()
                .filter(licenceToWellsProjection -> licenceToWellsProjection.getLicense().getId() == id)
                .collect(Collectors.toList());
    }

//    @Value("${upload.path}")
//    private String uploadPath;


}


