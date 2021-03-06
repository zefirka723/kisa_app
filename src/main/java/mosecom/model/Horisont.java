package mosecom.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "model", name = "Hor_name_model")
public class Horisont implements Serializable {

    @Column(name = "Code")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Hor_name_model")
    private String name;

    @Column(name = "Order")
    private int order;
}
