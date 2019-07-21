package mosecom.controller;

import mosecom.dictionaries.DocTypes;
import mosecom.dto.inspections.DocumentProjection;
import mosecom.model.inspections.Document;
import mosecom.model.inspections.RegItem;
import mosecom.service.UserService;
import mosecom.service.registration.DocumentServiceImpl;
import mosecom.service.registration.RegItemService;
import mosecom.service.welldoc.WellServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.print.Doc;
import java.io.IOException;


@Controller
public class RegistrationController {
    @Autowired
    DocumentServiceImpl documentService;

    @Autowired
    RegItemService regItemService;

    @Autowired
    WellServiceImpl wellService;

    @Autowired
    UserService userService;

    // Главная страница модуля ФГИ
    @GetMapping("/fgi")
    public String registrationMain(Model model) {
        model.addAttribute("currentUserId", userService.getCurrentUserId()); // TODO переделать на роли
        return "registration/reg-home";
    }

    // Список документов для регистрации
    @GetMapping("/registrations")
    public String registrationList(Model model,
                             @RequestParam DocTypes docType,
                             @RequestParam (required = false) Integer state) {

        model.addAttribute("items", state == null ?
                    regItemService.filterRegIremsByTypeAndState(docType, 0)
                :   regItemService.filterRegIremsByTypeAndState(docType, state));
        model.addAttribute("docType", docType);
        model.addAttribute("header", docType.getListName());
        boolean isWellDoc = false;
        if (docType == DocTypes.RECCARD || docType == DocTypes.PASSPORT || docType == DocTypes.DESCRIPTION) {
            model.addAttribute("wellsList", wellService.getWellsList());
            isWellDoc = true;
        }
        model.addAttribute("isWellDoc", isWellDoc);
        model.addAttribute("currentUserId", userService.getCurrentUserId()); // TODO переделать на роли
        return "registration/reg-list";
    }


    // Форма регистрации
    @RequestMapping(value = "/registration/{docType}/{id}")
    public ModelAndView registrationCard(@PathVariable("id") Integer id,
                                         @PathVariable DocTypes docType) {
        Document document = documentService.getDocument(id);
        RegItem item = regItemService.getRegItem(id);
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView result = new ModelAndView("registration/reg-card");
        //result.addObject("user", auth.getPrincipal());
        result.addObject("globalDocType", docType);
        result.addObject("item", item);
        result.addObject("document", document);
        result.addObject("employees", documentService.getAllEmployees());
        result.addObject("organizationSource", documentService.getAllOrganizationSource());
        result.addObject("status", documentService.getAllRegStatus());
        result.addObject("secrecy", documentService.getAllSecrecy());
        return result;
    }


    // Сохраненине формы регистрации
    @RequestMapping(value = "/registration/submit", method = RequestMethod.POST)
    public String registrationSubmit(
            @RequestParam DocTypes globalDocType,
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute DocumentProjection document) throws IOException {
        documentService.save(document, files);
        return "redirect:/registrations?docType=" + globalDocType +"&state=0";
    }
}
