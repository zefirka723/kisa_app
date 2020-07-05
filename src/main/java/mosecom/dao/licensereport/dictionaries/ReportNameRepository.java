package mosecom.dao.licensereport.dictionaries;

import mosecom.model.licencereport.ChemTemplateItem;
import mosecom.model.licencereport.dictionary.ReportName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportNameRepository extends JpaRepository<ReportName, Integer> {

}
