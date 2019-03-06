package mosecom.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import lombok.Data;

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
    private String wellCollar;

//    @Column(name = "Drilled_date")
//    private String drilledDate;


    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellsConstruction> constructions;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellsDocument> documents;
}
