package mosecom.service.licensereport;

import mosecom.dao.licensereport.WellConditionRepository;
import mosecom.model.licencereport.WellCondition;
import mosecom.model.licencereport.WellConditionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WellConditionServiceImpl {

    @Autowired
    private WellConditionRepository wellConditionRepository;

    public void save (WellCondition wellCondition) {
        wellConditionRepository.save(wellCondition);
    }

    public void saveWrapper (WellConditionWrapper wrapper) {
        if (!wrapper.getConditions().isEmpty()) {
            for (WellCondition c: wrapper.getConditions()) {
                if (c.getDate() != null && c.getConditionId() != null) {
                    c.setReportDocId(wrapper.getReportDocId());
                    c.setWellId(wrapper.getWellId());
                    save(c);
                }
            }
        }
    }

    public List<WellCondition> getWellConditionsByWellId (int wellId) {
        return wellConditionRepository.findAllByWellIdOrderByDateDesc(wellId);
    }

}


