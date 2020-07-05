package mosecom.service.licensereport;

import mosecom.dao.licensereport.Report3LsRepository;
import mosecom.model.licencereport.*;
import mosecom.utils.MeasurementConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;


@Service
public class Report3LsServiceImpl {

    @Autowired
    private Report3LsRepository report3LsRepository;

    public void save (Report3Ls report3Ls) {
        report3Ls.setFlowrate(
                MeasurementConverter.convertValueToM3Day(report3Ls.getFlowrate(), report3Ls.getDate(), report3Ls.getMeasurement())
        );
        report3LsRepository.save(report3Ls);
    }

    public List<Report3Ls> getReport3LsByReportDocId(int reportDocId) {
        return report3LsRepository.findAllByReportDocIdOrderByDateDesc(reportDocId);
    }

    public void saveWrapper (Report3LsWrapper report3LsWrapper) throws ParseException {
        if (!report3LsWrapper.getReport3LsList().isEmpty()) {
            for (Report3Ls r: report3LsWrapper.getReport3LsList()) {
                if (r.getDate() != null && r.getFlowrate() != null) {
                    r.setReportDocId(report3LsWrapper.getReportDocId());
                    save(r);
                }
            }
        }
    }

}


