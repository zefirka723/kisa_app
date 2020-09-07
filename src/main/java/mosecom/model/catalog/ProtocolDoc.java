package mosecom.model.catalog;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(schema = "fgi", name = "Catalog_protocols")
public class ProtocolDoc {

    @Column(name = "Doc_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "Reg_status")
    private String regStatus;

    @Column (name = "Reg_number")
    private String regNumber;

    @Column (name = "Date_processing")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateProcessing;

    @Column (name = "Name")
    private String name;

    @Column (name = "Organization_source")
    private String organizationSource;

    @Column (name = "Subject")
    private String subject;

    @Column (name = "Instance")
    private String instance;

    @Column (name = "Number")
    private String number;

    @Column (name = "Date")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private Date date;

    @Column (name = "License_number")
    private String licenseNumber;

    @Column (name = "Field_general_name")
    private String fieldGeneralName;

    @Column (name = "Field_name")
    private String fieldName;

    @Column (name = "Pages")
    private String pages;

    @Column (name = "Neck_secrecy")
    private String neckSecrecy;

    @Column (name = "Link")
    private String link;

    @Column (name = "Storage")
    private String storage;

    @Column (name = "Comments")
    private String comment;

    @Column (name = "Neck_secrecy_ID")
    private int neckSecrecyId;
}