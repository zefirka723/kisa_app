package mosecom.controller.catalog;

import mosecom.dao.inspections.OrganizationSourceRepository;
import mosecom.dao.inspections.RegStatusRepository;
import mosecom.model.catalog.ConclusionDoc;
import mosecom.model.catalog.OtherDoc;
import mosecom.service.UserService;
import mosecom.service.catalog.DocumentTypeServiceImpl;
import mosecom.service.catalog.OtherDocServiceImpl;
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
public class OtherDocController {

    @Autowired
    OtherDocServiceImpl docService;

    @Autowired
    UserService userService;

    @Autowired
    DocumentTypeServiceImpl documentTypeService;

    @Autowired // TODO:  переделать на сервис
    RegStatusRepository regStatusRepository;

    @Autowired
    OrganizationSourceRepository organizationSourceRepository;

    @Value("${upload.path}" + "CATALOG_REPORTS_TEMP/")
    private String uploadPath;

    @GetMapping("/other-doc")
    public String getFiltered(Model model,
                              @RequestParam(name = "idFromField", required = false) String idFromField,
                              @RequestParam(name = "regStatusFromField", required = false) String regStatusFromField,
                              @RequestParam(name = "regNumberFromField", required = false) String regNumberFromField,
                              @RequestParam(name = "dateProcessingFromField", required = false) String dateProcessingFromField,
                              @RequestParam(name = "dateProcessingToFromField", required = false) String dateProcessingToFromField,
                              @RequestParam(name = "organizationSourceFromField", required = false) String organizationSourceFromField,
                              @RequestParam(name = "docTypeFromField", required = false) String docTypeFromField,
                              @RequestParam(name = "reportNameFromField", required = false) String reportNameFromField,
                              @RequestParam(name = "authorFromField", required = false) String authorFromField,
                              @RequestParam(name = "organizationAuthorFromField", required = false) String organizationAuthorFromField,
                              @RequestParam(name = "compilationYearFromField", required = false) String compilationYearFromField,
                              @RequestParam(name = "compilationYearToFromField", required = false) String compilationYearToFromField,

                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "itemsByPage", required = false, defaultValue = "25" ) Integer itemsByPage) throws ParseException {

        if (page < 1) {
            page = 1;
        }

        Page<OtherDoc> docs = docService
                .findAllByPagingAndFiltering(docService.getSpec(
                        idFromField!=null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                                                                       regStatusFromField,
                                                                       regNumberFromField,
                        dateProcessingFromField!=null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                        dateProcessingToFromField!=null && !dateProcessingToFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingToFromField) : null,
                        organizationSourceFromField,
                        docTypeFromField,
                        reportNameFromField,
                        authorFromField,
                        organizationAuthorFromField,
                        compilationYearFromField!=null && !compilationYearFromField.isEmpty() ? Integer.parseInt(compilationYearFromField) : null,
                        compilationYearToFromField!=null && !compilationYearToFromField.isEmpty() ? Integer.parseInt(compilationYearToFromField) : null),
                        PageRequest.of(page - 1, itemsByPage, Sort.Direction.ASC, "id"));

        // для проверки доступности доков
        Boolean userDspAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        Boolean userConfAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();

        // обрезаем коммент и прячем секретные ссылки
        for (OtherDoc p: docs) {
            if(p.getReportName() != null) {
                p.setReportNameForFront(p.getReportName().length() > 50 ?
                        p.getReportName().substring(0, 50) + "..."
                        : p.getReportName());
            }

            if (p.getNeckSecrecy() != null) {
                if (p.getNeckSecrecyId() == 1 && !userDspAllowed) { // ДСП
                    p.setLink("нет доступа");
                }
                if ((p.getNeckSecrecyId() == 2 || p.getNeckSecrecyId() == 3) && !userConfAllowed) { // конфиденциально
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
                dateProcessingToFromField!=null && !dateProcessingToFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingToFromField) : null,
                organizationSourceFromField,
                docTypeFromField,
                reportNameFromField,
                authorFromField,
                organizationAuthorFromField,
                compilationYearFromField!=null && !compilationYearFromField.isEmpty() ? Integer.parseInt(compilationYearFromField) : null,
                compilationYearToFromField!=null && !compilationYearToFromField.isEmpty() ? Integer.parseInt(compilationYearToFromField) : null));
        model.addAttribute("idFromField", idFromField);
        model.addAttribute("regStatusFromField", regStatusFromField);
        model.addAttribute("regNumberFromField", regNumberFromField);
        model.addAttribute("dateProcessingFromField", dateProcessingFromField);
        model.addAttribute("dateProcessingToFromField", dateProcessingToFromField);
        model.addAttribute("organizationSourceFromField", organizationSourceFromField);
        model.addAttribute("docTypeFromField", docTypeFromField);
        model.addAttribute("reportNameFromField", reportNameFromField);
        model.addAttribute("authorFromField", authorFromField);
        model.addAttribute("organizationAuthorFromField", organizationAuthorFromField);
        model.addAttribute("compilationYearFromField", compilationYearFromField);
        model.addAttribute("compilationYearToFromField", compilationYearToFromField);

        // справочники
        model.addAttribute("regStatuses", regStatusRepository.findAll());
        model.addAttribute("organizations", organizationSourceRepository.findAll());
        int[] docTypesForOtherDocs = new int[]{4003, 4005, 4006, 4007, 6001, 7001, 8002, 8003};
        model.addAttribute("docTypes", documentTypeService.getTypesByIds(docTypesForOtherDocs));

        return "catalog/other-doc-table";
    }

    @GetMapping("/other-doc-excel")public String createFile(Model model,
                                                            @RequestParam(name = "idFromField", required = false) String idFromField,
                                                            @RequestParam(name = "regStatusFromField", required = false) String regStatusFromField,
                                                            @RequestParam(name = "regNumberFromField", required = false) String regNumberFromField,
                                                            @RequestParam(name = "dateProcessingFromField", required = false) String dateProcessingFromField,
                                                            @RequestParam(name = "dateProcessingToFromField", required = false) String dateProcessingToFromField,
                                                            @RequestParam(name = "organizationSourceFromField", required = false) String organizationSourceFromField,
                                                            @RequestParam(name = "docTypeFromField", required = false) String docTypeFromField,
                                                            @RequestParam(name = "reportNameFromField", required = false) String reportNameFromField,
                                                            @RequestParam(name = "authorFromField", required = false) String authorFromField,
                                                            @RequestParam(name = "organizationAuthorFromField", required = false) String organizationAuthorFromField,
                                                            @RequestParam(name = "compilationYearFromField", required = false) String compilationYearFromField,
                                                            @RequestParam(name = "compilationYearToFromField", required = false) String compilationYearToFromField
                                            ) throws ParseException, IOException {

        List<OtherDoc> docs = docService
                .findAllByFiltering(docService.getSpec(
                        idFromField!=null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                        regStatusFromField,
                        regNumberFromField,
                        dateProcessingFromField!=null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                        dateProcessingToFromField!=null && !dateProcessingToFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingToFromField) : null,
                        organizationSourceFromField,
                        docTypeFromField,
                        reportNameFromField,
                        authorFromField,
                        organizationAuthorFromField,
                        compilationYearFromField!=null && !compilationYearFromField.isEmpty() ? Integer.parseInt(compilationYearFromField) : null,
                        compilationYearToFromField!=null && !compilationYearToFromField.isEmpty() ? Integer.parseInt(compilationYearToFromField) : null));


        // скрываем ссылки ДСП и секретных доков
        Boolean userDspAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        Boolean userConfAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        for (OtherDoc p: docs) {
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
        HSSFSheet sheet = workbook.createSheet("Other docs");

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
        cell.setCellValue("Тип отчёта");
        cell.setCellStyle(style);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Название отчёта");
        cell.setCellStyle(style);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Организация-исполнитель");
        cell.setCellStyle(style);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Автор");
        cell.setCellStyle(style);

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Место составления");
        cell.setCellStyle(style);

        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Год составления");
        cell.setCellStyle(style);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Наличие бумажной версии");
        cell.setCellStyle(style);

        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Наличие цифровой версии");
        cell.setCellStyle(style);

        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Количество страниц");
        cell.setCellStyle(style);

        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Количество листов графики");
        cell.setCellStyle(style);

        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Гриф");
        cell.setCellStyle(style);

        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("Место хранения");
        cell.setCellStyle(style);

        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("Примечания");
        cell.setCellStyle(style);

        cell = row.createCell(19, CellType.STRING);
        cell.setCellValue("Файлы");
        cell.setCellStyle(style);

        // данные
        for (OtherDoc p : docs) {
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
            cell.setCellValue(p.getDocType());

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(p.getReportName());

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(p.getOrganizationAuthor());

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(p.getAuthor());

            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue(p.getCompilationSite());

            cell = row.createCell(11, CellType.STRING);
            if(p.getCompilationYear() != null) {
                cell.setCellValue(p.getCompilationYear());
            }

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(p.getPaperVersion());

            cell = row.createCell(13, CellType.STRING);
            cell.setCellValue(p.getDigitalVersion());

            cell = row.createCell(14, CellType.STRING);
            cell.setCellValue(p.getPages());

            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue(p.getNumberOfGraphic());

            cell = row.createCell(16, CellType.STRING);
            cell.setCellValue(p.getNeckSecrecy());

            cell = row.createCell(17, CellType.STRING);
            cell.setCellValue(p.getStorage());

            cell = row.createCell(18, CellType.STRING);
            cell.setCellValue(p.getComment());

            cell = row.createCell(19, CellType.STRING);
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
        return "redirect:" + "/download/file/" + userService.getCurrentUserId() + "/Report.xls";

    }


}