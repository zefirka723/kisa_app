package mosecom.service.registration;

import mosecom.dao.inspections.RegItemRepository;
import mosecom.dictionaries.DocTypes;
import mosecom.dto.inspections.RegItemProjection;
import mosecom.model.inspections.RegItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RegItemServiceImpl implements RegItemService {

    @Autowired
    private RegItemRepository regItemRepository;

    @Override
    public List<RegItemProjection> getRegItemsList() {
        return regItemRepository.findRegItemsList();
    }

    @Override
    public List<RegItemProjection> filterRegItemsByType(DocTypes docType) {
        return regItemRepository.findRegItemsList().stream()
                .filter(regItemProjection -> regItemProjection.getDocType() == docType.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<RegItemProjection> filterRegIremsByTypeAndState(DocTypes docType, Integer state) {
        return filterRegItemsByType(docType)
                .stream()
                .filter(regItemProjection -> regItemProjection.getRegStatusId() == state)
                .collect(Collectors.toList());
    }

    @Override
    public RegItem getRegItem(int id) {
        return regItemRepository.getOne(id);
    }

}
