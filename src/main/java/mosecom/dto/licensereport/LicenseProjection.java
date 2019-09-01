package mosecom.dto.licensereport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mosecom.model.licencereport.LicenseReport;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseProjection implements Serializable {

    private Integer id;

    private String licenseNumber;

//    private LicenseReport report;
//
//    private List<LicenseToWellsProjection> licenseToWellsProjectionList;

}
