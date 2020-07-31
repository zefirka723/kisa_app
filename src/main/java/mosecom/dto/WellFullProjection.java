package mosecom.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mosecom.model.Attachment;
import mosecom.model.MovedType;
import mosecom.model.licencereport.WaterDepth;
import sun.security.krb5.internal.crypto.Des;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WellFullProjection extends WellProjection {

    private List<WellsConstructionProjection> constructions;

    private List<WellsDocumentProjection> documents;

    private List<WellsGeologyProjection> geologies;

    private List<WellsStressTestProjection> stressTests;

    private DescriptionProjection wellDescription;

    private List<MovedType> movedTypes;

    private WellsDepthProjection depth;

    private ReccardProjection reccard;

    private PassportProjection passport;

    private DescriptionProjection description;

    private List<WaterDepth> depthsList;

    private List<Attachment> attachments;

//	private int docType;
    //	private WellsDocProjection wellDoc;
//		private List<WellsDocProjection> wellsDocs;


}
