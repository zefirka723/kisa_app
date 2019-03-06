package mosecom.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "dictionaries", name = "Dictionary_WellConstruction_Diametr")
public class Diametr implements Serializable {

    @Column(name = "Diametr_mm")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

// TODO: допилить сюда дюймы
    //    @Column(name = "Value")
//    private String name;
}
