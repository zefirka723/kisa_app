package mosecom.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
    Паспорт скважины
 */


@SuppressWarnings("serial")
@Entity
@Table(name = "WellsDoc_Descriptions", schema = "fgi")
@Data
public class Description implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pkey_id")
    private Integer id;

//    @Column(name = "Doc_type")
//    private int docType;

    @OneToOne
    @JoinColumn(name = "Well_ID")
    protected Well well;

}
