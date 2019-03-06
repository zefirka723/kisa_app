package mosecom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mosecom.dto.WellProjection;
import mosecom.model.Well;

@Repository
public interface WellRepository extends JpaRepository<Well, Integer> {

    @Query("select new mosecom.dto.WellProjection"
            + "(w.id as id, w.wellName as wellName, w.wellCollar as wellCollar)"//, w.drilledDate as drilledDate) "
            + "from Well w order by w.id")
    List<WellProjection> findWellsList();
}
