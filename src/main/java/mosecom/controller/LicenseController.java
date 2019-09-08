package mosecom.controller;

import mosecom.model.Attachment;
import mosecom.model.licencereport.FlowrateByWell;
import mosecom.model.licencereport.License;
import mosecom.model.licencereport.LicenseReport;
import mosecom.model.licencereport.WaterDepthByWell;
import mosecom.service.AttachmentServiceImpl;
import mosecom.service.licensereport.FlowrateServiceImpl;
import mosecom.service.licensereport.LicenseReportServiceImpl;
import mosecom.service.licensereport.LicenseServiceImpl;
import mosecom.service.licensereport.WaterDepthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class LicenseController {

    @Autowired
    LicenseServiceImpl licenseService;

    @Autowired
    FlowrateServiceImpl flowrateService;

    @Autowired
    WaterDepthServiceImpl waterDepthService;



    // водоотборы
    @RequestMapping(value = "/report-edit/flowrate/{reportId}/{wellId}")
    public ModelAndView flowRateEdit(@PathVariable("wellId") Integer wellId,
                                     @PathVariable("reportId") int reportId) {
        ModelAndView result = new ModelAndView("licence_report/flowrate-edit");
        FlowrateByWell flowrates = new FlowrateByWell();
        flowrates.setWellId(wellId);
        flowrates.setRates(flowrateService.findFlowratesByWellID(wellId));
        result.addObject("flowrates", flowrates);
        result.addObject("reportId", reportId);
        return result;
    }

    @RequestMapping(value = "/flowrate/submit", method = RequestMethod.POST)
    public String flowrateSubmit(@ModelAttribute FlowrateByWell flowrates,
                                 @RequestParam int reportId) {
        flowrateService.save(flowrates);
        return "redirect:/report-card/" + reportId;
}



    //глубины
    @RequestMapping(value = "/report-edit/waterdepth/{reportId}/{wellId}")
    public ModelAndView waterDepthEdit(@PathVariable("wellId") Integer wellId,
                                       @PathVariable("reportId") int reportId) {
        //    ModelAndView result = new ModelAndView("licence_report/flowrate-edit");
        ModelAndView result = new ModelAndView("licence_report/waterdepth-edit");
        WaterDepthByWell waterDepths = new WaterDepthByWell();
        waterDepths.setWellId(wellId);
        waterDepths.setDepths(waterDepthService.findWaterDepthsByWellId(wellId));
        result.addObject("waterDepths", waterDepths);
        result.addObject("reportId", reportId);
        return result;
    }

    @RequestMapping(value = "/waterdepth/submit", method = RequestMethod.POST)
    public String waterDepthSubmit(@ModelAttribute WaterDepthByWell waterDepths,
                                   @RequestParam("reportId") int reportId) {
        waterDepthService.save(waterDepths);
        return "redirect:/report-card/" + reportId;
    }
}