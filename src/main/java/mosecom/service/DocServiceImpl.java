package mosecom.service;

import mosecom.dao.WellsDocRepository;
import mosecom.model.WellsDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private WellsDocRepository docRepository;

    public List<WellsDoc> getAll() {
        return StreamSupport
                .stream(
                        Spliterators.spliteratorUnknownSize(docRepository.findAll().iterator(), Spliterator.NONNULL),
                        false)
                .collect(Collectors.toList());
    }

}
