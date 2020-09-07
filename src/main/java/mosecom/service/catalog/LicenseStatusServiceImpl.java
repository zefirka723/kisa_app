package mosecom.service.catalog;

import mosecom.dao.catalog.dictionaties.LicenseOrganizationRepository;
import mosecom.dao.catalog.dictionaties.LicenseStatusRepository;
import mosecom.model.catalog.dictionaries.LicenseOrganization;
import mosecom.model.catalog.dictionaries.LicenseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LicenseStatusServiceImpl {

    @Autowired
    private LicenseStatusRepository licenseStatusRepository;

    public List<LicenseStatus> findAll() {
        return licenseStatusRepository.findAllByOrderByNameAsc();
    }
}


