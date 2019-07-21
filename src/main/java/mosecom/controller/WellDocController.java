package mosecom.controller;

import mosecom.dictionaries.DocTypes;
import mosecom.dto.WellFullProjection;
import mosecom.model.Well;
import mosecom.model.inspections.Document;
import mosecom.model.inspections.RegItem;
import mosecom.service.registration.DocumentServiceImpl;
import mosecom.service.registration.RegItemService;
import mosecom.service.welldoc.WellServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class WellDocController {
//    @Autowired
//    DocumentServiceImpl documentService;
//
//    @Autowired
//    RegItemService regItemService;

    @Autowired
    WellServiceImpl wellService;


    // редактирование учётки
    @RequestMapping(value = "/welldoc/{docType}/{id}")
    public ModelAndView wellDocCard(@PathVariable("id") Integer id,
                                 @PathVariable("docType") DocTypes docType) {
        ModelAndView result = new ModelAndView("welldoc/card");
        Well well = wellService.getWell(id);
        result.addObject("well", well);
        result.addObject("docType", docType);
        result.addObject("constructionTypes", wellService.getAllConstructionTypes());
        result.addObject("diametrs", wellService.getAllDiametrs());
        result.addObject("horisonts", wellService.getAllHorisonts());
        result.addObject("movedTypes", wellService.getAllMovedTypes());
        return result;
    }

//    // Сабмит формы
    @RequestMapping(value = "/welldoc/submit", method = RequestMethod.POST)
    public String wellDocCardSubmit(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @RequestParam DocTypes docType,
            @ModelAttribute WellFullProjection well) throws IOException {
        wellService.save(well, files, docType);
        return "redirect:/registrations?docType=" + docType +"&state=0";
    }

    // создание (пока не нужно)
//    @RequestMapping(value = "/add-card")
//    public ModelAndView addCard() {
//        return editCard(new Well());
//    }
}