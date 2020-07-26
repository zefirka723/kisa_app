package mosecom.dao;

import mosecom.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    public List<Attachment> findAllByFileSetId(int fileSetId);

    public Attachment findByFileSetId(int fileSetId);

    @Query(value = "SELECT nextval('public.\"File_set_ID_seq\"')", nativeQuery = true)
    Integer getNextFileSetId();
}
