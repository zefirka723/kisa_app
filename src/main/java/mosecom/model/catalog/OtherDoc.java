package mosecom.model.catalog;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(schema = "fgi", name = "Catalog_reports")
public class OtherDoc {

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

    @Column (name = "Doc_type")
    private String docType;

    @Column (name = "Report_name")
    private String reportName;

    @Column (name = "Organization_author")
    private String organizationAuthor;

    @Column (name = "Author")
    private String author;

    @Column (name = "Compilation_site")
    private String compilationSite;

    @Column (name = "Compilation_year")
    private Integer compilationYear;

    @Column (name = "Paper_version")
    private String paperVersion;

    @Column (name = "Digital_version")
    private String digitalVersion;

    @Column (name = "Pages")
    private String pages;

    @Column (name = "Number_of_graphic")
    private String numberOfGraphic;

    @Column (name = "Neck_secrecy")
    private String neckSecrecy;

    @Column (name = "Link")
    private String link;

    @Column (name = "Storage")
    private String storage;

    @Column (name = "Comments")
    private String comment;

    @Column (name = "Neck_secrecy_ID")
    private Integer neckSecrecyId;

    @Transient
    private String reportNameForFront;

}