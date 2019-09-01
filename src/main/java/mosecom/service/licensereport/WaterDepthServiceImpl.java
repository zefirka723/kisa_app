package mosecom.service.licensereport;

import mosecom.dao.licensereport.WaterDepthRepository;
import mosecom.model.licencereport.WaterDepth;
import mosecom.model.licencereport.WaterDepthByWell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WaterDepthServiceImpl {

    @Autowired
    private WaterDepthRepository waterDepthRepository;

    public List<WaterDepth> findWaterDepthsByWellId(int wellId) {
        return waterDepthRepository.findAllByWellId(wellId);
    }

    public void save(WaterDepthByWell waterDepths) {
        if (waterDepths.getDepths() != null) {
            for (WaterDepth d : waterDepths.getDepths()) {
                d.setWellId(waterDepths.getWellId());
                d.setDataSource(1);
                waterDepthRepository.save(d);
            }
        }
    }

}


