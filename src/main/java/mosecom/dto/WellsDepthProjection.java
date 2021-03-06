package mosecom.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
public class WellsDepthProjection implements Serializable {

    private Integer id;

    private Double wellDepth;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private java.util.Date Date;

}
