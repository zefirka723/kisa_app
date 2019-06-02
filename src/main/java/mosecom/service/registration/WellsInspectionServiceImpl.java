package mosecom.service.registration;

import mosecom.dao.inspections.*;
import mosecom.dto.inspections.WellsInspectionProjection;
import mosecom.model.inspections.Document;
import mosecom.model.inspections.File;
import mosecom.model.inspections.WellsInspection;
import mosecom.model.inspections.dictionaries.Employees;
import mosecom.model.inspections.dictionaries.OrganizationSource;
import mosecom.model.inspections.dictionaries.RegStatus;
import mosecom.model.inspections.dictionaries.Secrecy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class WellsInspectionServiceImpl implements WellsInspectionService {

    @Autowired
    private WellsInspectionRepository inspectionRepository;

//    @Autowired
//    private FileRepository fileRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private OrganizationSourceRepository organizationSourceRepository;

    @Autowired
    private RegStatusRepository regStatusRepository;

    @Autowired
    private SecrecyRepository secrecyRepository;

//    @Value("${upload.path}") // ??
//    private String uploadPath;


    @Override
    public List<WellsInspectionProjection> getWellsInspectionList() {
        return inspectionRepository.findWellsInspectionList();
    }

    @Override
    public WellsInspection getWellsInspection(int id) {
        return inspectionRepository.getOne(id);
    }


    @Override
    public List<Employees> getAllEmployees() {
        return employeesRepository.findAll();
    }

    @Override
    public List<OrganizationSource> getAllOrganizationSource() {
        return organizationSourceRepository.findAll();
    }

    @Override
    public List<RegStatus> getAllRegStatus() {
        return regStatusRepository.findAll();
    }

    @Override
    public List<Secrecy> getAllSecrecy() {
        return secrecyRepository.findAll();
    }

    @Override
    public Document getDocumentByDocID(WellsInspection wellsInspection) {
        return documentRepository.getOne(wellsInspection.getDocument().getId());
    }


//    @Override
//    public File getFile(int id) {
//        return fileRepository.getOne(id);
//    }
}
