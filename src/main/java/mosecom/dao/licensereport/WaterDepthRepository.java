package mosecom.dao.licensereport;

import mosecom.model.licencereport.Flowrate;
import mosecom.model.licencereport.WaterDepth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterDepthRepository extends JpaRepository<WaterDepth, Integer> {
    List<WaterDepth> findAllByWellId(int wellId);
}
