package mosecom.controller;

import mosecom.dao.HorisontRepository;
import mosecom.model.licencereport.Report4Ls;
import mosecom.model.licencereport.Report4LsWrapper;
import mosecom.model.licencereport.dictionary.Measurement;
import mosecom.service.licensereport.Report4LsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
@RequestMapping("/report4ls")
public class Report4LsController {

    @Autowired
    Report4LsServiceImpl report4LsService;

    @Autowired
    HorisontRepository horisontRepository; // TODO: заменить

    @RequestMapping(value = "/{reportDocId}")
    public ModelAndView licenseReport4Ls(@PathVariable(name = "reportDocId") int reportDocId) {
        ModelAndView result = new ModelAndView("licence_report/report4ls");
        Report4LsWrapper report4LsWrapper = new Report4LsWrapper();
        report4LsWrapper.setReportDocId(reportDocId);
        report4LsWrapper.setOldReport4Ls(report4LsService.getReport4LsByReportDocId(reportDocId));

        Report4Ls report4Ls = new Report4Ls();
        report4Ls.setMeasurement(Measurement.TM3DAY);
        report4LsWrapper.setReport4LsList(new ArrayList<>());
        report4LsWrapper.getReport4LsList().add(report4Ls);

        result.addObject("measurements", new Measurement[]{Measurement.TM3DAY, Measurement.TM3YEAR});
        result.addObject("report4LsWrapper", report4LsWrapper);
        result.addObject("horisonts", horisontRepository.findAllByOrderNotNullOrderByOrderAsc());
        return result;
    }




    @RequestMapping(value = "/submit")
    public String save4Ls(@ModelAttribute Report4LsWrapper report4LsWrapper) throws ParseException {
        report4LsService.saveWrapper(report4LsWrapper);
        return "redirect:/reports/" + report4LsWrapper.getReportDocId();
    }

}
