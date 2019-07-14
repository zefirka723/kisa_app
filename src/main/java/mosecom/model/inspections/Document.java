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
    
    @Column(name = "Reg_status")
    private Integer regStatusId;
    
    @Column(name = "Processed_by")
    private Integer authorId;

    @Column(name = "Date_processing")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    //@Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "Reg_number")
    private String regNumber;
    
    @Column(name = "Organization_source")
    private Integer organizationId;

    @Column(name = "Pages")
    private Integer pages;
    
    @Column (name = "Neck_secrecy")
    private Integer secrecyId;

    @Column (name = "Storage")
    private String storage;

    @Column (name = "Comments")
    private String comment;

    @Column(name = "Doc_type")
    private Integer docType;

}
