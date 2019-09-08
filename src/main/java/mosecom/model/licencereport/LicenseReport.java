package mosecom.model.licencereport;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "fgi", name = "\"License_Reports\"")
public class LicenseReport implements Serializable {

    @Column(name = "Pkey_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
//    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "4LS")
    private Double ls;

    @Column(name = "2TP")
    private Double tp;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "License_Doc_ID")
    private License license;
//    @Column(name = "License_Doc_ID")
//    private int licenseId;

    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "File_set_ID_seq")
    //@SequenceGenerator(name = "File_set_ID_seq", sequenceName = "File_set_ID_seq")
    @Column(name = "File_Set_ID")
    private Integer fileSetId;

}
