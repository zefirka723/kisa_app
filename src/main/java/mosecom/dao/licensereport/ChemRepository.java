package mosecom.dao.licensereport;

import mosecom.model.licencereport.Chem;
import mosecom.model.licencereport.Flowrate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChemRepository extends JpaRepository<Chem, Integer> {
    public List<Chem> findAllByWellId(Integer wellId);

    public Chem findByWellIdAndDateAndParametrId(int wellId, Date date, int parametrId);

}
