package mosecom.model.licencereport;

import lombok.*;
import mosecom.model.licencereport.Report2Tp;

import javax.persistence.Id;
import java.util.List;

@Data
public class Report2TpWrapper {

    @Id
    private int reportDocId;

    private List<Report2Tp> oldReport2Tp;

    private List<Report2Tp> report2Tps;
}
