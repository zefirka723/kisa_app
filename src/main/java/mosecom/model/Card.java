package mosecom.model;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
    Учётная карточка
 */


@SuppressWarnings("serial")
@Entity
@Table(name = "Docs_Wells", schema = "fgi")
@Data
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pkey_id")
    private Integer id;

    @Column(name = "Doc_type")
    private int docType;

    @Column(name = "Doc_ID")
    private int docId;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date docDate;

//    @OneToOne
//    @JoinColumn(name = "Well_ID")
//    protected Well well;

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Well well;

}
