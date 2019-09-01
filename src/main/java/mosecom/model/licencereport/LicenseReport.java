package mosecom.model.licencereport;

import lombok.Data;
import mosecom.model.*;
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

    @Column(name = "Pkey_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date;

    @Column(name = "4LS")
    private Double ls;

    @Column(name = "2TP")
    private Double tp;

    // ещё тут должен быть File_Set_ID

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "License_Doc_ID")
    private License license;

    }
