package mosecom.service.licensereport;

import mosecom.dao.licensereport.ChemRepository;
import mosecom.dao.licensereport.WellChemsRepository;
import mosecom.dao.licensereport.dictionaries.ChemComponentRepository;
import mosecom.dao.licensereport.dictionaries.ChemTemplateInfoRepository;
import mosecom.dao.licensereport.dictionaries.ChemTemplateItemRepository;
import mosecom.dao.licensereport.dictionaries.LaboratoryRepository;
import mosecom.model.licencereport.Chem;
import mosecom.model.licencereport.ChemTemplateItem;
import mosecom.model.licencereport.ChemWrapper;
import mosecom.model.licencereport.WellChems;
import mosecom.model.licencereport.dictionary.ChemComponent;
import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import mosecom.model.licencereport.dictionary.Laboratory;
import mosecom.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ChemServiceImpl {

    @Autowired
    private ChemRepository chemRepository;

    @Autowired
    private TemplateServiceImpl templateService;

    @Autowired
    private ChemComponentRepository componentRepository;

    @Autowired
    private WellChemsRepository wellChemsRepository;

    public List<WellChems> getWellChemsByWellId(int wellId) {
        return wellChemsRepository.findAllByWellId(wellId);
    }


    public ChemWrapper prepareChemWrapperByTemplate(int templateId, int wellId, String date) throws ParseException {
        ChemTemplateInfo template = templateService.findTemplateInfoById(templateId);
        ChemWrapper wrapper = new ChemWrapper();
        wrapper.setDate(date);
        wrapper.setWellId(wellId);
        wrapper.setChems(new ArrayList<>());
        wrapper.setTemplateId(templateId);
        for (ChemTemplateItem i : template.getChemItems()) {
            Chem chem;
            if (findChemByParams(wellId, DateFormatter.getDateFromString(date), i.getParametrId()) != null) {
                chem = findChemByParams(wellId, DateFormatter.getDateFromString(date), i.getParametrId());
                chem.setTooLow(chem.getValue() == null);
            } else {
                chem = new Chem();
                chem.setParametrId(i.getParametrId());
            }
            chem.setParamName(componentRepository.getOne(i.getParametrId()).getName());
            chem.setMeasure(componentRepository.getOne(i.getParametrId()).getMeasure());
            wrapper.getChems().add(chem);
        }
        return wrapper;
    }


    public Chem findChemByParams(int wellId, Date date, int parametrId) {
        return chemRepository.findByWellIdAndDateAndParametrId(wellId, date, parametrId);
    }

    public void save(ChemWrapper chemWrapper) {
        chemWrapper.getChems().forEach(chem -> {
            if (chem.getValue() != null || chem.isTooLow()) {
                chem.setWellId(chemWrapper.getWellId());
                try {
                    chem.setDate(DateFormatter.getDateFromString(chemWrapper.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                chem.setDataSource(1);
                chem.setTemplateId(chemWrapper.getTemplateId());
                chem.setValue(chem.isTooLow() ? null : chem.getValue());
                //chem.setReportDocId(chemWrapper.getReportDocId());
                chem.setReportDocId(chemWrapper.getReportId());
                chemRepository.save(chem);
            }
            else {
                if(chem.getId() != null) {
                    chemRepository.delete(chem);
                }
            }
        });
    }

    public List<Chem> getChemsByWellId(int wellId) {
        return chemRepository.findAllByWellId(wellId);
    }

}


