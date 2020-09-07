package mosecom.service.catalog;

import mosecom.dao.DocumentTypeRepository;
import mosecom.model.DocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DocumentTypeServiceImpl {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    public List<DocumentType> getTypesByIds(int ids[]) {
        List<DocumentType> result = new ArrayList<>();
        for (int id: ids) {
            result.add(documentTypeRepository.findOneById(id));
        }
        return result;
    }



}


