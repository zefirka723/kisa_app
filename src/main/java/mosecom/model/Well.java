package mosecom.model;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "public", name = "\"Wells\"")
public class Well implements Serializable {

    @Column(name = "Well_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Well_name")
    private String wellName;

    @Column(name = "Well_collar")
    private Float wellCollar;

    @Column(name = "Drilled_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    //@Temporal(TemporalType.DATE)
    private Date drilledDate;


    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellsConstruction> constructions;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellsDocument> documents;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellsGeology> geologies;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellssStressTest> stressTests;

//    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private WellsDepth depth;

    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private WellsDoc wellsDoc;
}
