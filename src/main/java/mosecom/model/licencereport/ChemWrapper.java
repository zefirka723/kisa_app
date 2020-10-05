package mosecom.model.licencereport;

import lombok.Data;
import mosecom.model.licencereport.dictionary.ChemComponent;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data

public class ChemWrapper implements Serializable {

    @Id
    private int wellId;

    private String date;

    private int templateId;

    private List<Chem> chems;

    //private int reportDocId;
    private int reportId;

}
