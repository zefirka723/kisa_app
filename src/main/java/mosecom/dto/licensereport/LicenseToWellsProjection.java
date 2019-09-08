package mosecom.dto.licensereport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mosecom.model.Well;
import mosecom.model.licencereport.License;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseToWellsProjection implements Serializable {

    private Integer id;

    private Double flowRate;

    public Integer wellId;

 //   public License license;

}
