package mosecom.controller.catalog;

import mosecom.model.catalog.LicenseDoc;
import mosecom.model.catalog.ProtocolDoc;
import mosecom.service.UserService;
import mosecom.service.catalog.LicenseDocServiceImpl;
import mosecom.service.catalog.ProtocolDocServiceImpl;
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
public class ProtocolDocController {

    @Autowired
    ProtocolDocServiceImpl docService;

    @Autowired
    UserService userService;

    @Value("${upload.path}" + "CATALOG_REPORTS_TEMP/")
    private String uploadPath;

    @GetMapping("/protocol-doc")
    public String getFiltered(Model model,
                              @RequestParam(name = "idFromField", required = false) String idFromField,
                              @RequestParam(name = "regStatusFromField", required = false) String regStatusFromField,
                              @RequestParam(name = "regNumberFromField", required = false) String regNumberFromField,
                              @RequestParam(name = "dateProcessingFromField", required = false) String dateProcessingFromField,
                              @RequestParam(name = "subjectFromField", required = false) String subjectFromField,
                              @RequestParam(name = "instanceFromField", required = false) String instanceFromField,
                              @RequestParam(name = "numberFromField", required = false) String numberFromField,
                              @RequestParam(name = "dateFromField", required = false) String dateFromField,
                              @RequestParam(name = "licenseNumberFromField", required = false) String licenseNumberFromField,
                              @RequestParam(name = "fieldGeneralNameFromField", required = false) String fieldGeneralNameFromField,
                              @RequestParam(name = "fieldNameFromField", required = false) String fieldNameFromField,

                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "itemsByPage", required = false, defaultValue = "25" ) Integer itemsByPage) throws ParseException {

        if (page < 1) {
            page = 1;
        }

        Page<ProtocolDoc> docs = docService
                .findAllByPagingAndFiltering(docService.getSpec(
                        idFromField!=null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                                                                       regStatusFromField,
                                                                       regNumberFromField,
                        dateProcessingFromField!=null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                        subjectFromField,
                        instanceFromField,
                        numberFromField,
                        dateFromField,
                        licenseNumberFromField,
                        fieldGeneralNameFromField,
                        fieldNameFromField),
                        PageRequest.of(page - 1, itemsByPage, Sort.Direction.ASC, "id"));

        // для проверки доступности доков
        Boolean userDspAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        Boolean userConfAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();

        // обрезаем коммент и прячем секретные ссылки
        for (ProtocolDoc p: docs) {
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

        model.addAttribute("docs", docs);
        model.addAttribute("page", page);
        model.addAttribute("itemsByPage", itemsByPage);
        model.addAttribute("filtresString", docService.getFiltresString(
                idFromField!=null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                regStatusFromField,
                regNumberFromField,
                dateProcessingFromField!=null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                subjectFromField,
                instanceFromField,
                numberFromField,
                dateFromField,
                licenseNumberFromField,
                fieldGeneralNameFromField,
                fieldNameFromField));
        model.addAttribute("idFromField", idFromField);
        model.addAttribute("regStatusFromField", regStatusFromField);
        model.addAttribute("regNumberFromField", regNumberFromField);
        model.addAttribute("dateProcessingFromField", dateProcessingFromField);
        model.addAttribute("subjectFromField", subjectFromField);
        model.addAttribute("instanceFromField", instanceFromField);
        model.addAttribute("numberFromField", numberFromField);
        model.addAttribute("dateFromField", dateFromField);
        model.addAttribute("licenseNumberFromField", licenseNumberFromField);
        model.addAttribute("fieldGeneralNameFromField", fieldGeneralNameFromField);
        model.addAttribute("fieldNameFromField", fieldNameFromField);
        return "catalog/protocol-doc-table";
    }

    @GetMapping("/protocol-doc-excel")public String createFile(Model model,
                                                               @RequestParam(name = "idFromField", required = false) String idFromField,
                                                               @RequestParam(name = "regStatusFromField", required = false) String regStatusFromField,
                                                               @RequestParam(name = "regNumberFromField", required = false) String regNumberFromField,
                                                               @RequestParam(name = "dateProcessingFromField", required = false) String dateProcessingFromField,
                                                               @RequestParam(name = "subjectFromField", required = false) String subjectFromField,
                                                               @RequestParam(name = "instanceFromField", required = false) String instanceFromField,
                                                               @RequestParam(name = "numberFromField", required = false) String numberFromField,
                                                               @RequestParam(name = "dateFromField", required = false) String dateFromField,
                                                               @RequestParam(name = "licenseNumberFromField", required = false) String licenseNumberFromField,
                                                               @RequestParam(name = "fieldGeneralNameFromField", required = false) String fieldGeneralNameFromField,
                                                               @RequestParam(name = "fieldNameFromField", required = false) String fieldNameFromField
                                            ) throws ParseException, IOException {

        List<ProtocolDoc> docs = docService
                .findAllByFiltering(docService.getSpec(
                        idFromField!=null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                        regStatusFromField,
                        regNumberFromField,
                        dateProcessingFromField!=null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                        subjectFromField,
                        instanceFromField,
                        numberFromField,
                        dateFromField,
                        licenseNumberFromField,
                        fieldGeneralNameFromField,
                        fieldNameFromField));


        // скрываем ссылки ДСП и секретных доков
        Boolean userDspAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        Boolean userConfAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        for (ProtocolDoc p: docs) {
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
        HSSFSheet sheet = workbook.createSheet("Protocol docs");

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
        cell.setCellValue("Предмет рассмотрения");
        cell.setCellStyle(style);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Инстанция утверждения");
        cell.setCellStyle(style);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("№ протокола");
        cell.setCellStyle(style);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Дата утверждения");
        cell.setCellStyle(style);

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Лицензия");

        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Месторождение");
        cell.setCellStyle(style);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Участок месторождения");
        cell.setCellStyle(style);

        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Кол-во страниц");
        cell.setCellStyle(style);

        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Гриф");
        cell.setCellStyle(style);

        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Место хранения");
        cell.setCellStyle(style);

        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Примечания");
        cell.setCellStyle(style);

        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("Файлы");
        cell.setCellStyle(style);

        // данные
        for (ProtocolDoc p : docs) {
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
            cell.setCellValue(p.getSubject());

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(p.getInstance());

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(p.getNumber());

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(p.getDate());

            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue(p.getLicenseNumber());

            cell = row.createCell(11, CellType.STRING);
            cell.setCellValue(p.getFieldGeneralName());

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(p.getFieldName());

            cell = row.createCell(13, CellType.STRING);
            cell.setCellValue(p.getPages());

            cell = row.createCell(14, CellType.STRING);
            cell.setCellValue(p.getNeckSecrecy());

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue(p.getStorage());

            cell = row.createCell(16, CellType.STRING);
            cell.setCellValue(p.getComment());

            cell = row.createCell(17, CellType.STRING);
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