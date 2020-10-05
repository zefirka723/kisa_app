package mosecom.model.licencereport;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "public", name = "Wells_Chem")
public class Chem implements Serializable {
    @Column(name = "Pkey_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Well_ID")
    private int wellId;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Parametr")
    private int parametrId;

    @Column(name = "Chem_value")
    private Double value;

    @Column(name = "Data_source")
    private int dataSource;

    @Column(name = "Template_ID")
    private Integer templateId;

    @Column(name = "Doc_ID")
    private Integer reportDocId;

    @Transient
    private boolean tooLow;

    @Transient
    private String paramName;

    @Transient
    private String measure;

}
