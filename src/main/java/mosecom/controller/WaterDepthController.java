package mosecom.controller;

import mosecom.dao.licensereport.dictionaries.PeriodRepository;
import mosecom.model.licencereport.Flowrate;
import mosecom.model.licencereport.FlowrateWrapper;
import mosecom.model.licencereport.WaterDepth;
import mosecom.model.licencereport.WaterDepthWrapper;
import mosecom.model.licencereport.dictionary.Measurement;
import mosecom.service.licensereport.FlowrateServiceImpl;
import mosecom.service.licensereport.LicenseReportServiceImpl;
import mosecom.service.licensereport.WaterDepthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
@RequestMapping("/waterdepth")
public class WaterDepthController {

    @Autowired
    WaterDepthServiceImpl waterDepthService;

    @RequestMapping(value = "/{reportDocId}/{wellId}")
    public ModelAndView waterDepthEdit(@PathVariable(name = "reportDocId") int reportDocId,
                                     @PathVariable(name = "wellId") int wellId) {

        ModelAndView result = new ModelAndView("licence_report/waterdepth");
        WaterDepthWrapper waterDepthWrapper = new WaterDepthWrapper();
        waterDepthWrapper.setWellId(wellId);
        waterDepthWrapper.setReportDocId(reportDocId);

        waterDepthWrapper.setOldWaterDepths(waterDepthService.findWaterDepthsByWellIdOrderByDate(wellId));

        WaterDepth waterDepth = new WaterDepth();
        waterDepthWrapper.setWaterDepths(new ArrayList<>());
        waterDepthWrapper.getWaterDepths().add(waterDepth);

        result.addObject("waterDepthWrapper", waterDepthWrapper);
        return result;
    }


    @RequestMapping(value = "/submit")
    public String saveWaterDepths(@ModelAttribute WaterDepthWrapper waterDepthWrapper) throws ParseException {
        waterDepthService.saveWrapper(waterDepthWrapper);
        return "redirect:/reports/" + waterDepthWrapper.getReportDocId();
    }
}
