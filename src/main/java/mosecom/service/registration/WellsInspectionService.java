package mosecom.service.registration;

import mosecom.dto.inspections.WellsInspectionProjection;
import mosecom.model.inspections.Document;
import mosecom.model.inspections.File;
import mosecom.model.inspections.WellsInspection;
import mosecom.model.inspections.dictionaries.Employees;
import mosecom.model.inspections.dictionaries.OrganizationSource;
import mosecom.model.inspections.dictionaries.RegStatus;
import mosecom.model.inspections.dictionaries.Secrecy;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface WellsInspectionService {

    List<WellsInspectionProjection> getWellsInspectionList();

    WellsInspection getWellsInspection(int id);

    List<Employees> getAllEmployees();

    List<OrganizationSource> getAllOrganizationSource();

    List<RegStatus> getAllRegStatus();

    List<Secrecy> getAllSecrecy();

    Document getDocumentByDocID(WellsInspection wellsInspection);


//    Document getDocument();

 //   File getFile(int id);
}
