package mosecom.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "wells_construction", schema = "public")
@Data
public class WellsConstruction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkey_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "construction_type_id")
    protected ConstructionType constructionType;

    @Column(name = "diametr")
    private int diameter;

    // TODO: Число?
    @Column(name = "depth_from")
    private String depthFrom;

    // TODO: Число?
    @Column(name = "depth_to")
    private String depthTo;

    @ManyToOne
    @JoinColumn(name = "well_id", referencedColumnName = "well_id")
    protected Well well;
}
