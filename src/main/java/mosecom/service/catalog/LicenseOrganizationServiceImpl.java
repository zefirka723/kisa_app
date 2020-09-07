package mosecom.service.catalog;

import mosecom.dao.DocumentTypeRepository;
import mosecom.dao.catalog.dictionaties.LicenseOrganizationRepository;
import mosecom.model.DocumentType;
import mosecom.model.catalog.dictionaries.LicenseOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LicenseOrganizationServiceImpl {

    @Autowired
    private LicenseOrganizationRepository licenseOrganizationRepository;

    public List<LicenseOrganization> findAll() {
        return licenseOrganizationRepository.findAllByOrderByNameAsc();
    }
}


