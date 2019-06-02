package mosecom.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
    Геологическое описание скважины
 */


@SuppressWarnings("serial")
@Entity
@Table(name = "WellsDoc_Passports", schema = "fgi")
@Data
public class Passport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pkey_id")
    private Integer id;

    @Column(name = "Doc_type")
    private int docType;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date docDate;

    @OneToOne
    @JoinColumn(name = "Well_ID")
    protected Well well;

}
