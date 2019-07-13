package mosecom.controller;

import mosecom.dictionaries.DocTypes;
//import mosecom.dto.inspections.DocumentFullProjection;
import mosecom.dto.inspections.DocumentProjection;
import mosecom.dto.inspections.RegItemProjection;
import mosecom.model.inspections.Document;
import mosecom.model.inspections.RegItem;
import mosecom.service.registration.DocumentServiceImpl;
import mosecom.service.registration.RegItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.print.Doc;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {
    @Autowired
    DocumentServiceImpl documentService;

    @Autowired
    RegItemService regItemService;

    // Главная страница регистрации
    @GetMapping("/registration")
    public String registrationMain() {
        return "registration/reg-home";
    }

    // Список документов для регистрации
    @GetMapping("/registrations")
    public String registrationList(Model model,
                             @RequestParam DocTypes docType,
                             //@RequestParam String header,
                             @RequestParam (required = false) Integer state) {

        model.addAttribute("items", state == null ?
                regItemService.filterRegIremsByTypeAndState(docType, 0)
                :   regItemService.filterRegIremsByTypeAndState(docType, state));
        model.addAttribute("docType", docType);
        model.addAttribute("header", docType.getListName());
        return "registration/reg-list";
    }


    // Регистрация
    @RequestMapping(value = "/registration/{docType}/{id}")
    public ModelAndView registrationCard(@PathVariable("id") Integer id,
                                    @PathVariable DocTypes docType) {
        Document document = documentService.getDocument(id);
        RegItem item = regItemService.getRegItem(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        ModelAndView result = new ModelAndView("registration/reg-card");
        result.addObject("user", auth.getPrincipal());
        result.addObject("globalDocType", docType);//DocTypes.getOneById(document.getId()));
        result.addObject("item", item);
        result.addObject("document", document);
        result.addObject("employees", documentService.getAllEmployees());
        result.addObject("organizationSource", documentService.getAllOrganizationSource());
        result.addObject("status", documentService.getAllRegStatus());
        result.addObject("secrecy", documentService.getAllSecrecy());
        return result;
    }

    @RequestMapping(value = "/registration/submit", method = RequestMethod.POST)
    public String registrationSubmit(
            @RequestParam DocTypes globalDocType,
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute DocumentProjection document) throws IOException {
        documentService.save(document, files);
        //return "registration/reg-home";
        return "redirect:/registrations?docType=" + globalDocType +"&state=0";
    }
}
