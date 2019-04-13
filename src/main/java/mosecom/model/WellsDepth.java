package mosecom.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "Wells_Depth", schema = "public")
@Data
public class WellsDepth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pkey_id")
    private Integer id;

    @Column(name = "Well_depth")
    private Double wellDepth;


    @Column(name = "Date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date;


    @OneToOne
    @JoinColumn(name = "Well_ID" , referencedColumnName = "Well_ID")
    protected Well well;
}
