package mosecom.model.inspections;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "kisa", name = "\"Registrations\"")
public class RegItem implements Serializable {

    @Id
    @Column(name = "Doc_ID")
    private Integer id;

    @Column(name = "Reg_status")
    private Integer regStatusId;

    @Column(name = "RegStatus_text")
    private String regStatusText;

    @Column(name = "Processed_by")
    private Integer authorId;

    @Column(name = "Date_processing")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateProcessing;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date date;

    @Column(name = "Reg_number")
    private String regNumber;

    @Column (name = "Doc_type")
    private Integer docType;

    @Column (name = "Name") // айдишник пункта наблюдения
    private Integer observationPointId;

}
