package mosecom.controller;

import mosecom.dao.HorisontRepository;
import mosecom.model.licencereport.Report3Ls;
import mosecom.model.licencereport.Report3LsWrapper;
import mosecom.model.licencereport.dictionary.Measurement;
import mosecom.service.licensereport.Report3LsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
@RequestMapping("/report3ls")
public class Report3LsController {

    @Autowired
    Report3LsServiceImpl report3LsService;

    @Autowired
    HorisontRepository horisontRepository; // TODO: заменить

    @RequestMapping(value = "/{reportDocId}")
    public ModelAndView licenseReport3Ls(@PathVariable(name = "reportDocId") int reportDocId) {
        ModelAndView result = new ModelAndView("licence_report/report3ls");
        Report3LsWrapper report3LsWrapper = new Report3LsWrapper();
        report3LsWrapper.setReportDocId(reportDocId);
        report3LsWrapper.setOldReport3Ls(report3LsService.getReport3LsByReportDocId(reportDocId));

        Report3Ls report3Ls = new Report3Ls();
        report3Ls.setMeasurement(Measurement.TM3DAY);
        report3LsWrapper.setReport3LsList(new ArrayList<>());
        report3LsWrapper.getReport3LsList().add(report3Ls);

        result.addObject("measurements", new Measurement[]{Measurement.TM3DAY, Measurement.TM3YEAR});
        result.addObject("report3LsWrapper", report3LsWrapper);
        result.addObject("horisonts", horisontRepository.findAllByOrderNotNullOrderByOrderAsc());
        return result;
    }




    @RequestMapping(value = "/submit")
    public String save2Tp(@ModelAttribute Report3LsWrapper report3LsWrapper) throws ParseException {
        report3LsService.saveWrapper(report3LsWrapper);
        return "redirect:/reports/" + report3LsWrapper.getReportDocId();
    }

}
