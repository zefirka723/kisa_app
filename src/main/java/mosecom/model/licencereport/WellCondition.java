package mosecom.model.licencereport;

import lombok.Data;
import mosecom.model.licencereport.dictionary.Condition;
import mosecom.model.licencereport.dictionary.Measurement;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "public", name = "Wells_Condition")
public class WellCondition implements Serializable {
    @Column(name = "Pkey_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "Condition")
    private Integer conditionId;

    @Column(name = "Doc_ID")
    private Integer reportDocId;

    @Column(name = "Well_ID")
    private int wellId;

}
