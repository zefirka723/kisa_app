package mosecom.model.licencereport;

import lombok.Data;
import mosecom.model.Attachment;
import mosecom.model.licencereport.dictionary.ReportName;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "fgi", name = "\"License_Reports\"")
public class LicenseReport implements Serializable {

    @Column(name = "Doc_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

//    @OneToOne(optional = false, cascade = CascadeType.ALL)
//    @JoinColumn(name = "License_Doc_ID")
//    private License license;

    @Column(name = "License_Doc_ID")
    private Integer licenseDocId;

    @OneToOne
    @JoinColumn(name = "Report_name")
    private ReportName reportName;

    @Column(name = "File_Set_ID")
    private Integer fileSetId;

    @Column(name = "Comments")
    private String comments;

//    @OneToMany(mappedBy = "report")
//    private List<Attachment> attachments;

//    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Transient
    private List<Attachment> attachments;

}
