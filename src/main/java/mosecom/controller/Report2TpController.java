package mosecom.controller;

import mosecom.model.licencereport.Report2Tp;
import mosecom.model.licencereport.Report2TpWrapper;
import mosecom.model.licencereport.dictionary.Measurement;
import mosecom.service.licensereport.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("/report2tp")
public class Report2TpController {

    @Autowired
    Report2TpServiceImpl report2TpService;

    @RequestMapping(value = "/{reportDocId}")
    public ModelAndView licenseReport2Tp(@PathVariable(name = "reportDocId") int reportDocId) {
        ModelAndView result = new ModelAndView("licence_report/report2tp");
        Report2TpWrapper report2TpWrapper = new Report2TpWrapper();
        report2TpWrapper.setReportDocId(reportDocId);
        report2TpWrapper.setOldReport2Tp(report2TpService.getReport2TpByReportDocId(reportDocId));

        Report2Tp report2Tp = new Report2Tp();
        report2Tp.setMeasurement(Measurement.TM3DAY);
        report2TpWrapper.setReport2Tps(new ArrayList<>());
        report2TpWrapper.getReport2Tps().add(report2Tp);

//        List<Report2Tp> oldReport2Tp = report2TpService.getReport2TpByReportDocId(reportDocId);
//        List<Report2Tp> report2TpList = new ArrayList<>();
//        Report2Tp report2tp = new Report2Tp();
//        report2tp.setReportDocId(reportDocId);
//        report2tp.setMeasurement(Measurement.TM3DAY);
//        result.addObject("report2tp", report2tp);
        result.addObject("measurements", new Measurement[]{Measurement.TM3DAY, Measurement.TM3YEAR});
        result.addObject("report2TpWrapper", report2TpWrapper);
//        result.addObject("oldReport2Tp", oldReport2Tp);
//        result.addObject("report2Tps", report2TpList);
//        result.addObject("reportDocId", reportDocId);
        return result;
    }




    @RequestMapping(value = "/submit")
    public String save2Tp(@ModelAttribute Report2TpWrapper report2TpWrapper) throws ParseException {
//    public String save2Tp(@ModelAttribute List<Report2Tp> report2Tps,
//                          @ModelAttribute int reportDocId) throws ParseException {
        report2TpService.saveWrapper(report2TpWrapper);
        //report2TpService.save(report2Tp);
        //return "redirect:/reports/" + report2Tp.getReportDocId();
        return "redirect:/reports/" + report2TpWrapper.getReportDocId();
    }

}
