package mosecom.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mosecom.model.WellsDoc;
import mosecom.model.WellsGeology;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellFullProjection extends WellProjection {

	private List<WellsConstructionProjection> constructions;

	private List<WellsDocumentProjection> documents;

	private List<WellsGeologyProjection> geologies;

	private List<WellsStressTestProjection> stressTests;

	private WellsDocProjection wellDoc;

	//private WellsDepthProjection depth;

//	private List<WellsDocProjection> wellsDocs;
}
