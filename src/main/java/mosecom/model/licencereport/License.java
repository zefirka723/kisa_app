package mosecom.model.licencereport;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "fgi", name = "\"License\"")
public class License implements Serializable {

    @Column(name = "Doc_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "License_number")
    private String licenseNumber;

    @OneToOne(mappedBy = "license")
    private LicenseReport report;

    @OneToMany(mappedBy = "license")
    private Set<LicenseToWells> licenseToWellsSet;

    }
