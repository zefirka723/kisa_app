package mosecom.model.licencereport;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "\"Wells_FlowRate\"")
public class Flowrate implements Serializable {
    @Column(name = "Pkey_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Well_ID")
    private int wellId;


    @Column(name = "Date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date;

    @Column(name = "Flow_rate")
    private double flowrate;

    @Transient
    private int measure = 0;

}
