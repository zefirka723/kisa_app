package mosecom.model.licencereport.dictionary;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mosecom.model.licencereport.ChemTemplateItem;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(schema = "dictionaries", name = "Dictionary_ChemProt_Template")
public class ChemTemplateInfo implements Serializable {

    @Column(name = "Code")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Value")
    private String name;

    @Column(name = "Laboratory_ID")
    private Integer laboratoryId;

    @OneToMany(mappedBy = "templateInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OrderBy("displayOrder ASC")
    private List<ChemTemplateItem> chemItems;

}
