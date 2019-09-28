package mosecom.dao.licensereport.dictionaries;

import mosecom.model.licencereport.ChemTemplateItem;
import mosecom.model.licencereport.Flowrate;
import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChemTemplateItemRepository extends JpaRepository<ChemTemplateItem, Integer> {
    public List<ChemTemplateItem> findAllByTemplateInfoOrderByDisplayOrder(ChemTemplateInfo templateInfo);
}
