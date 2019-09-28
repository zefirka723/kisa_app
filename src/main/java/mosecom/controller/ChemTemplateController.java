package mosecom.controller;

import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import mosecom.service.licensereport.TemplateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChemTemplateController {

    @Autowired
    private TemplateServiceImpl templateService;

    @RequestMapping(value = "/chem-template/{id}", method = RequestMethod.GET)
    public ModelAndView createTemplate(@PathVariable(value = "id", required = false) Integer id) {
        ModelAndView result = new ModelAndView("licence_report/template-edit");
        //ChemTemplate template = new ChemTemplate();
        ChemTemplateInfo template = id == null? new ChemTemplateInfo() : templateService.findTemplateInfoById(id) ;
        result.addObject("laboratories", templateService.findLaboratoryList());
        result.addObject("components", templateService.findComponentList());
        result.addObject("template", template);
        return result;
    }

    @RequestMapping(value = "/chem-template", method = RequestMethod.POST)
    public String submitTemplate(@ModelAttribute ChemTemplateInfo template) {
        templateService.save(template);
        return "redirect:/fgi";
    }
}