package mosecom.service;

import mosecom.dao.AttachmentRepository;
import mosecom.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentServiceImpl {
    @Autowired
    AttachmentRepository attachmentRepository;

    //public List<Attachment> findAllAttachesByFileSetId(int fileSetId) {
        //return attachmentRepository.findAllByFileSetId(fileSetId);
    //}

    public Attachment findOneByFileSetId (int fileSetId) {
        return attachmentRepository.findByFileSetId(fileSetId);
    }

}
