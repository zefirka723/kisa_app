package mosecom.dao;

import mosecom.model.WellsDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellsDocRepository extends JpaRepository<WellsDoc, Integer> {

    WellsDoc findOneByWellId(int wellId);

    List<WellsDoc> findAllByWellId(int wellId);
}
