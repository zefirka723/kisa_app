package mosecom.dto.inspections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpringsInspectionProjection {

    private Integer id;

    private Integer wellId;

    private Date date;

    private String link;

//    private int docId;

}
