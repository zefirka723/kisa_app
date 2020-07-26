package mosecom.dao.licensereport;

import mosecom.model.licencereport.WellCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellConditionRepository extends JpaRepository<WellCondition, Integer> {

    public List<WellCondition> findAllByWellIdOrderByDateDesc(Integer wellId);

}
