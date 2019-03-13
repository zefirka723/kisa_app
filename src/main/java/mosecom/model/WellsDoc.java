package mosecom.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "Docs_Wells", schema = "fgi")
@Data
public class WellsDoc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pkey_id")
    private Integer id;

    @Column(name = "Doc_type")
    private int docType;

    @Column(name = "Date")
    private Date docDate;

//    @ManyToOne
//    @JoinColumn(name = "Well_ID")
//    protected Well well;
}
