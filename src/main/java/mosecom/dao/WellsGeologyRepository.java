package mosecom.dao;

import mosecom.model.WellsGeology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellsGeologyRepository extends JpaRepository<WellsGeology, Integer> {

    WellsGeology findOneByWellId(int wellId);

    List<WellsGeology> findAllByWellId(int wellId);
}
