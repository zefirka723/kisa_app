package mosecom.controller.catalog;

import mosecom.dao.catalog.PrimaryDocRepository;
import mosecom.dao.catalog.dictionaties.TypeObservRepository;
import mosecom.dao.inspections.RegStatusRepository;
import mosecom.dao.inspections.SecrecyRepository;
import mosecom.model.catalog.PrimaryDoc;
import mosecom.service.UserService;
import mosecom.service.catalog.DocumentTypeServiceImpl;
import mosecom.service.catalog.PrimaryDocServiceImpl;
import mosecom.service.registration.DocumentServiceImpl;
import mosecom.utils.DateFormatter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static mosecom.utils.ExcelGenerator.createStyleForTitle;

@Controller
//@RequestMapping("/primary")
public class PrimaryDocController {

    @Autowired
    PrimaryDocServiceImpl primaryDocService;

    @Autowired
    UserService userService;

    @Autowired
    RegStatusRepository regStatusRepository; // TODO: переделать на сервисы тут и далее

    @Autowired
    TypeObservRepository typeObservRepository;

    @Autowired
    DocumentTypeServiceImpl documentTypeService;

    @Value("${upload.path}" + "CATALOG_REPORTS_TEMP/")
    private String uploadPath;

//    @RequestMapping(value = "/")
//    public ModelAndView primaryDocList() {
//
//        ModelAndView result = new ModelAndView("catalog/primary");
//        result.addObject("primaryDocs", primaryDocService.findAll());
//        return result;
//    }

    @GetMapping("/primary")
    public String getFiltered(Model model,
                              @RequestParam(name = "idFromField", required = false) String idFromField,
                              @RequestParam(name = "regStatusFromField", required = false) String regStatusFromField,
                              @RequestParam(name = "regNumberFromField", required = false) String regNumberFromField,
                              @RequestParam(name = "dateProcessingFromField", required = false) String dateProcessingFromField,
                              @RequestParam(name = "dateProcessingToFromField", required = false) String dateProcessingToFromField,
                              @RequestParam(name = "typeObservFromField", required = false) String typeObservFromField,
                              @RequestParam(name = "observIdFromField", required = false) String observIdFromField,
                              @RequestParam(name = "docTypeFromField", required = false) String docTypeFromField,
                              @RequestParam(name = "datePrepareFromField", required = false) String datePrepareFromField,
                              @RequestParam(name = "datePrepareToFromField", required = false) String datePrepareToFromField,
                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "itemsByPage", required = false, defaultValue = "25") Integer itemsByPage) throws ParseException {


        if (page < 1) {
            page = 1;
        }

        Page<PrimaryDoc> primaryDocs = primaryDocService
                .findAllByPagingAndFiltering(primaryDocService.getSpec(
                        idFromField != null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                        regStatusFromField,
                        regNumberFromField,
                        dateProcessingFromField != null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                        dateProcessingToFromField != null && !dateProcessingToFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingToFromField) : null,
                        typeObservFromField,
                        observIdFromField,
                        docTypeFromField,
                        datePrepareFromField != null && !datePrepareFromField.isEmpty() ? DateFormatter.getDateFromString(datePrepareFromField) : null,
                        datePrepareToFromField != null && !datePrepareToFromField.isEmpty() ? DateFormatter.getDateFromString(datePrepareToFromField) : null),
                        PageRequest.of(page - 1, itemsByPage, Sort.Direction.ASC, "id"));

        // скрываем ссылки ДСП и секретных доков
        Boolean userDspAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        Boolean userConfAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();

        for (PrimaryDoc p : primaryDocs) {
            if (p.getNeckSecrecy() != null) {
                if (p.getNeckSecrecyId() == 1 && !userDspAllowed) { // ДСП
                    p.setLink("нет доступа");
                }
                if ((p.getNeckSecrecyId() == 2 || p.getNeckSecrecyId() == 3) && !userConfAllowed) { // конфиденциально
                    p.setLink("нет доступа");
                }
            }
        }

        model.addAttribute("primaryDocs", primaryDocs);
        model.addAttribute("page", page);
        model.addAttribute("itemsByPage", itemsByPage);
        model.addAttribute("filtresString", primaryDocService.getFiltresString(
                idFromField != null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                regStatusFromField,
                regNumberFromField,
                dateProcessingFromField != null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                dateProcessingToFromField != null && !dateProcessingToFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingToFromField) : null,
                typeObservFromField,
                observIdFromField,
                docTypeFromField,
                datePrepareFromField != null && !datePrepareFromField.isEmpty() ? DateFormatter.getDateFromString(datePrepareFromField) : null,
                datePrepareToFromField != null && !datePrepareToFromField.isEmpty() ? DateFormatter.getDateFromString(datePrepareToFromField) : null));
        model.addAttribute("idFromField", idFromField);
        model.addAttribute("regStatusFromField", regStatusFromField);
        model.addAttribute("regNumberFromField", regNumberFromField);
        model.addAttribute("dateProcessingFromField", dateProcessingFromField);
        model.addAttribute("dateProcessingToFromField", dateProcessingToFromField);
        model.addAttribute("typeObservFromField", typeObservFromField);
        model.addAttribute("observIdFromField", observIdFromField);
        model.addAttribute("docTypeFromField", docTypeFromField);
        model.addAttribute("datePrepareFromField", datePrepareFromField);
        model.addAttribute("datePrepareToFromField", datePrepareToFromField);

        //справочники
        model.addAttribute("regStatuses", regStatusRepository.findAll());
        model.addAttribute("observTypes", typeObservRepository.findAllByOrderByNameAsc());

        int[] docTypesForPrimary = new int[]{1001, 1002, 1003, 1004, 1101, 1102, 1201, 1202, 1203, 1900, 2001,
                2002, 2003, 2004, 2005, 2101, 2102, 2201, 3001, 3002, 3003, 3004,
                3005, 3006, 3007, 8001
        };
        model.addAttribute("docTypes", documentTypeService.getTypesByIds(docTypesForPrimary));

        return "catalog/primary-table";
    }

    @GetMapping("/primary-excel")
    public String createFile(Model model,
                             @RequestParam(name = "idFromField", required = false) String idFromField,
                             @RequestParam(name = "regStatusFromField", required = false) String regStatusFromField,
                             @RequestParam(name = "regNumberFromField", required = false) String regNumberFromField,
                             @RequestParam(name = "dateProcessingFromField", required = false) String dateProcessingFromField,
                             @RequestParam(name = "dateProcessingToFromField", required = false) String dateProcessingToFromField,
                             @RequestParam(name = "typeObservFromField", required = false) String typeObservFromField,
                             @RequestParam(name = "observIdFromField", required = false) String observIdFromField,
                             @RequestParam(name = "docTypeFromField", required = false) String docTypeFromField,
                             @RequestParam(name = "datePrepareFromField", required = false) String datePrepareFromField,
                             @RequestParam(name = "datePrepareToFromField", required = false) String datePrepareToFromField//,
    ) throws ParseException, IOException {


        List<PrimaryDoc> primaryDocs = primaryDocService
                .findAllByFiltering(primaryDocService.getSpec(
                        idFromField != null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                        regStatusFromField,
                        regNumberFromField,
                        dateProcessingFromField != null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                        dateProcessingToFromField != null && !dateProcessingToFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingToFromField) : null,
                        typeObservFromField,
                        observIdFromField,
                        docTypeFromField,
                        datePrepareFromField != null && !datePrepareFromField.isEmpty() ? DateFormatter.getDateFromString(datePrepareFromField) : null,
                        datePrepareToFromField != null && !datePrepareToFromField.isEmpty() ? DateFormatter.getDateFromString(datePrepareToFromField) : null));


        // скрываем ссылки ДСП и секретных доков
        Boolean userDspAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        Boolean userConfAllowed = userService.getUser(userService.getCurrentUserId()).getIsOfficialUseAllowed();
        for (PrimaryDoc p : primaryDocs) {
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
        HSSFSheet sheet = workbook.createSheet("Primary docs");

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
        cell.setCellValue("Тип ПН");
        cell.setCellStyle(style);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Номер/название ПН");
        cell.setCellStyle(style);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Тип документа");
        cell.setCellStyle(style);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Дата составления документа");
        cell.setCellStyle(style);

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Кол-во страниц");
        cell.setCellStyle(style);

        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Гриф");
        cell.setCellStyle(style);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Ссылка на файл");
        cell.setCellStyle(style);

        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Место хранения");
        cell.setCellStyle(style);

        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Примечания");
        cell.setCellStyle(style);


        // данные
        for (PrimaryDoc p : primaryDocs) {
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
            cell.setCellValue(p.getTypeObserv());

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(p.getObservId());

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(p.getDocType());

            cell = row.createCell(9, CellType.STRING);
            cell.setCellStyle(cellDateStyle);
            if (p.getDatePrepare() != null) {
                cell.setCellValue(p.getDatePrepare());
            }

            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue(p.getPages());

            cell = row.createCell(11, CellType.STRING);
            cell.setCellValue(p.getNeckSecrecy());

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(p.getLink());

            cell = row.createCell(13, CellType.STRING);
            cell.setCellValue(p.getStorage());

            cell = row.createCell(14, CellType.STRING);
            cell.setCellValue(p.getComments());

        }

        String filePath = uploadPath + userService.getCurrentUserId() + "/Report.xls";

        File file = new File(filePath);

        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        outFile.close();
        //System.out.println("Created file: " + file.getAbsolutePath());
        model.addAttribute("filePath", filePath);
        model.addAttribute("file", file);
        return "redirect:" + "/download/file/" + userService.getCurrentUserId() + "/Report.xls";

    }

}