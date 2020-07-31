package mosecom.controller;

import mosecom.dao.AttachmentRepository;
import mosecom.dao.licensereport.dictionaries.ConditionReporitory;
import mosecom.model.licencereport.PluggingAct;
import mosecom.model.licencereport.WellCondition;
import mosecom.model.licencereport.WellConditionWrapper;
import mosecom.service.AttachmentServiceImpl;
import mosecom.service.PluggingActServiceImpl;
import mosecom.service.licensereport.ConditionServiceImpl;
import mosecom.service.licensereport.WellConditionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/wellconditions")
public class WellConditionController {

    @Autowired
    WellConditionServiceImpl wellConditionService;

    @Autowired
    ConditionServiceImpl conditionService;

    @Autowired
    PluggingActServiceImpl pluggingActService;

    @Autowired
    AttachmentServiceImpl attachmentService;

    @RequestMapping(value = "/{reportDocId}/{wellId}")
    public ModelAndView wellConditions(@PathVariable(name = "reportDocId") int reportDocId,
                                       @PathVariable(name = "wellId") int wellId) {
        ModelAndView result = new ModelAndView("licence_report/well-conditions");
        WellConditionWrapper conditionWrapper = new WellConditionWrapper();
        conditionWrapper.setReportDocId(reportDocId);
        conditionWrapper.setWellId(wellId);
        conditionWrapper.setOldConditions(wellConditionService.getWellConditionsByWellId(wellId));
        WellCondition condition = new WellCondition();
        conditionWrapper.setConditions(new ArrayList<>());
        conditionWrapper.getConditions().add(condition);
        PluggingAct act;
        if(pluggingActService.findOneByWellId(wellId) == null) {
            act = new PluggingAct();
            act.setWellId(wellId);
        }
        else {
            act = pluggingActService.findOneByWellId(wellId);
            act.setAttachments(attachmentService.findAllByFileSetId(act.getFileSetId())); // edited
        }
        result.addObject("conditionWrapper", conditionWrapper);
        result.addObject("act", act);
        result.addObject("conditions", conditionService.getAllConditions());
        return result;
    }

    @RequestMapping(value = "/submit")
    public String saveConditions(@ModelAttribute WellConditionWrapper wellConditionWrapper) {
        wellConditionService.saveWrapper(wellConditionWrapper);
        return "redirect:/wellconditions/" + wellConditionWrapper.getReportDocId() + '/' + wellConditionWrapper.getWellId();
    }

}
