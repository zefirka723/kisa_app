package mosecom.model.licencereport;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@Data
public class WaterDepthByWell implements Serializable {

    @Id
    private int wellId;

    private List<WaterDepth> depths;

}
