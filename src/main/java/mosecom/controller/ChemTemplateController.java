package mosecom.controller;

import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import mosecom.service.licensereport.TemplateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChemTemplateController {

    @Autowired
    private TemplateServiceImpl templateService;

    @RequestMapping(value = "/chem-templates", method = RequestMethod.GET)
    public ModelAndView templatesList() {
        ModelAndView result = new ModelAndView("licence_report/templates-list");

        result.addObject("templatesList", templateService.findTemplateInfoList());
        return result;
    }

    @RequestMapping(value = "/chem-template", method = RequestMethod.GET)
    public ModelAndView createTemplate(@RequestParam(value = "id", required = false) Integer id) {
        ModelAndView result = new ModelAndView("licence_report/template-edit");
        //ChemTemplate template = new ChemTemplate();
        ChemTemplateInfo template = id == null? new ChemTemplateInfo() : templateService.findTemplateInfoById(id) ;
        result.addObject("laboratories", templateService.findLaboratoryList());
        result.addObject("components", templateService.findComponentList());
        result.addObject("template", template);
        return result;
    }

    @RequestMapping(value = "/chem-template-submit", method = RequestMethod.POST)
    public String submitTemplate(@ModelAttribute ChemTemplateInfo template) {
        templateService.save(template);
        return "redirect:/chem-templates";
    }
}