package mosecom.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "dictionaries", name = "Dictionary_Hor_ID")
public class Horisont implements Serializable {

    @Column(name = "Code")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Hor_name")
    private String name;

    @Column(name = "Hor_index")
    private String index;
}
