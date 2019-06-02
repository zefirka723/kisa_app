package mosecom.dto.inspections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mosecom.model.inspections.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellsInspectionFullProjection {

    private DocumentProjection document;

}
