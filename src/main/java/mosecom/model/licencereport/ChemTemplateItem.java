package mosecom.model.licencereport;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mosecom.model.licencereport.dictionary.ChemTemplateInfo;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(schema = "kisa", name = "ChemProtTemplate_Content")
public class ChemTemplateItem implements Serializable {
    @Column(name = "Link_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Template_ID", referencedColumnName = "Code")
    protected ChemTemplateInfo templateInfo;

//    @Column(name = "Template_ID")
//    protected ChemTemplateInfo templateInfo;

    @Column(name = "Parametr")
    private Integer parametrId;

    @Column (name = "Display_order")
    private Double displayOrder;
}
