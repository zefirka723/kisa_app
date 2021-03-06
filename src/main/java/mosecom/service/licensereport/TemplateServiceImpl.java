package mosecom.service.licensereport;

import mosecom.dao.licensereport.dictionaries.ChemComponentRepository;
import mosecom.dao.licensereport.dictionaries.ChemTemplateInfoRepository;
import mosecom.dao.licensereport.dictionaries.ChemTemplateItemRepository;
import mosecom.dao.licensereport.dictionaries.LaboratoryRepository;
import mosecom.model.licencereport.ChemTemplateItem;
import mosecom.model.licencereport.dictionary.ChemComponent;
import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import mosecom.model.licencereport.dictionary.Laboratory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;


@Service
public class TemplateServiceImpl {

    @Autowired
    private ChemComponentRepository componentRepository;

    @Autowired
    private ChemTemplateInfoRepository templateInfoRepository;

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    @Autowired
    private ChemTemplateItemRepository itemRepository;

    public List<Laboratory> findLaboratoryList() {
        return laboratoryRepository.findAll();
    }

    public List<ChemComponent> findComponentList() {
        return componentRepository.findAllByOrderByNameAsc();
    }

    public List<ChemTemplateInfo> findTemplateInfoList() {
        return templateInfoRepository.findAll();
    }


    public void save(ChemTemplateInfo template) {
        template.getChemItems().removeIf(item -> item.getParametrId() == null);

        if (template.getId() != null) {
            template.setChemItems(updateDbItems(template));
        }

        // автонумерация
        double lastIndex = 0;
        for (ChemTemplateItem i : template.getChemItems()) {
                if (i.getDisplayOrder() != null) {
                    lastIndex = i.getDisplayOrder();
                } else {
                    lastIndex = lastIndex + 5;
                    i.setDisplayOrder(lastIndex);
                }
                i.setTemplateInfo(template);
            }

        templateInfoRepository.save(template);
    }

    private List<ChemTemplateItem> updateDbItems (ChemTemplateInfo template) {
        List<ChemTemplateItem> itemsFromDb = templateInfoRepository.getOne(template.getId()).getChemItems();
        HashMap<Integer, Integer> componentsFromDb = new HashMap<>();

        for (ChemTemplateItem i: itemsFromDb) {
            componentsFromDb.put(i.getParametrId(), i.getId());
        }

        for (ChemTemplateItem i: template.getChemItems()) {
            if (componentsFromDb.containsKey(i.getParametrId())) {
                i.setId(componentsFromDb.get(i.getParametrId()));
            }
        }

        return template.getChemItems();
    }


    public ChemTemplateInfo findTemplateInfoById(int id) {
        return templateInfoRepository.getOne(id);
    }

}


