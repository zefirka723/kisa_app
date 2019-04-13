package mosecom.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
public class WellsDescriptionProjection implements Serializable {

    private Integer id;

    protected int docType;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date docDate;

}
