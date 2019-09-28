package mosecom.service.licensereport;

import mosecom.dao.licensereport.dictionaries.ChemComponentRepository;
import mosecom.dao.licensereport.dictionaries.ChemTemplateInfoRepository;
import mosecom.dao.licensereport.dictionaries.ChemTemplateItemRepository;
import mosecom.dao.licensereport.dictionaries.LaboratoryRepository;
import mosecom.model.licencereport.dictionary.ChemComponent;
import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import mosecom.model.licencereport.dictionary.Laboratory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TemplateServiceImpl {

    @Autowired
    private ChemComponentRepository componentRepository;

    @Autowired
    private ChemTemplateInfoRepository templateInfoRepository;

    @Autowired
    private ChemTemplateItemRepository templateItemRepository;

//    @Autowired
//    private ChemTemplateRepository chemTemplateRepository;

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    public List<Laboratory> findLaboratoryList() {
        return laboratoryRepository.findAll();
    }

    public List<ChemComponent> findComponentList() { return componentRepository.findAll(); }


    public void save(ChemTemplateInfo template) {
        template.getChemItems().stream().forEach(i -> i.setTemplateInfo(template));
        templateInfoRepository.save(template);
    }

    public ChemTemplateInfo findTemplateInfoById(int id) {
        return templateInfoRepository.getOne(id);
    }

}


