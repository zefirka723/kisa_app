package mosecom.service.registration;

import mosecom.dictionaries.DocTypes;
import mosecom.dto.inspections.DocumentFullProjection;
import mosecom.dto.inspections.DocumentProjection;
import mosecom.dto.inspections.RegItemProjection;
import mosecom.model.inspections.Document;
import mosecom.model.inspections.RegItem;
import mosecom.model.inspections.dictionaries.Employees;
import mosecom.model.inspections.dictionaries.OrganizationSource;
import mosecom.model.inspections.dictionaries.RegStatus;
import mosecom.model.inspections.dictionaries.Secrecy;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RegItemService {

    List<RegItemProjection> getRegItemsList();

    List<RegItemProjection> filterRegItemsByType(DocTypes docType);

    List<RegItemProjection> filterRegIremsByTypeAndState(DocTypes docTypes, Integer state);

    RegItem getRegItem(int id);

}
