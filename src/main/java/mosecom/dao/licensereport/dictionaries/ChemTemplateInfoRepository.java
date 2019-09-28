package mosecom.dao.licensereport.dictionaries;

import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChemTemplateInfoRepository extends JpaRepository<ChemTemplateInfo, Integer> {
}
