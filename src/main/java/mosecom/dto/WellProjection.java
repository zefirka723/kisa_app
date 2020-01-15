package mosecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellProjection {

    private Integer id;

    private String wellName;

    private Float wellCollar;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date drilledDate;
//    private String drilledDate;

    private Integer moved;

    private Integer horId;

}
