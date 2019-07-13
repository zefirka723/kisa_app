package mosecom.dao.inspections;

import mosecom.dto.inspections.RegItemProjection;
import mosecom.model.inspections.RegItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegItemRepository extends JpaRepository<RegItem, Integer> {

    @Query("select new mosecom.dto.inspections.RegItemProjection"
            + "(r.id as id, r.regStatusId as regStatusId, r.regStatusText as regStatusText, r.authorId as authorId, r.docType as docType, r.dateProcessing as dateProcessing, r.date as date, r.regNumber as regNumber, r.observationPointId as observationPointId, r.link as link) "
            + "from RegItem r order by r.id")
    List<RegItemProjection> findRegItemsList();

}

