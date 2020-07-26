package mosecom.model.licencereport.dictionary;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "dictionaries", name = "Dictionary_FlowRate_Period")
public class Period implements Serializable {

    @Column(name = "Code")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Value")
    private String name;
}
