package mosecom.model;

import lombok.Data;
import mosecom.model.licencereport.WaterDepth;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
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
    private Date drilledDate;

    @Column(name = "Moved")
    private Integer moved;

    @Column(name="Hor_ID")
    private Integer horId;


    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellsConstruction> constructions;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OrderBy("botElev ASC")
    private List<WellsGeology> geologies;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellssStressTest> stressTests;


    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Reccard reccard;

    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Passport passport;

    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Description description;


    @Transient
    List<WaterDepth> depthsList;

    /*
        Здесь начинается костыль. Т.к. глубин к одной скв может быть несколько, предложено искать по скв и дате бурения.
        НО. Для большого кол-ва скв нет такого соответствия (хотя глубины вообще есть) + непонятно, корректно ли это:
        документы разные, даты у них свои, возможно, правильно было бы искать по дате документа или какой-то ещё связи
        с документом.
        Поскольку логика до конца не определена, полноценно переделывать, а потом переделывать ещё раз не хочется.
     */

//    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private WellsDepth depth;

    @Transient
    WellsDepth depth;

    /*
        Здесь заканчивается костыль по глубинам.
    */

    }
