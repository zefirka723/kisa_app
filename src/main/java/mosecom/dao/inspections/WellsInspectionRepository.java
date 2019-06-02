package mosecom.dao.inspections;

import mosecom.dto.inspections.DrawWellsInspectionProjection;
import mosecom.dto.inspections.WellsInspectionProjection;
import mosecom.model.inspections.DrawWellsInspection;
import mosecom.model.inspections.WellsInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellsInspectionRepository extends JpaRepository<WellsInspection, Integer> {

    @Query("select new mosecom.dto.inspections.WellsInspectionProjection"
            + "(w.id as id, w.wellId as wellId, w.date as date, w.link as link) "
            + "from WellsInspection w order by w.id")
    List<WellsInspectionProjection> findWellsInspectionList();}
