package mosecom.model.licencereport;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@Data
public class WaterDepthWrapper implements Serializable {

    @Id
    private int wellId;

    private int reportDocId;

    private List<WaterDepth> waterDepths;

    private List<WaterDepth> oldWaterDepths;

}
