package mosecom.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mosecom.model.MovedType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellFullProjection extends WellProjection {

	private List<WellsConstructionProjection> constructions;

	private List<WellsDocumentProjection> documents;

	private List<WellsGeologyProjection> geologies;

	private List<WellsStressTestProjection> stressTests;

	private WellsDocProjection wellDoc;

	private WellsDescriptionProjection wellDescription;

	private WellsPassportProjection wellPassport;

	private List<MovedType> movedTypes;

	private WellsDepthProjection depth;

	private int docType;
//	private List<WellsDocProjection> wellsDocs;
}
