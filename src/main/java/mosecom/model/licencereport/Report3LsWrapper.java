package mosecom.model.licencereport;

import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@Data
public class Report3LsWrapper {

    @Id
    private int reportDocId;

    private List<Report3Ls> oldReport3Ls;

    private List<Report3Ls> report3LsList;
}
