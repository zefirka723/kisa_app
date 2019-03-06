package mosecom.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "Wells_Geology", schema = "public")
@Data
public class WellsGeology implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pkey_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Hor_ID")
    protected Horisont horisont;

    @Column(name = "Bot_elev")
    private Float botElev;

    @ManyToOne
    @JoinColumn(name = "Well_ID", referencedColumnName = "Well_ID")
    protected Well well;

}
