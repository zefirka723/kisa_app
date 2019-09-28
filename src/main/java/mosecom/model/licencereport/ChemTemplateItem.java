package mosecom.model.licencereport;

import lombok.Data;
import mosecom.model.Well;
import mosecom.model.licencereport.dictionary.ChemTemplateInfo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "kisa", name = "ChemProtTemplate_Content")
public class ChemTemplateItem implements Serializable {
    @Column(name = "Link_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(name = "Template_ID")
//    private int templateId;

    @ManyToOne
    @JoinColumn(name = "Template_ID", referencedColumnName = "Code")
    protected ChemTemplateInfo templateInfo;

    @Column(name = "Parametr")
    private int parametrId;

    @Column (name = "Display_order")
    private double displayOrder;

}
