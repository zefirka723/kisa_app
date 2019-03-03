package mosecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellProjection {

    private Integer id;

    private String wellName;

    private String wellCollar;

    private String drilledDate;
}
