package mosecom.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "Wells_StressTests", schema = "public")
@Data
public class WellssStressTest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pkey_id")
    private Integer id;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date stressDate;

    @Column(name = "Depression")
    private Float depression;

    @Column(name = "Flow_rate")
    private Float flowRate;

    @ManyToOne
    @JoinColumn(name = "Well_ID", referencedColumnName = "Well_ID")
    protected Well well;
}
