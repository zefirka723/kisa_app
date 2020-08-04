package mosecom.dao.licensereport;

import mosecom.model.licencereport.Chem;
import mosecom.model.licencereport.WellChems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WellChemsRepository extends JpaRepository<WellChems, Integer> {
    List<WellChems> findAllByWellId(Integer wellId);


}
