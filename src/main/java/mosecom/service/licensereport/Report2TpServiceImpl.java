package mosecom.service.licensereport;

import mosecom.dao.licensereport.Report2TpRepository;
import mosecom.model.licencereport.Measure;
import mosecom.model.licencereport.Report2Tp;
import mosecom.model.licencereport.Report2TpWrapper;
import mosecom.utils.MeasurementConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;


@Service
public class Report2TpServiceImpl {

    @Autowired
    private Report2TpRepository report2TpRepository;

    public void saveWithMeasure (Report2Tp report2Tp, Measure measure) {
        report2TpRepository.save(report2Tp);
    }

    public void save (Report2Tp report2Tp) throws ParseException {
        report2Tp.setFlowrate(
                MeasurementConverter.convertValueToM3Day(report2Tp.getFlowrate(), report2Tp.getDate(), report2Tp.getMeasurement())
        );
        report2TpRepository.save(report2Tp);
    }


    public void saveWrapper (Report2TpWrapper report2TpWrapper) throws ParseException {
        if (!report2TpWrapper.getReport2Tps().isEmpty()) {
            for (Report2Tp r: report2TpWrapper.getReport2Tps()) {
                if (r.getDate() != null && r.getFlowrate() != null) {
                    r.setReportDocId(report2TpWrapper.getReportDocId());
                    save(r);
                }
            }
        }
    }


    public List<Report2Tp> getReport2TpByReportDocId(int reportDocId) {
        return report2TpRepository.findAllByReportDocIdOrderByDateDesc(reportDocId);
    }

}


