package mosecom.controller.catalog;

import mosecom.dao.inspections.RegStatusRepository;
import mosecom.model.catalog.LicenseReportDoc;
import mosecom.service.UserService;
import mosecom.service.catalog.LicenseReportDocServiceImpl;
import mosecom.service.licensereport.ReportNameServiceImpl;
import mosecom.utils.DateFormatter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static mosecom.utils.ExcelGenerator.createStyleForTitle;

@Controller
public class LicenseDocReportController {

    @Autowired
    LicenseReportDocServiceImpl licenseReportDocService;

    @Autowired
    UserService userService;

    @Autowired
    ReportNameServiceImpl reportNameService;

    @Autowired //TODO: переделать на сервис
            RegStatusRepository regStatusRepository;

    @Value("${upload.path}" + "CATALOG_REPORTS_TEMP/")
    private String uploadPath;

    @GetMapping("/license-report-doc")
    public String getFiltered(Model model,
                              @RequestParam(name = "idFromField", required = false) String idFromField,
                              @RequestParam(name = "regStatusFromField", required = false) String regStatusFromField,
                              @RequestParam(name = "regNumberFromField", required = false) String regNumberFromField,
                              @RequestParam(name = "dateProcessingFromField", required = false) String dateProcessingFromField,
                              @RequestParam(name = "dateProcessingToFromField", required = false) String dateProcessingToFromField,
                              @RequestParam(name = "licenseNumberFromField", required = false) String licenseNumberFromField,
                              @RequestParam(name = "subjectFromField", required = false) String subjectFromField,
                              @RequestParam(name = "dateFromField", required = false) String dateFromField,
                              @RequestParam(name = "dateToFromField", required = false) String dateToFromField,
                              @RequestParam(name = "reportTypeFromField", required = false) String reportTypeFromField,
                              @RequestParam(name = "reportingPeriodFromField", required = false) String reportingPeriodFromField,
                              @RequestParam(name = "have4LSFromField", required = false) String have4LSFromField,
                              @RequestParam(name = "have2TPFromField", required = false) String have2TPFromField,
                              @RequestParam(name = "have3LSFromField", required = false) String have3LSFromField,
                              @RequestParam(name = "quarterRepFromField", required = false) String quarterRepFromField,

                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "itemsByPage", required = false, defaultValue = "25") Integer itemsByPage) throws ParseException {

        if (page < 1) {
            page = 1;
        }

        Page<LicenseReportDoc> licenseReportDocs = licenseReportDocService
                .findAllByPagingAndFiltering(licenseReportDocService.getSpec(
                        idFromField != null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                        regStatusFromField,
                        regNumberFromField,
                        dateProcessingFromField != null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                        dateProcessingToFromField != null && !dateProcessingToFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingToFromField) : null,
                        licenseNumberFromField,
                        subjectFromField,
                        dateFromField != null && !dateFromField.isEmpty() ? DateFormatter.getDateFromString(dateFromField) : null,
                        dateToFromField != null && !dateToFromField.isEmpty() ? DateFormatter.getDateFromString(dateToFromField) : null,
                        reportTypeFromField,
                        //reportingPeriodFromField,
                        have4LSFromField,
                        have2TPFromField,
                        have3LSFromField,
                        quarterRepFromField
                        ),
                        PageRequest.of(page - 1, itemsByPage, Sort.Direction.ASC, "id"));

        // для проверки доступности доков
        Boolean userDspAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        Boolean userConfAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();

        // прячем секретные ссылки
        for (LicenseReportDoc p : licenseReportDocs) {
            if (p.getNeckSecrecy() != null) {
                if (p.getNeckSecrecyId() == 1 && !userDspAllowed) { // ДСП
                    p.setLink("нет доступа");
                }
                if ((p.getNeckSecrecyId() == 2 || p.getNeckSecrecyId() == 3) && !userConfAllowed) { // конфиденциально
                    p.setLink("нет доступа");
                }
            }
        }

        model.addAttribute("licenseReportDocs", licenseReportDocs);
        model.addAttribute("page", page);
        model.addAttribute("itemsByPage", itemsByPage);
        model.addAttribute("filtresString", licenseReportDocService.getFiltresString(
                idFromField != null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                regStatusFromField,
                regNumberFromField,
                dateProcessingFromField != null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                dateProcessingToFromField != null && !dateProcessingToFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingToFromField) : null,
                licenseNumberFromField,
                subjectFromField,
                dateFromField != null && !dateFromField.isEmpty() ? DateFormatter.getDateFromString(dateFromField) : null,
                dateToFromField != null && !dateToFromField.isEmpty() ? DateFormatter.getDateFromString(dateToFromField) : null,
                reportTypeFromField,
                //reportingPeriodFromField,
                have4LSFromField,
                have2TPFromField,
                have3LSFromField,
                quarterRepFromField));
        model.addAttribute("idFromField", idFromField);
        model.addAttribute("regStatusFromField", regStatusFromField);
        model.addAttribute("regNumberFromField", regNumberFromField);
        model.addAttribute("dateProcessingFromField", dateProcessingFromField);
        model.addAttribute("dateProcessingToFromField", dateProcessingToFromField);
        model.addAttribute("licenseNumberFromField", licenseNumberFromField);
        model.addAttribute("subjectFromField", subjectFromField);
        model.addAttribute("dateFromField", dateFromField);
        model.addAttribute("dateToFromField", dateToFromField);
        model.addAttribute("reportTypeFromField", reportTypeFromField);
       // model.addAttribute("reportingPeriodFromField", reportingPeriodFromField);
        model.addAttribute("have4LSFromField", have4LSFromField);
        model.addAttribute("have2TPFromField", have2TPFromField);
        model.addAttribute("have3LSFromField", have3LSFromField);
        model.addAttribute("quarterRepFromField", quarterRepFromField);

        // справочники
        model.addAttribute("regStatuses", regStatusRepository.findAll());
        model.addAttribute("reportTypes", reportNameService.findAllReportNames());

        return "catalog/license-report-doc-table";
    }

    @GetMapping("/license-report-doc-excel")
    public String createFile(Model model,
                             @RequestParam(name = "idFromField", required = false) String idFromField,
                             @RequestParam(name = "regStatusFromField", required = false) String regStatusFromField,
                             @RequestParam(name = "regNumberFromField", required = false) String regNumberFromField,
                             @RequestParam(name = "dateProcessingFromField", required = false) String dateProcessingFromField,
                             @RequestParam(name = "dateProcessingToFromField", required = false) String dateProcessingToFromField,
                             @RequestParam(name = "licenseNumberFromField", required = false) String licenseNumberFromField,
                             @RequestParam(name = "subjectFromField", required = false) String subjectFromField,
                             @RequestParam(name = "dateFromField", required = false) String dateFromField,
                             @RequestParam(name = "dateToFromField", required = false) String dateToFromField,
                             @RequestParam(name = "reportTypeFromField", required = false) String reportTypeFromField,
                          //   @RequestParam(name = "reportingPeriodFromField", required = false) String reportingPeriodFromField,
                             @RequestParam(name = "have4LSFromField", required = false) String have4LSFromField,
                             @RequestParam(name = "have2TPFromField", required = false) String have2TPFromField,
                             @RequestParam(name = "have3LSFromField", required = false) String have3LSFromField,
                             @RequestParam(name = "quarterRepFromField", required = false) String quarterRepFromField
    ) throws ParseException, IOException {

        List<LicenseReportDoc> licenseReportDocs = licenseReportDocService
                .findAllByFiltering(licenseReportDocService.getSpec(
                        idFromField != null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                        regStatusFromField,
                        regNumberFromField,
                        dateProcessingFromField != null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                        dateProcessingToFromField != null && !dateProcessingToFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingToFromField) : null,
                        licenseNumberFromField,
                        subjectFromField,
                        dateFromField != null && !dateFromField.isEmpty() ? DateFormatter.getDateFromString(dateFromField) : null,
                        dateToFromField != null && !dateToFromField.isEmpty() ? DateFormatter.getDateFromString(dateToFromField) : null,
                        reportTypeFromField,
                      //  reportingPeriodFromField,
                        have4LSFromField,
                        have2TPFromField,
                        have3LSFromField,
                        quarterRepFromField));


        // скрываем ссылки ДСП и секретных доков
        Boolean userDspAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        Boolean userConfAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        for (LicenseReportDoc p : licenseReportDocs) {
            if (p.getNeckSecrecy() != null) {
                if (p.getNeckSecrecyId() == 1 && !userDspAllowed) { // ДСП
                    p.setLink("нет доступа");
                }
                if ((p.getNeckSecrecyId() == 2 || p.getNeckSecrecyId() == 3) && !userConfAllowed) { // конфиденциально
                    p.setLink("нет доступа");
                }
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("License report docs");

        int rownum = 0;
        Cell cell;
        Row row;


        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);


        // Для форматирования даты
        CellStyle cellDateStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellDateStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("d/m/yyyy"));


        // заголовки
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Doc_ID");
        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Рег. статус");
        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Рег. номер в фонде");
        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Дата регистрации");
        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("ФИО регистратора");
        cell.setCellStyle(style);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Источник поступления");
        cell.setCellStyle(style);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Номер лицензии");
        cell.setCellStyle(style);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Недропользователь");
        cell.setCellStyle(style);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Дата отчёта");
        cell.setCellStyle(style);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Тип отчёта");
        cell.setCellStyle(style);

//        cell = row.createCell(10, CellType.STRING);
//        cell.setCellValue("Отчётный период");
//        cell.setCellStyle(style);

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("4-ЛС");
        cell.setCellStyle(style);

        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("2-ТП");
        cell.setCellStyle(style);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("3-ЛС");
        cell.setCellStyle(style);

        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Квартальный отчёт");
        cell.setCellStyle(style);

        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Кол-во страниц");
        cell.setCellStyle(style);

        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Гриф");
        cell.setCellStyle(style);

        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Место хранения");
        cell.setCellStyle(style);

        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("Примечания");
        cell.setCellStyle(style);

        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("Файлы");
        cell.setCellStyle(style);


        // данные
        for (LicenseReportDoc p : licenseReportDocs) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(p.getId().toString());

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(p.getRegStatus());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(p.getRegNumber());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellStyle(cellDateStyle);
            if (p.getDateProcessing() != null) {
                cell.setCellValue(p.getDateProcessing());
            }

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(p.getName());

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(p.getOrganizationSource());

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(p.getLicenseNumber());

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(p.getSubject());

            cell = row.createCell(8, CellType.STRING);
            cell.setCellStyle(cellDateStyle);
            if (p.getDate() != null) {
                cell.setCellValue(p.getDate());
            }

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(p.getReportType());

//            cell = row.createCell(10, CellType.STRING);
//            cell.setCellValue(p.getReportingPeriod());

            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue(p.getHave4LS());

            cell = row.createCell(11, CellType.STRING);
            cell.setCellValue(p.getHave2TP());

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(p.getHave3LS());

            cell = row.createCell(13, CellType.STRING);
            cell.setCellValue(p.getQuarterRep());

            cell = row.createCell(14, CellType.STRING);
            cell.setCellValue(p.getPages());

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue(p.getNeckSecrecy());

            cell = row.createCell(16, CellType.STRING);
            cell.setCellValue(p.getStorage());

            cell = row.createCell(17, CellType.STRING);
            cell.setCellValue(p.getComments());

            cell = row.createCell(18, CellType.STRING);
            cell.setCellValue(p.getLink());
        }

        String filePath = uploadPath + userService.getCurrentUserId() + "/Report.xls";

        File file = new File(filePath);

        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        outFile.close();
        model.addAttribute("filePath", filePath);
        return "redirect:" + "/download/file/" + userService.getCurrentUserId() + "/Report.xls";

    }


}