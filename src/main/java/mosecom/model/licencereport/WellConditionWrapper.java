package mosecom.model.licencereport;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
public class WellConditionWrapper implements Serializable {

    private Integer wellId;

    private Integer reportDocId;

    private List<WellCondition> conditions;

    private List<WellCondition> oldConditions;

}
