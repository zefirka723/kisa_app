package mosecom.service.registration;

import mosecom.dao.inspections.*;
//import mosecom.dto.inspections.DocumentFullProjection;
import mosecom.dto.inspections.DocumentProjection;
import mosecom.model.inspections.Document;
import mosecom.model.inspections.dictionaries.Employees;
import mosecom.model.inspections.dictionaries.OrganizationSource;
import mosecom.model.inspections.dictionaries.RegStatus;
import mosecom.model.inspections.dictionaries.Secrecy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;


@Service
public class DocumentServiceImpl implements DocumentService {

    //TODO: убрать отсюда репозитории, сделать через сервисы

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private RegStatusRepository regStatusRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private OrganizationSourceRepository organizationSourceRepository;

    @Autowired
    private SecrecyRepository secrecyRepository;


    @Override
    public List<DocumentProjection> getDocumentsList() {
        return documentRepository.findDocumentsList();
    }

    @Override
    public Document getDocument(int id) {
        return documentRepository.getOne(id);
    }

    @Override
    @Transactional
    public Document save(DocumentProjection dto, MultipartFile[] file) throws IllegalStateException, IOException {
        Document document;

        if (dto.getId() != null && dto.getId() > 0) {
            document = documentRepository.getOne(dto.getId());
        } else {
            document = new Document();
        }

        document.setAuthorId(dto.getAuthorId());
        document.setComment(dto.getComment());
        document.setDate(dto.getDate());
        document.setOrganizationId(dto.getOrganizationId());
        document.setPages(dto.getPages());
        document.setRegNumber(dto.getRegNumber());
        document.setRegStatusId(dto.getRegStatusId());
        document.setSecrecyId(dto.getSecrecyId());
        document.setStorage(dto.getStorage());

        documentRepository.save(document);
        return document;
    }

    @Override
    public List<RegStatus> getAllRegStatus() {
        return regStatusRepository.findAll();
    }

    @Override
    public List<Employees> getAllEmployees() {
        return employeesRepository.findAllByOrderByNameAsc();
    }

    public List<Employees> getRegistrators() {
        return employeesRepository.findAllByIsRegistratorTrueOrderByNameAsc();
    }

    @Override
    public List<OrganizationSource> getAllOrganizationSource() {
        return organizationSourceRepository.findAll();
    }

    @Override
    public List<Secrecy> getAllSecrecy() {
        return secrecyRepository.findAll();
    }

}
