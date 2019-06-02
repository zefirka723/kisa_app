package mosecom.service.registration;

import mosecom.dto.inspections.DocumentFullProjection;
import mosecom.dto.inspections.DocumentProjection;
import mosecom.model.ConstructionType;
import mosecom.model.inspections.Document;
import mosecom.model.inspections.dictionaries.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentService {

    List<DocumentProjection> getDocumentsList();

    Document getDocument(int id);

    Document save(DocumentFullProjection well, MultipartFile[] file) throws IllegalStateException, IOException;

    List<RegStatus> getAllRegStatus();

    List<Employees> getAllEmployees();

    List<OrganizationSource> getAllOrganizationSource();

    List<Secrecy> getAllSecrecy();

}
