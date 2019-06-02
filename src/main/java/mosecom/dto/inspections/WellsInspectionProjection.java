package mosecom.dto.inspections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mosecom.model.inspections.Document;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellsInspectionProjection {

    private Integer id;

    private Integer wellId;

    private Date date;

    private String link;

//    private int docId;

}
