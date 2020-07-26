package mosecom.service.licensereport;

import mosecom.dao.licensereport.FlowrateRepository;
import mosecom.model.licencereport.Flowrate;
import mosecom.model.licencereport.FlowrateWrapper;
import mosecom.model.licencereport.Report2Tp;
import mosecom.model.licencereport.Report2TpWrapper;
import mosecom.utils.MeasurementConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;


@Service
public class FlowrateServiceImpl {

    @Autowired
    private FlowrateRepository flowrateRepository;

    public List<Flowrate> findFlowratesByWellID(int wellId) {
        return flowrateRepository.findAllByWellIdOrderByDateDesc(wellId);
    }

    public void saveWrapper (FlowrateWrapper flowrateWrapper) throws ParseException {
        if (!flowrateWrapper.getFlowrates().isEmpty()) {
            for (Flowrate f: flowrateWrapper.getFlowrates()) {
                if (f.getDate() != null && f.getFlowrate() != null) {
                    f.setWellId(flowrateWrapper.getWellId());
                    f.setReportDocId(flowrateWrapper.getReportDocId());
                    save(f);
                }
            }
        }
    }

    public void save (Flowrate flowrate) throws ParseException {
        flowrate.setFlowrate(
                MeasurementConverter.convertValueToM3Day(flowrate.getFlowrate(), flowrate.getDate(), flowrate.getMeasurement())
        );
        flowrateRepository.save(flowrate);
    }



}


