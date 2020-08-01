package mosecom.model.catalog;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(schema = "fgi", name = "Primary_documents")
public class PrimaryDoc {

    @Column(name = "Doc_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Reg_status")
    private String regStatus;

    @Column(name = "Reg_number")
    private String regNumber;

    @Column(name = "Date_processing")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateProcessing;

    @Column(name = "Name")
    private String name;

    @Column(name = "Organization_source")
    private String organizationSource;

    @Column(name = "Type_observ")
    private String typeObserv;

    @Column(name = "ID_observ")
    private String observId;

    @Column(name = "Doc_type")
    private String docType;

    @Column(name = "Date_prepare")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datePrepare;

    @Column(name = "Pages")
    private String pages;

    @Column(name = "Neck_secrecy")
    private String neckSecrecy;

    @Column(name = "Link")
    private String link;

    @Column(name = "Storage")
    private String storage;

    @Column(name = "Comments")
    private String comments;

}