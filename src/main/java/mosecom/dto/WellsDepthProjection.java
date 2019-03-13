package mosecom.dto;

import lombok.Data;
import mosecom.model.Well;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
public class WellsDepthProjection implements Serializable {

    private Integer id;

    private Double wellDepth;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private java.util.Date Date;

}
