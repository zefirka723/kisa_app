package mosecom.controller;

import mosecom.dictionaries.DocTypes;
import mosecom.model.Attachment;
import mosecom.model.licencereport.PluggingAct;
import mosecom.service.AttachmentServiceImpl;
import mosecom.service.PluggingActServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class PluggingActController {

    @Autowired
    PluggingActServiceImpl pluggingActService;

    @Autowired
    AttachmentServiceImpl attachmentService;

    @RequestMapping(value = "/plugging/{wellId}/{reportDocId}")
    public ModelAndView wellDocCard(@PathVariable("wellId") Integer wellId,
                                    @PathVariable(value = "reportDocId") Integer reportDocId) {
                                    //@ModelAttribute("reportDocId") Integer reportId) {
        ModelAndView result = new ModelAndView("licence_report/plugging-act");
        PluggingAct act;
        if(pluggingActService.findOneByWellId(wellId) == null) {
            act = new PluggingAct();
            act.setWellId(wellId);
        }
        else {
            act = pluggingActService.findOneByWellId(wellId);
            Attachment attachment = attachmentService.findOneByFileSetId(act.getFileSetId());
            act.setLink("/file/" + attachment.getId());
        }

        if(reportDocId != 0) {
            act.setReportDocId(reportDocId);
        }

        act.setDocType(DocTypes.PLUGGING_ACT);
        result.addObject("act", act);
        return result;
    }


    @RequestMapping(value = "/plugging/submit")
    public String saveFlowrates(@ModelAttribute PluggingAct act,
                                @RequestParam(value = "files", required = false) MultipartFile[] files) throws ParseException, IOException {
        pluggingActService.save(act, files);
        if(act.getReportDocId() == null) {
            return "redirect:/registrations?docType=" + act.getDocType();
        }
        else {
            return "redirect:/wellconditions/" + act.getReportDocId() + '/' + act.getWellId();
        }
    }

}