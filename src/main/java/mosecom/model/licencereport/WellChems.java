package mosecom.model.licencereport;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "kisa", name = "Well_Chems") // вьюха
public class WellChems implements Serializable {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "Well_ID")
    private Integer wellId;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Template_ID")
    private int templateId;

    @Column(name = "Value")
    private String templateName;

}
