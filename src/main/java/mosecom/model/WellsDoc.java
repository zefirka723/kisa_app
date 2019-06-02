//package mosecom.model;
//
//import lombok.Data;
//import org.hibernate.annotations.Cascade;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//
///*
//    Учётная карточка по скважине
// */
//
//
//@SuppressWarnings("serial")
//@Entity
//@Table(name = "WellsDoc_Reccards", schema = "fgi")
//@Data
//public class WellsDoc implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "Pkey_id")
//    private Integer id;
//
//    @Column(name = "Doc_type")
//    private int docType;
//
//    @Column(name = "Date")
////    @DateTimeFormat(pattern = "dd.MM.yyyy")
//    private Date docDate;
//
//    @OneToOne
//    @JoinColumn(name = "Well_ID", referencedColumnName = "Well_ID")
//    protected Well well;
//
//}