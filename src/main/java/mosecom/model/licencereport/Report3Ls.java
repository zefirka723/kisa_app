package mosecom.model.licencereport;

import lombok.Data;
import mosecom.model.Horisont;
import mosecom.model.licencereport.dictionary.Measurement;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "fgi", name = "Reports_3LS")
public class Report3Ls implements Serializable {
    @Column(name = "Pkey_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Date")
    private Date date;

    @Column(name = "Flow_rate")
    private Double flowrate;

    @Column (name = "Hor_ID")
    private Integer horisontId;

    @Column(name = "Doc_ID")
    private int reportDocId;

    @Transient
    private Measurement measurement;

}
