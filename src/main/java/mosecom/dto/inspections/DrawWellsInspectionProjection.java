package mosecom.dto.inspections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawWellsInspectionProjection {

    private Integer id;

    private Integer drawWellId;

    private Date date;

    private String link;

//    private int docId;

}
