package mosecom.service.licensereport;

import mosecom.dao.licensereport.WaterDepthRepository;
import mosecom.model.licencereport.WaterDepth;
import mosecom.model.licencereport.WaterDepthByWell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void save(WaterDepth waterDepth) {
        waterDepthRepository.save(waterDepth);
    }

    public void delete(List<WaterDepth> waterDepths) {
        for (WaterDepth d : waterDepths) {
            waterDepthRepository.delete(d);
        }
    }

    public void clearWaterDepths(List<WaterDepth> depths) {
        for (WaterDepth d : depths) {
            if (d.getId() != null) {
                waterDepthRepository.delete(waterDepthRepository.getOne(d.getId()));
            }
        }
    }

    public void saveChanges(List<WaterDepth> depthsList, int wellId) {
        if (waterDepthRepository.findAllByWellId(wellId).size() != 0) {
            List<WaterDepth> listForDel = waterDepthRepository.findAllByWellId(wellId);

            if (depthsList != null) {
                for (WaterDepth c : waterDepthRepository.findAllByWellId(wellId)) {
                    for (WaterDepth d : depthsList) {
                        if (c.getId().equals(d.getId())) {
                            listForDel.remove(c);
                        }
                    }
                }
            }
            listForDel.stream().forEach(c -> waterDepthRepository.delete(c));
        }

        if (depthsList != null) {
            depthsList.stream().forEach(d -> {
                if (!(d.getDate() == null && d.getDate() == null)) { // TODO: разобраться с пустыми записями, которые приходят с фронта
                    d.setWellId(wellId);
                    d.setDataSource(0);
                    save(d);
                }
            });
        }
    }
}