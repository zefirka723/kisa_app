package mosecom.controller;

import mosecom.dao.licensereport.dictionaries.ChemComponentRepository;
import mosecom.model.licencereport.ChemTemplateItem;
import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import mosecom.service.licensereport.TemplateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ChemTemplateController {

    @Autowired
    private TemplateServiceImpl templateService;

    @Autowired
    private ChemComponentRepository componentRepository; // TODO: заменить на сервис

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
    //public String submitTemplate(@ModelAttribute ChemTemplateInfo template, BindingResult result, ModelMap model) {
    public ModelAndView submitTemplate(@ModelAttribute ChemTemplateInfo template, BindingResult result, ModelMap model) {

        // проверяем на дубли
        Set<Integer> uniqueItems = new HashSet<Integer>();
        List<String> duplicatedItems = new ArrayList<String>();
        for (ChemTemplateItem item : template.getChemItems()) {
            if (uniqueItems.contains(item.getParametrId())) {
                duplicatedItems.add(componentRepository.getOne(item.getParametrId()).getName());
            }
            else {
                uniqueItems.add(item.getParametrId());
            }
        }

        if(!duplicatedItems.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("licence_report/template-edit");
            modelAndView.addObject("laboratories", templateService.findLaboratoryList());
            modelAndView.addObject("components", templateService.findComponentList());
            modelAndView.addObject("template", template);
            modelAndView.addObject("duplicatedItems", duplicatedItems);
            return modelAndView;
        }

        templateService.save(template);
        //return "redirect:/chem-templates";
        return templatesList();
    }
}