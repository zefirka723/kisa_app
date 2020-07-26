package mosecom.controller;

import mosecom.dictionaries.DocTypes;
import mosecom.dto.WellFullProjection;
import mosecom.model.Well;
import mosecom.model.licencereport.WaterDepth;
import mosecom.service.licensereport.WaterDepthServiceImpl;
import mosecom.service.welldoc.WellServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
public class WellDocController {

    @Autowired
    WellServiceImpl wellService;

    @Autowired
    WaterDepthServiceImpl waterDepthService;


    // редактирование учётки
    @RequestMapping(value = "/welldoc/{docType}/{id}")
    public ModelAndView wellDocCard(@PathVariable("id") Integer id,
                                 @PathVariable("docType") DocTypes docType) {
        ModelAndView result = new ModelAndView("welldoc/card");
        /* пока не определена бизнес-логика по добыванию глубины, изящное извлечение по id
         * будет заменено на кое-что поядрёнее */
        //Well well = wellService.getWell(id);
        Well well = wellService.prepareWellForWellDoc(id);

        List<WaterDepth> depthsList = waterDepthService.findWaterDepthsByWellId(id);
        well.setDepthsList(depthsList);

        result.addObject("well", well);
        result.addObject("docType", docType);
        result.addObject("constructionTypes", wellService.getAllConstructionTypes());
        result.addObject("diametrs", wellService.getAllDiametrs());
        //result.addObject("horisonts", wellService.getAllHorisonts());
        result.addObject("horisonts", wellService.getAllHorisontsByOrder());
        result.addObject("movedTypes", wellService.getAllMovedTypes());
//        result.addObject("depthsList", waterDepthService.findWaterDepthsByWellId(id));
//        result.addObject("depthsByWell", depthsByWell);
        return result;
    }

//    // Сабмит формы
    @RequestMapping(value = "/welldoc/submit", method = RequestMethod.POST)
    public String wellDocCardSubmit(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @RequestParam DocTypes docType,
            @ModelAttribute WellFullProjection well
            ) throws IOException, ParseException {
        wellService.save(well, files, docType);
        //, depthsByWell);
        return "redirect:/registrations?docType=" + docType +"&state=0";
    }

    // создание (пока не нужно)
//    @RequestMapping(value = "/add-card")
//    public ModelAndView addCard() {
//        return editCard(new Well());
//    }
}