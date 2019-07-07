package mosecom.controller;

import mosecom.dto.inspections.DocumentFullProjection;
import mosecom.dto.inspections.RegItemProjection;
import mosecom.model.inspections.Document;
import mosecom.service.registration.DocumentServiceImpl;
import mosecom.service.registration.RegItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationController {
    @Autowired
    DocumentServiceImpl documentService;

    @Autowired
    RegItemService regItemService;

    //Родники
//    @RequestMapping(value = "/registration/springs-inspection/{id}")
//    public ModelAndView editSpringRegCard(@PathVariable("id") Integer id) {
//        Document document = documentService.getDocument(id);
//        return editInspectionCard(document, "reg-inspections/springs-inspection");
//    }
//
//    @RequestMapping(value = "/list/submit", method = RequestMethod.POST)
//    public String submitSpringsInspection(
//            @RequestParam(value = "file", required = false) MultipartFile[] files,
//            @ModelAttribute DocumentFullProjection document) throws IOException {
//        documentService.save(document, files);
//        return "redirect:/springs-inspection-list/"; //PROD
//    }

    @RequestMapping("/pvmonitoring/springs")
    public String springsInspectionList(Model model, @RequestParam(required = false) Integer state) {
        model.addAttribute("items", regItemService.getRegItemsList());
        return "registration/list";
    }


    // Получение документов нужного типа
    public List<RegItemProjection> getItemsByType(int docType) {
        return regItemService.getRegItemsList().stream()
                .filter(regItemProjection -> regItemProjection.getDocType() == docType)
                .collect(Collectors.toList());
    }


    // Получение документов в нужном статусе (для фильтров)
    private List<RegItemProjection> getItemsByState(Integer state, int docType) {
        List<RegItemProjection> items = regItemService.getRegItemsList().stream()
                .filter(itemProjection -> itemProjection.getDocType() == docType)
                .collect(Collectors.toList());
        if (state == -100 || state == null) {
            return items;
        }
        return items.stream().filter(regItemProjection -> regItemProjection.getRegStatusId() == state)
                .collect(Collectors.toList());
    }


}
