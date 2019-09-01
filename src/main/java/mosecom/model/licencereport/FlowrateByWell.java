package mosecom.model.licencereport;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@Data
public class FlowrateByWell implements Serializable {

    @Id
    private int wellId;

    private List<Flowrate> rates;

}
