package mosecom.model.inspections;

import lombok.Data;
import mosecom.model.Description;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@Entity
@Table(schema = "public", name = "\"Wells_Inspection\"")
public class WellsInspection implements Serializable {

    @Column(name = "Pkey_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Well_ID")
    private Integer wellId;

    @Column(name = "Date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    //@Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "Link")
    private String link;

    @OneToOne
    @JoinColumn(name = "Doc_ID", referencedColumnName = "Doc_ID")
    protected Document document;

    public String getLink() {

        return link != null ? this.link.replaceAll("geoserver", "10.37.71.6")
                            : null;
    }

}
