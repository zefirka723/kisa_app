package mosecom.service.licensereport;

import mosecom.dao.licensereport.ChemRepository;
import mosecom.dao.licensereport.WellChemsRepository;
import mosecom.dao.licensereport.dictionaries.ChemComponentRepository;
import mosecom.dao.licensereport.dictionaries.ConditionReporitory;
import mosecom.model.licencereport.Chem;
import mosecom.model.licencereport.ChemTemplateItem;
import mosecom.model.licencereport.ChemWrapper;
import mosecom.model.licencereport.WellChems;
import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import mosecom.model.licencereport.dictionary.Condition;
import mosecom.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ConditionServiceImpl {

    @Autowired
    private ConditionReporitory conditionReporitory;

    public List<Condition> getAllConditions () {
        return conditionReporitory.findAll();
    }

}


