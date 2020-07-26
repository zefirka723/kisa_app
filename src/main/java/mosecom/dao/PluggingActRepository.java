package mosecom.dao;

import mosecom.model.Attachment;
import mosecom.model.licencereport.PluggingAct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PluggingActRepository extends JpaRepository<PluggingAct, Integer> {
    PluggingAct findOneByWellId(int wellId);

//    @Query(value = "SELECT nextval('fgi.\"Documents_Doc_ID_seq\"')", nativeQuery = true)
//    Integer getDocId();
}