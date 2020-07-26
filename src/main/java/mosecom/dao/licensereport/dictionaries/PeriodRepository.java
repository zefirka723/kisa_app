package mosecom.dao.licensereport.dictionaries;

import mosecom.model.licencereport.dictionary.ChemComponent;
import mosecom.model.licencereport.dictionary.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Integer> {
}
