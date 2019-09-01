package mosecom.controller;

import mosecom.dto.licensereport.LicenseReportProjection;
import mosecom.model.licencereport.FlowrateByWell;
import mosecom.model.licencereport.License;
import mosecom.model.licencereport.Measure;
import mosecom.model.licencereport.WaterDepthByWell;
import mosecom.service.licensereport.FlowrateServiceImpl;
import mosecom.service.licensereport.LicenseServiceImpl;
import mosecom.service.licensereport.WaterDepthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class LicenseReportController {

    @Autowired
    LicenseServiceImpl licenseService;

    @Autowired
    FlowrateServiceImpl flowrateService;

    @Autowired
    WaterDepthServiceImpl waterDepthService;

    // общая карточка
    @RequestMapping(value = "/licence-report/{id}")
    public ModelAndView licenceReportView(@PathVariable("id") Integer id) {
        ModelAndView result = new ModelAndView("licence_report/card");
//        LicenseReport licenseReport = licenseReportService.getLicenceReport(id);
//        result.addObject("licenceReport", licenseReport);
        License license = licenseService.getLicense(id);
        result.addObject("license", license);
        result.addObject("links", licenseService.getLinksList(license));
        return result;
    }


    // редактирование атрибутов отчёта
    @RequestMapping(value = "/licence-report/report-edit/{license_id}")
    public ModelAndView editReport(@PathVariable("license_id") Integer license_id) {

        ModelAndView result = new ModelAndView("licence_report/report-edit");

        License license = licenseService.getLicense(license_id);

        LicenseReportProjection report = licenseService.findByLicenseId(license_id);
        report = report != null ? report : new LicenseReportProjection();

        result.addObject("report", report);
        result.addObject("license", license);
        return result;
    }


    // сохранение атрибутов отчёта
    @RequestMapping(value = "/report-edit/report/submit", method = RequestMethod.POST)
    public String reportCardSubmit(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute LicenseReportProjection report,
            @RequestParam int license_id) throws IOException {
        licenseService.save(report, license_id, files);
        return "redirect:/licence-report/" + license_id;
    }


    // водоотборы
    @RequestMapping(value = "/report-edit/flowrate/{wellId}")
    public ModelAndView flowRateEdit(@PathVariable("wellId") Integer wellId) {
        //    ModelAndView result = new ModelAndView("licence_report/flowrate-edit");
        ModelAndView result = new ModelAndView("licence_report/flowrate-edit");
        FlowrateByWell flowrates = new FlowrateByWell();
        flowrates.setWellId(wellId);
        flowrates.setRates(flowrateService.findFlowratesByWellID(wellId));
        result.addObject("flowrates", flowrates);
        return result;
    }

    @RequestMapping(value = "/flowrate/submit", method = RequestMethod.POST)
    public String flowrateSubmit(@ModelAttribute FlowrateByWell flowrates) {
        flowrateService.save(flowrates);
        return"redirect:/fgi";
}



    //глубины
    @RequestMapping(value = "/report-edit/waterdepth/{wellId}")
    public ModelAndView waterDepthEdit(@PathVariable("wellId") Integer wellId) {
        //    ModelAndView result = new ModelAndView("licence_report/flowrate-edit");
        ModelAndView result = new ModelAndView("licence_report/waterdepth-edit");
        WaterDepthByWell waterDepths = new WaterDepthByWell();
        waterDepths.setWellId(wellId);
        waterDepths.setDepths(waterDepthService.findWaterDepthsByWellId(wellId));
        result.addObject("waterDepths", waterDepths);
        return result;
    }

    @RequestMapping(value = "/waterdepth/submit", method = RequestMethod.POST)
    public String waterDepthSubmit(@ModelAttribute WaterDepthByWell waterDepths) {
        waterDepthService.save(waterDepths);
        return"redirect:/fgi";
    }
}