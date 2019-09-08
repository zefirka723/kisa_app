package mosecom.controller;

import mosecom.dao.AttachmentRepository;
import mosecom.dao.licensereport.LicenseRepository;
import mosecom.model.Attachment;
import mosecom.model.licencereport.LicenseReport;
import mosecom.service.UserService;
import mosecom.service.licensereport.LicenseReportServiceImpl;
import mosecom.service.licensereport.LicenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class LicenseReportController {

    @Autowired
    LicenseReportServiceImpl licenseReportService;

    @Autowired
    LicenseServiceImpl licenseService;

    @Autowired
    UserService userService;

    @Autowired
    LicenseRepository licenseRepository;

    @Autowired
    AttachmentRepository attachmentRepository;


    @RequestMapping(value = "/licence-reports")
    public ModelAndView licenseReportsList() {
        ModelAndView result = new ModelAndView("licence_report/reports-list");
        result.addObject("reports", licenseReportService.findLicenseReportsList());
        result.addObject("currentUserId", userService.getCurrentUserId());
        return result;
    }


    @RequestMapping(value = "/report-add")
    public ModelAndView addReport(@RequestParam (name="licenseNumber") String licenseNumber,
                                  @RequestParam (name="reportDate") String reportDate) throws ParseException {
        ModelAndView result = new ModelAndView("licence_report/report-edit");
        LicenseReport report = new LicenseReport();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date formattedDate = format.parse(reportDate);
        report.setDate(formattedDate);
       // List<Attachment> attachments = null;
        //result.addObject("attachments", attachments);
        result.addObject("report", report);
        result.addObject("licenseNumber", licenseNumber);
        result.addObject("currentUserId", userService.getCurrentUserId());
        return result;
    }


    @RequestMapping(value = "/report-edit/{id}")
    public ModelAndView editReport(@PathVariable(name = "id") int id) {
        ModelAndView result = new ModelAndView("licence_report/report-edit");
        LicenseReport report = licenseReportService.findReportById(id);
        String licenseNumber = report.getLicense().getLicenseNumber();
        List<Attachment> attachments = attachmentRepository.findAllByFileSetId(report.getFileSetId());
        result.addObject("report", report);
        result.addObject("currentUserId", userService.getCurrentUserId());
        result.addObject("licenseNumber", licenseNumber);
        result.addObject("attachments", attachments);
        return result;
    }


    @RequestMapping(value = "/report-edit/report/submit", method = RequestMethod.POST)
    public String reportCardSubmit(
            @RequestParam(value = "file", required = false) MultipartFile[] attachments,
            @ModelAttribute LicenseReport report,
            @RequestParam String licenseNumber) throws IOException {
        licenseService.save(report, licenseNumber, attachments);//licenseProjection, attachments);
        return "redirect:/report-card/" + report.getId();
    }

    @RequestMapping(value = "/report-card/{id}")
    public ModelAndView reportCard(@PathVariable(name = "id") int id) {
        ModelAndView result = new ModelAndView("licence_report/card");
        LicenseReport report = licenseReportService.findReportById(id);
        result.addObject("files", attachmentRepository.findAllByFileSetId(report.getFileSetId()));
        result.addObject("report", report);
        result.addObject("currentUserId", userService.getCurrentUserId());
        result.addObject("links", licenseService.getLinksList(report.getLicense()));
        return result;
    }
}