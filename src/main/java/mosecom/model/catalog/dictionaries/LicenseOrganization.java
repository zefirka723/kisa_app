package mosecom.model.catalog.dictionaries;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "dictionaries", name = "Dictionary_Organizations_License")
public class LicenseOrganization implements Serializable {

    @Column(name = "Code")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Value")
    private String name;

}
