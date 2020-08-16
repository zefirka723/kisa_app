package mosecom.controller.catalog;

import mosecom.model.catalog.LicenseDoc;
import mosecom.model.catalog.PrimaryDoc;
import mosecom.service.UserService;
import mosecom.service.catalog.LicenseDocServiceImpl;
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
//@RequestMapping("/primary")
public class LicenseDocController {

    @Autowired
    LicenseDocServiceImpl licenseDocService;

    @Autowired
    UserService userService;

    @Value("${upload.path}" + "CATALOG_REPORTS_TEMP/")
    private String uploadPath;

    @GetMapping("/license-doc")
    public String getFiltered(Model model,
                              @RequestParam(name = "idFromField", required = false) String idFromField,
                              @RequestParam(name = "regStatusFromField", required = false) String regStatusFromField,
                              @RequestParam(name = "regNumberFromField", required = false) String regNumberFromField,
                              @RequestParam(name = "dateProcessingFromField", required = false) String dateProcessingFromField,
                              @RequestParam(name = "licenseNumberFromField", required = false) String licenseNumberFromField,
                              @RequestParam(name = "organizationsFromField", required = false) String organizationsFromField,
                              @RequestParam(name = "subjectFromField", required = false) String subjectFromField,
                              @RequestParam(name = "statusFromField", required = false) String statusFromField,
                              @RequestParam(name = "dateStartFromField", required = false) String dateStartFromField,
                              @RequestParam(name = "dateEndFromField", required = false) String dateEndFromField,
                              @RequestParam(name = "flowRateSummFromField", required = false) String flowRateSummFromField,
                              @RequestParam(name = "commentsDocsFromField", required = false) String commentsDocsFromField,

                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "itemsByPage", required = false, defaultValue = "25" ) Integer itemsByPage) throws ParseException {

        if (page < 1) {
            page = 1;
        }

        Page<LicenseDoc> licenseDocs = licenseDocService
                .findAllByPagingAndFiltering(licenseDocService.getSpec(
                        idFromField!=null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                                                                       regStatusFromField,
                                                                       regNumberFromField,
                        dateProcessingFromField!=null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                                                                        licenseNumberFromField,
                                                                        organizationsFromField,
                                                                        subjectFromField,
                                                                        statusFromField,
                        dateStartFromField!=null && !dateStartFromField.isEmpty() ? DateFormatter.getDateFromString(dateStartFromField) : null,
                        dateEndFromField!=null && !dateEndFromField.isEmpty() ? DateFormatter.getDateFromString(dateEndFromField) : null,
                        flowRateSummFromField!= null && !flowRateSummFromField.isEmpty() ? Float.parseFloat(flowRateSummFromField) : null,
                                                                        commentsDocsFromField

                        ),
                        PageRequest.of(page - 1, itemsByPage, Sort.Direction.ASC, "id"));

        // для проверки доступности доков
        Boolean userDspAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        Boolean userConfAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();

        // обрезаем коммент и прячем секретные ссылки
        for (LicenseDoc p: licenseDocs) {
            p.setCommentsLicenseForFront(p.getCommentsLicense().length() > 45 ?
                    p.getCommentsLicense().substring(0, 45)+"..."
                    : p.getCommentsLicense()
            );
            if (p.getNeckSecrecy() != null) {
                if (p.getNeckSecrecy().equals("Для служебного пользования") && !userDspAllowed) {
                    p.setLink("нет доступа");
                }
                if ((p.getNeckSecrecy().equals("Конфиденциально в течение 5 лет") || p.getNeckSecrecy().equals("Конфиденциально в течение 7 лет"))
                        && !userConfAllowed) {
                    p.setLink("нет доступа");
                }
            }
        }

        model.addAttribute("licenseDocs", licenseDocs);
        model.addAttribute("page", page);
        model.addAttribute("itemsByPage", itemsByPage);
        model.addAttribute("filtresString", licenseDocService.getFiltresString(
                idFromField!=null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                regStatusFromField,
                regNumberFromField,
                dateProcessingFromField!=null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                licenseNumberFromField,
                organizationsFromField,
                subjectFromField,
                statusFromField,
                dateStartFromField!=null && !dateStartFromField.isEmpty() ? DateFormatter.getDateFromString(dateStartFromField) : null,
                dateEndFromField!=null && !dateEndFromField.isEmpty() ? DateFormatter.getDateFromString(dateEndFromField) : null,
                flowRateSummFromField!= null && !flowRateSummFromField.isEmpty() ? Float.parseFloat(flowRateSummFromField) : null,
                commentsDocsFromField));
        model.addAttribute("idFromField", idFromField);
        model.addAttribute("regStatusFromField", regStatusFromField);
        model.addAttribute("regNumberFromField", regNumberFromField);
        model.addAttribute("dateProcessingFromField", dateProcessingFromField);
        model.addAttribute("licenseNumberFromField", licenseNumberFromField);
        model.addAttribute("organizationsFromField", organizationsFromField);
        model.addAttribute("subjectFromField", subjectFromField);
        model.addAttribute("statusFromField", statusFromField);
        model.addAttribute("dateStartFromField", dateStartFromField);
        model.addAttribute("dateEndFromField", dateEndFromField);
        model.addAttribute("flowRateSummFromField", flowRateSummFromField);
        model.addAttribute("commentsDocsFromField", commentsDocsFromField);
        return "catalog/license-doc-table";
    }

    @GetMapping("/license-doc-excel")public String createFile(Model model,
                                                              @RequestParam(name = "idFromField", required = false) String idFromField,
                                                              @RequestParam(name = "regStatusFromField", required = false) String regStatusFromField,
                                                              @RequestParam(name = "regNumberFromField", required = false) String regNumberFromField,
                                                              @RequestParam(name = "dateProcessingFromField", required = false) String dateProcessingFromField,
                                                              @RequestParam(name = "licenseNumberFromField", required = false) String licenseNumberFromField,
                                                              @RequestParam(name = "organizationsFromField", required = false) String organizationsFromField,
                                                              @RequestParam(name = "subjectFromField", required = false) String subjectFromField,
                                                              @RequestParam(name = "statusFromField", required = false) String statusFromField,
                                                              @RequestParam(name = "dateStartFromField", required = false) String dateStartFromField,
                                                              @RequestParam(name = "dateEndFromField", required = false) String dateEndFromField,
                                                              @RequestParam(name = "flowRateSummFromField", required = false) String flowRateSummFromField,
                                                              @RequestParam(name = "commentsDocsFromField", required = false) String commentsDocsFromField
                                            ) throws ParseException, IOException {

        List<LicenseDoc> licenseDocs = licenseDocService
                .findAllByFiltering(licenseDocService.getSpec(
                        idFromField!=null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                        regStatusFromField,
                        regNumberFromField,
                        dateProcessingFromField!=null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                        licenseNumberFromField,
                        organizationsFromField,
                        subjectFromField,
                        statusFromField,
                        dateStartFromField!=null && !dateStartFromField.isEmpty() ? DateFormatter.getDateFromString(dateStartFromField) : null,
                        dateEndFromField!=null && !dateEndFromField.isEmpty() ? DateFormatter.getDateFromString(dateEndFromField) : null,
                        flowRateSummFromField!= null && !flowRateSummFromField.isEmpty() ? Float.parseFloat(flowRateSummFromField) : null,
                        commentsDocsFromField));


        // скрываем ссылки ДСП и секретных доков
        Boolean userDspAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        Boolean userConfAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        for (LicenseDoc p: licenseDocs) {
            if (p.getNeckSecrecy() != null) {
                if (p.getNeckSecrecy().equals("Для служебного пользования") && !userDspAllowed) {
                    p.setLink("нет доступа");
                }
                if ((p.getNeckSecrecy().equals("Конфиденциально в течение 5 лет") || p.getNeckSecrecy().equals("Конфиденциально в течение 7 лет"))
                        && !userConfAllowed) {
                    p.setLink("нет доступа");
                }
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("License docs");

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
        cell.setCellValue("Орган, выдавший лицензию");
        cell.setCellStyle(style);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Недропользователь");
        cell.setCellStyle(style);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Статус лицензии");
        cell.setCellStyle(style);

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Регистрация лицензии");
        cell.setCellStyle(style);

        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Окончание лицензии");
        cell.setCellStyle(style);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Разреш. среднесут. водоотбор (м3/сут)");
        cell.setCellStyle(style);

        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Прим. к лицензии");
        cell.setCellStyle(style);

        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Прим. при регистрации/местополож. участка");
        cell.setCellStyle(style);

        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Кол-во страниц");
        cell.setCellStyle(style);

        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Гриф");
        cell.setCellStyle(style);

        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("Место хранения");
        cell.setCellStyle(style);

        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("Файлы");
        cell.setCellStyle(style);


        // данные
        for (LicenseDoc p : licenseDocs) {
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
            if(p.getDateProcessing() != null) {
                cell.setCellValue(p.getDateProcessing());
            }

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(p.getName());

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(p.getOrganizationSource());

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(p.getLicenseNumber());

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(p.getOrganizations());

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(p.getSubject());

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(p.getStatus());

            cell = row.createCell(10, CellType.STRING);
            if(p.getDateStart() != null) {
                cell.setCellValue(p.getDateStart());
            }

            cell = row.createCell(11, CellType.STRING);
            if(p.getDateEnd() != null) {
                cell.setCellValue(p.getDateEnd());
            }

            cell = row.createCell(12, CellType.STRING);
            if(p.getFlowRateSumm() != null) {
                cell.setCellValue(p.getFlowRateSumm());
            }

            cell = row.createCell(13, CellType.STRING);
            cell.setCellValue(p.getCommentsLicense());

            cell = row.createCell(14, CellType.STRING);
            cell.setCellValue(p.getCommentsDocs());

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue(p.getPages());

            cell = row.createCell(16, CellType.STRING);
            cell.setCellValue(p.getNeckSecrecy());

            cell = row.createCell(17, CellType.STRING);
            cell.setCellValue(p.getStorage());

            cell = row.createCell(18, CellType.STRING);
            cell.setCellValue(p.getLink());
        }

        String filePath = uploadPath + userService.getCurrentUserId() + "/Report.xls";

        File file = new File(filePath);

        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        outFile.close();
        //System.out.println("Created file: " + file.getAbsolutePath());
        model.addAttribute("filePath", filePath);
        return "catalog/primary-file";

    }


}