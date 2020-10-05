package mosecom.controller;

import mosecom.dao.licensereport.ChemRepository;
import mosecom.model.licencereport.ChemWrapper;
import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import mosecom.service.licensereport.ChemServiceImpl;
import mosecom.service.licensereport.TemplateServiceImpl;
import mosecom.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.Date;

@Controller
public class ChemController {

    @Autowired
    private TemplateServiceImpl templateService;

    @Autowired
    private ChemServiceImpl chemService;


    // Список химий по скв
    //TODO: переделать переход на предыдущую страницу по header вместо reportId
    @RequestMapping(value = "report-edit/chem/{reportId}/{wellId}", method = RequestMethod.GET)
    public ModelAndView chemsList(@PathVariable(value = "wellId") Integer wellId,
                                  @PathVariable(value = "reportId") Integer reportId) {
        ModelAndView result = new ModelAndView("licence_report/chems-list");
        result.addObject("reportId", reportId);
        result.addObject("wellId", wellId);
        result.addObject("chemTemplates", templateService.findTemplateInfoList());
        result.addObject("wellChems", chemService.getWellChemsByWellId(wellId));
        return result;
    }



    // создание новой химии по шаблону
    @RequestMapping(value = "/report-edit/chem/{reportId}", method = RequestMethod.GET)
    public ModelAndView createChem(@PathVariable Integer reportId,
                                   @RequestParam(name = "wellId") int wellId,
                                   @RequestParam(name = "templateId") int templateId,
                                   @RequestParam(name = "date") String date) throws ParseException {
        ModelAndView result = new ModelAndView("licence_report/chem-edit");
        ChemWrapper wrapper =
                chemService.prepareChemWrapperByTemplate(templateId, wellId, date);
        result.addObject("components", templateService.findComponentList());
        result.addObject("wrapper", wrapper);
        result.addObject("reportId", reportId);
        return result;
    }

    @RequestMapping(value = "/chem-submit", method = RequestMethod.POST)
    public String submitTemplate(@ModelAttribute ChemWrapper wrapper,
                                 @RequestParam Integer reportId) {
        //wrapper.setReportDocId(reportId);
        wrapper.setReportId(reportId);
        chemService.save(wrapper);
        return "redirect:report-edit/chem/" + reportId + "/" + wrapper.getWellId();

    }
}