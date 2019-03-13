package mosecom.dao;

import mosecom.model.WellssStressTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellsStressTestRepository extends JpaRepository<WellssStressTest, Integer> {

    WellssStressTest findOneByWellId(int wellId);

    List<WellssStressTest> findAllByWellId(int wellId);
}
