package mosecom.model.catalog;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(schema = "fgi", name = "Catalog_license")
public class LicenseDoc {

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

    @Column (name = "License_number")
    private String licenseNumber;

    @Column (name = "Organizations")
    private String organizations;

    @Column (name = "Subject")
    private String subject;

    @Column (name = "Status")
    private String status;

    @Column (name = "Date_start")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateStart;

    @Column (name = "Date_end")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEnd;

    @Column (name = "Flow_rate_summ")
    private Float flowRateSumm;

    @Column (name = "Comments_license")
    private String commentsLicense;

    @Column (name = "Comments_docs")
    private String commentsDocs;

    @Column (name = "Pages")
    private String pages;

    @Column (name = "Neck_secrecy")
    private String neckSecrecy;

    @Column (name = "Link")
    private String link;

    @Column (name = "Storage")
    private String storage;

    @Transient
    private String commentsLicenseForFront;

}