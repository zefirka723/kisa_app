package mosecom.controller;

import mosecom.dictionaries.DocTypes;
import mosecom.dto.inspections.RegItemProjection;
import mosecom.service.registration.DocumentServiceImpl;
import mosecom.service.registration.RegItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {
    @Autowired
    DocumentServiceImpl documentService;

    @Autowired
    RegItemService regItemService;

    @RequestMapping("/pvmonitoring/springs")
    public String springsInspectionList(Model model, @RequestParam(required = false) Integer state) {
        model.addAttribute("items", state == null ?
                    regItemService.filterRegItemsByType(DocTypes.SPRING_INSPECTION)
                :   regItemService.filterRegIremsByTypeAndState(DocTypes.SPRING_INSPECTION, state));
        model.addAttribute("docType", DocTypes.SPRING_INSPECTION);
        model.addAttribute("header", "Обследования родников"); // добавить в enum описания
        return "registration/list";
    }

    @GetMapping("/filter")
    public String filterList(Model model,
                             @RequestParam DocTypes docType,
                             //@RequestParam String header,
                             @RequestParam (required = false) Integer state) {

        model.addAttribute("items", state == null ?
                regItemService.filterRegItemsByType(docType)
                :   regItemService.filterRegIremsByTypeAndState(docType, state));
        model.addAttribute("docType", docType);
       // model.addAttribute("header", header);
        return "registration/list";
    }



}
