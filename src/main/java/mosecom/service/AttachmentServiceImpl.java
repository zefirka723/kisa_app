package mosecom.service;

import mosecom.dao.AttachmentRepository;
import mosecom.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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

    public List<Attachment> findAllByFileSetId (int fileSetId) {
        return attachmentRepository.findAllByFileSetId(fileSetId);
    }

    public void delete (Attachment attachment) {
        File fileForDel = new File(attachment.getFilePath() + attachment.getFileName());
        fileForDel.delete();
        attachmentRepository.delete(attachment);
    }

    public Integer getNextFileSetId() {
        return attachmentRepository.getNextFileSetId();
    }

    public void save(Attachment attachment) {
        attachmentRepository.save(attachment);
    }

    public Attachment getOne(int id) {
        return attachmentRepository.getOne(id);
    }

}
