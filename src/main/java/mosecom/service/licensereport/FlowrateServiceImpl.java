package mosecom.service.licensereport;

import mosecom.dao.licensereport.FlowrateRepository;
import mosecom.model.licencereport.Flowrate;
import mosecom.model.licencereport.FlowrateByWell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FlowrateServiceImpl {

    @Autowired
    private FlowrateRepository flowrateRepository;

    public List<Flowrate> findFlowratesByWellID(int wellId) {
        return flowrateRepository.findAllByWellId(wellId);
    }

    public void save(FlowrateByWell flowrates) {
        if (flowrates.getRates() != null) {
            for (Flowrate f : flowrates.getRates()) {
                f.setWellId(flowrates.getWellId());
                switch (f.getMeasure()) {
                    case 1:
                        f.setFlowrate(f.getFlowrate() * 24);
                        break;
                    case 2:
                        f.setFlowrate(f.getFlowrate() * 86.4);
                }
                flowrateRepository.save(f);
            }
        }
    }

}


