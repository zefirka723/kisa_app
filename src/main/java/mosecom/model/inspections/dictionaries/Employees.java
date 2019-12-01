package mosecom.model.inspections.dictionaries;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "work", name = "Employees")
public class Employees implements Serializable {

    @Column(name = "Emploe_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Name")
    private String name;
}
