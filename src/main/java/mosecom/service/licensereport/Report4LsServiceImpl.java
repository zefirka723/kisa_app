package mosecom.service.licensereport;

import mosecom.dao.licensereport.Report4LsRepository;
import mosecom.model.licencereport.Report4Ls;
import mosecom.model.licencereport.Report4LsWrapper;
import mosecom.utils.MeasurementConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;


@Service
public class Report4LsServiceImpl {

    @Autowired
    private Report4LsRepository report4LsRepository;

    public void save (Report4Ls report4Ls) {
        report4Ls.setFlowrate(MeasurementConverter.convertValueToM3Day(report4Ls.getFlowrate(), report4Ls.getDate(), report4Ls.getMeasurement()));
        report4LsRepository.save(report4Ls);
    }

    public List<Report4Ls> getReport4LsByReportDocId(int reportDocId) {
        return report4LsRepository.findAllByReportDocIdOrderByDateDesc(reportDocId);
    }



    public void saveWrapper (Report4LsWrapper report4LsWrapper) throws ParseException {
        if (!report4LsWrapper.getReport4LsList().isEmpty()) {
            for (Report4Ls r: report4LsWrapper.getReport4LsList()) {
                if (r.getDate() != null && r.getFlowrate() != null) {
                    r.setReportDocId(report4LsWrapper.getReportDocId());
                    save(r);
                }
            }
        }
    }

}


