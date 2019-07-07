package mosecom.model.inspections;

import lombok.Data;
import mosecom.model.Well;
import mosecom.model.inspections.dictionaries.Employees;
import mosecom.model.inspections.dictionaries.OrganizationSource;
import mosecom.model.inspections.dictionaries.RegStatus;
import mosecom.model.inspections.dictionaries.Secrecy;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
@Data
@Entity
//@Table(schema = "fgi", name = "\"Documents\"")
@Table(schema = "fgi", name = "\"Documents\"")
public class Document implements Serializable {

    @Id
    @Column(name = "Doc_ID")
    private Integer id;

 //   @ManyToOne
 //   @JoinColumn(name = "Reg_status")
    @Column(name = "Reg_status")
    private Integer regStatusId;

//    @ManyToOne
//    @JoinColumn(name = "Processed_by")
    @Column(name = "Processed_by")
    private Integer authorId;

    @Column(name = "Date_processing")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    //@Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "Reg_number")
    private String regNumber;

//    @ManyToOne
//    @JoinColumn(name = "Organization_source")
    @Column(name = "Organization_source")
    private Integer organizationId;

    @Column(name = "Pages")
    private Integer pages;

//    @ManyToOne
//    @JoinColumn (name = "Neck_secrecy")
    @Column (name = "Neck_secrecy")
    private Integer secrecyId;

    @Column (name = "Storage")
    private String storage;

    @Column (name = "Comments")
    private String comment;

    @Column(name = "Doc_type")
    private Integer docType;

    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private WellsInspection wellsInspection;

    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private DrawWellsInspection drawWellsInspection;

    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private SpringsInspection springsInspection;
}
