package mosecom.model.licencereport;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mosecom.dictionaries.DocTypes;
import mosecom.model.Attachment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(schema = "fgi", name = "WellsDoc_Liquidation")
public class PluggingAct implements Serializable {
    @Column(name = "Doc_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Well_ID")
    private int wellId;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "File_Set_ID")
    private Integer fileSetId;

    @Column(name = "Report_Doc_ID")
    private Integer reportDocId;

    @Transient
    private Attachment attachment;

    @Transient
    private DocTypes docType;

    @Transient
    private String link;

}
