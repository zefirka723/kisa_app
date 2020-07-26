package mosecom.dao.licensereport.dictionaries;

import mosecom.model.licencereport.dictionary.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionReporitory extends JpaRepository<Condition, Integer> {
}
