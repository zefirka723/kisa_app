package mosecom.controller;

import mosecom.dao.AttachmentRepository;
import mosecom.dao.HorisontRepository;
import mosecom.dao.licensereport.LicenseToWellsRepository;
import mosecom.model.licencereport.LicenseReport;
import mosecom.service.UserService;
import mosecom.service.licensereport.*;
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
import java.util.Locale;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    UserService userService;

    @Autowired
    LicenseReportServiceImpl licenseReportService;

    @Autowired
    ReportNameServiceImpl reportNameService;

    @Autowired
    LicenseServiceImpl licenseService;

    @Autowired
    Report2TpServiceImpl report2TpService;

    @Autowired
    Report3LsServiceImpl report3LsService;

    @Autowired
    Report4LsServiceImpl report4LsService;

    @Autowired
    HorisontRepository horisontRepository; // TODO: заменить на сервис

    @Autowired
    LicenseToWellsRepository licenseToWellsRepository; // TODO: заменить на сервис

    @Autowired
    AttachmentRepository attachmentRepository;

    @RequestMapping(value = "/list")
    public ModelAndView licenseReportsList() {
        ModelAndView result = new ModelAndView("licence_report/reports-list");
        result.addObject("reports", licenseReportService.findLicenseReportsList());
        result.addObject("currentUserId", userService.getCurrentUserId());
        result.addObject("reportNames", reportNameService.findAllReportNames());
        result.addObject("licenses", licenseService.findAll());
        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createReport(@RequestParam(name = "licenseNumber") String licenseNumber,
                               @RequestParam(name = "reportName") int reportName,
                               @RequestParam(name = "reportDate") String reportDate) throws ParseException {
        LicenseReport report = new LicenseReport();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date formattedDate = format.parse(reportDate);
        report.setDate(formattedDate);
        report.setLicenseDocId(licenseService.findLicenseByNumber(licenseNumber).getId());
        report.setReportName(reportNameService.findOneById(reportName)); // TODO: переделать получение id на объект
        return "redirect:/reports/" + licenseReportService.save(report).getId();
    }

    @RequestMapping(value = "/{reportDocId}", method = RequestMethod.GET)
    public ModelAndView reportCard(@PathVariable(name = "reportDocId") int reportDocId) {
        ModelAndView result = new ModelAndView("licence_report/report-card");
        LicenseReport report = licenseReportService.findOneById(reportDocId);
        switch (report.getReportName().getId()) {
            case (1): //Квартальный
                result.addObject("links",
                        licenseToWellsRepository.findAllByLicense(
                                licenseService.findLicenceByLicenseId(report.getLicenseDocId())));
                break;
            case (2): // Годовой
                result.addObject("reports2tp", report2TpService.getReport2TpByReportDocId(reportDocId));
                result.addObject("reports4Ls", report4LsService.getReport4LsByReportDocId(reportDocId));
                result.addObject("reports3Ls", report3LsService.getReport3LsByReportDocId(reportDocId));
                result.addObject("links",
                        licenseToWellsRepository.findAllByLicense(licenseService.findLicenceByLicenseId(report.getLicenseDocId())));
                break;
            case (3):
                result.addObject("reports4Ls", report4LsService.getReport4LsByReportDocId(reportDocId));
                break;
            case(4):
                result.addObject("reports2tp", report2TpService.getReport2TpByReportDocId(reportDocId));
                break;
            case (5):
                result.addObject("reports3Ls", report3LsService.getReport3LsByReportDocId(reportDocId));
                break;
        }
        if (report.getFileSetId() != null) {
            report.setAttachments(attachmentRepository.findAllByFileSetId(report.getFileSetId()));
        }
        result.addObject("report", report);
        result.addObject("license", licenseService.findLicenceByLicenseId(report.getLicenseDocId()));
        result.addObject("horisonts", horisontRepository.findAllByOrderNotNullOrderByOrderAsc());
        return result;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveFileAndComment(@ModelAttribute (name = "report") LicenseReport report,
                                     @RequestParam(value = "file", required = false) MultipartFile[] files) throws IOException, ParseException {
        licenseReportService.update(report, files);
        return "redirect:/reports/" + report.getId();
    }

}
