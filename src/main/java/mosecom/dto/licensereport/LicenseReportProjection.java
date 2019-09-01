package mosecom.dto.licensereport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mosecom.model.licencereport.License;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseReportProjection implements Serializable {

    private Integer id;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date;

    private Double ls;

    private Double tp;

//    private License license;

}
