package mosecom.dao.licensereport;

import mosecom.model.licencereport.Flowrate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowrateRepository extends JpaRepository<Flowrate, Integer> {
    List<Flowrate> findAllByWellId(int wellId);
}
