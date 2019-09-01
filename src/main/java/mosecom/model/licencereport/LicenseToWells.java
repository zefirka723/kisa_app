package mosecom.model.licencereport;

import lombok.Data;
import mosecom.model.Well;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "public", name = "\"License_to_Wells\"")
public class LicenseToWells implements Serializable {

    @Column(name = "Link_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Flow_rate")
    private Double flowRate;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "Well_ID")
//    public Well well;

    @Column(name = "Well_ID")
    private int wellId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Doc_ID")
    protected License license;

}