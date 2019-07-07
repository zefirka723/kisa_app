package mosecom.dao.inspections;

import mosecom.dto.inspections.DocumentProjection;
import mosecom.model.inspections.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @Query("select new mosecom.dto.inspections.DocumentProjection"
            + "(d.id as id, d.authorId as authorId, d.comment as comment, d.date as date, d.organizationId as organizationId, d.pages as pages, d.regNumber as regNumber, d.regStatusId as regStatusId, d.secrecyId as secrecyId, d.storage as storage, d.docType as docType) "
            + "from Document d order by d.id")
    List<DocumentProjection> findDocumentsList();

}

