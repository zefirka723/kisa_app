package mosecom.dao.inspections;

import mosecom.dto.inspections.SpringsInspectionProjection;
import mosecom.dto.inspections.WellsInspectionProjection;
import mosecom.model.inspections.SpringsInspection;
import mosecom.model.inspections.WellsInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringsInspectionRepository extends JpaRepository<SpringsInspection, Integer> {

    @Query("select new mosecom.dto.inspections.SpringsInspectionProjection"
            + "(s.id as id, s.springId as springId, s.date as date, s.link as link) "
            + "from SpringsInspection s order by s.id")
    List<SpringsInspectionProjection> findSpringsInspectionList();
}
