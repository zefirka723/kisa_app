package mosecom.model;

import lombok.Data;
import mosecom.model.licencereport.LicenseToWells;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "public", name = "\"Wells\"")
public class Well implements Serializable {

    @Column(name = "Well_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Well_name")
    private String wellName;

    @Column(name = "Well_collar")
    private Float wellCollar;

    @Column(name = "Drilled_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    //@Temporal(TemporalType.DATE)
    private Date drilledDate;
    //private String drilledDate;

    @Column(name = "Moved")
    private Integer moved;


    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellsConstruction> constructions;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellsGeology> geologies;

    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<WellssStressTest> stressTests;

    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private WellsDepth depth;

    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Reccard reccard;

    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Passport passport;

    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Description description;

//    @OneToMany(mappedBy = "well")
//    private Set<LicenseToWells> licenseToWellsSet;


//    public List<Attachment> getDocumentsByReccard() {
//        boolean haveReccard = false;
//        if(reccard.getId() != null) {
//            for (Attachment d : documents) {
//                if (d.getDocumentType() != null && d.getDocumentType().getId() == 3001) {
//                    haveReccard = true;
//                }
//            }
//            if (haveReccard) {
//                return documents.stream()
//                        .filter(documents -> documents.getDocumentType().getId() == 3001 && documents.getDocId() == reccard.getId())
//                        .collect(Collectors.toList());
//            }
//        }
//        return null;
//    }


//    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private WellsDoc wellDoc;


//    @OneToMany(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private List<WellsDoc> wellsDocList;
//
//
//    public WellsDoc getWellDoc() {
//        return wellsDocList.stream()
//                            .filter(wellsDoc -> wellsDoc.getDocType() == 3001)
//                            .collect(Collectors.toList())
//                            .get(0);
//    }


//    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private Passport wellPassport;
//
//    @OneToOne(mappedBy = "well", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private Description wellsDescription;


    }
