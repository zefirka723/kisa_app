package mosecom.service.registration;

import mosecom.dao.inspections.*;
import mosecom.dto.inspections.DocumentFullProjection;
import mosecom.dto.inspections.DocumentProjection;
import mosecom.dto.inspections.RegItemProjection;
import mosecom.model.inspections.Document;
import mosecom.model.inspections.RegItem;
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
public class RegItemServiceImpl implements RegItemService {

    @Autowired
    private RegItemRepository regItemRepository;

    @Override
    public List<RegItemProjection> getRegItemsList() {
        return regItemRepository.findRegItemsList();
    }

    @Override
    public RegItem getRegItem(int id) {
        return regItemRepository.getOne(id);
    }

}
