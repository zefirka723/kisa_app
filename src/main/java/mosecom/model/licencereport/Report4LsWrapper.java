package mosecom.model.licencereport;

import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@Data
public class Report4LsWrapper {

    @Id
    private int reportDocId;

    private List<Report4Ls> oldReport4Ls;

    private List<Report4Ls> report4LsList;
}
