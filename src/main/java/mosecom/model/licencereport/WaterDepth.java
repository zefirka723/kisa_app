package mosecom.model.licencereport;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "\"Wells_WaterDepth\"")
public class WaterDepth implements Serializable {
    @Column(name = "Pkey_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Well_ID")
    private int wellId;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date;

    @Column(name = "Water_depth")
    private Double depth;

    @Column(name = "Data_source")
    private Integer dataSource;

}
