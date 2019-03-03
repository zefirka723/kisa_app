package mosecom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mosecom.model.WellsConstruction;

@Repository
public interface WellsConstructionRepository extends JpaRepository<WellsConstruction, Integer> {

    WellsConstruction findOneByWellId(int wellId);

    List<WellsConstruction> findAllByWellId(int wellId);
}
