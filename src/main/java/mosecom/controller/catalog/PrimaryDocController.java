package mosecom.controller.catalog;

import mosecom.dao.licensereport.dictionaries.PeriodRepository;
import mosecom.model.catalog.PrimaryDoc;
import mosecom.model.licencereport.Flowrate;
import mosecom.model.licencereport.FlowrateWrapper;
import mosecom.model.licencereport.dictionary.Measurement;
import mosecom.service.catalog.PrimaryDocServiceImpl;
import mosecom.service.licensereport.FlowrateServiceImpl;
import mosecom.service.licensereport.LicenseReportServiceImpl;
import mosecom.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@Controller
//@RequestMapping("/primary")
public class PrimaryDocController {

    @Autowired
    PrimaryDocServiceImpl primaryDocService;

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
                              @RequestParam(name = "typeObservFromField", required = false) String typeObservFromField,
                              @RequestParam(name = "observIdFromField", required = false) String observIdFromField,
                              @RequestParam(name = "docTypeFromField", required = false) String docTypeFromField,
                              @RequestParam(name = "datePrepareFromField", required = false) String datePrepareFromField,
                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "itemsByPage", required = false, defaultValue = "25") Integer itemsByPage) throws ParseException {

        Page<PrimaryDoc> primaryDocs = primaryDocService
                .findAllByPagingAndFiltering(primaryDocService.getSpec(
                        idFromField!=null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                                                                       regStatusFromField,
                                                                       regNumberFromField,
                        dateProcessingFromField!=null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                                                                       typeObservFromField,
                                                                       observIdFromField,
                                                                       docTypeFromField,
                        datePrepareFromField != null && !datePrepareFromField.isEmpty() ? DateFormatter.getDateFromString(datePrepareFromField) : null),
                        PageRequest.of(page - 1, itemsByPage, Sort.Direction.ASC, "id"));

        model.addAttribute("primaryDocs", primaryDocs);
        model.addAttribute("page", page);
        model.addAttribute("itemsByPage", itemsByPage);
        model.addAttribute("filtresString", primaryDocService.getFiltresString(
                idFromField!=null && !idFromField.isEmpty() ? Integer.parseInt(idFromField) : null,
                                                                                        regStatusFromField,
                                                                                        regNumberFromField,
                datePrepareFromField != null && !dateProcessingFromField.isEmpty() ? DateFormatter.getDateFromString(dateProcessingFromField) : null,
                                                                                        typeObservFromField,
                                                                                        observIdFromField,
                                                                                        docTypeFromField,
                datePrepareFromField != null && !datePrepareFromField.isEmpty() ? DateFormatter.getDateFromString(datePrepareFromField) : null));
        model.addAttribute("idFromField", idFromField);
        model.addAttribute("regStatusFromField", regStatusFromField);
        model.addAttribute("regNumberFromField", regNumberFromField);
        model.addAttribute("dateProcessingFromField", dateProcessingFromField);
        model.addAttribute("typeObservFromField", typeObservFromField);
        model.addAttribute("observIdFromField", observIdFromField);
        model.addAttribute("docTypeFromField", docTypeFromField);
        model.addAttribute("datePrepareFromField", datePrepareFromField);
        return "catalog/primary-table";
    }

}
