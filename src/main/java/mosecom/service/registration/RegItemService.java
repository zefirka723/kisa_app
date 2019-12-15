package mosecom.service.registration;

import mosecom.dictionaries.DocTypes;
import mosecom.dto.inspections.RegItemProjection;
import mosecom.model.inspections.RegItem;

import java.util.List;

public interface RegItemService {

    List<RegItemProjection> getRegItemsList();

    List<RegItemProjection> filterRegItemsByType(DocTypes docType);

    List<RegItemProjection> filterRegIremsByTypeAndState(DocTypes docTypes, Integer state);

    RegItem getRegItem(int id);

}
