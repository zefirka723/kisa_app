package mosecom.dao.inspections;

import mosecom.dto.inspections.DrawWellsInspectionProjection;
import mosecom.model.inspections.DrawWellsInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawWellsInspectionRepository extends JpaRepository<DrawWellsInspection, Integer> {

    @Query("select new mosecom.dto.inspections.DrawWellsInspectionProjection"
            + "(w.id as id, w.drawWellId as drawWellId, w.date as date, w.link as link) "
            + "from DrawWellsInspection w order by w.id")
    List<DrawWellsInspectionProjection> findDrawWellsInspectionList();

}
