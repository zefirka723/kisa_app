package mosecom.controller;

import mosecom.dao.licensereport.dictionaries.PeriodRepository;
import mosecom.model.licencereport.Flowrate;
import mosecom.model.licencereport.FlowrateWrapper;
import mosecom.model.licencereport.Report2Tp;
import mosecom.model.licencereport.Report2TpWrapper;
import mosecom.model.licencereport.dictionary.Measurement;
import mosecom.service.licensereport.FlowrateServiceImpl;
import mosecom.service.licensereport.LicenseReportServiceImpl;
import mosecom.service.licensereport.LicenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
@RequestMapping("/flowrate")
public class FlowrateController {

    @Autowired
    FlowrateServiceImpl flowrateService;

    @Autowired
    LicenseReportServiceImpl licenseReportService;

    @Autowired
    PeriodRepository periodRepository;

    @RequestMapping(value = "/{reportDocId}/{wellId}")
    public ModelAndView flowrateEdit(@PathVariable(name = "reportDocId") int reportDocId,
                                     @PathVariable(name = "wellId") int wellId) {

        ModelAndView result = new ModelAndView("licence_report/flowrate");
        FlowrateWrapper flowrateWrapper = new FlowrateWrapper();
        flowrateWrapper.setWellId(wellId);
        flowrateWrapper.setReportDocId(reportDocId);
        flowrateWrapper.setOldFlowrates(flowrateService.findFlowratesByWellID(wellId));
        Flowrate flowrate = new Flowrate();
        flowrate.setMeasurement(Measurement.M3DAY);
        flowrateWrapper.setFlowrates(new ArrayList<>());
        flowrateWrapper.getFlowrates().add(flowrate);
        result.addObject("measurements", new Measurement[]{Measurement.TM3DAY, Measurement.M3HOUR, Measurement.LSEK, Measurement.M3MONTH});
        result.addObject("flowrateWrapper", flowrateWrapper);
        result.addObject("periods", periodRepository.findAll());
        return result;
    }


    @RequestMapping(value = "/submit")
    public String saveFlowrates(@ModelAttribute FlowrateWrapper flowrateWrapper) throws ParseException {
        flowrateService.saveWrapper(flowrateWrapper);
        return "redirect:/reports/" + flowrateWrapper.getReportDocId();
    }
}
