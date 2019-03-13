package mosecom.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
public class WellsStressTestProjection implements Serializable {

    private Integer id;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date stressDate;

    private Float depression;

    private Float flowRate;
}
