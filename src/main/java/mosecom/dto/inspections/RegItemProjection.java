package mosecom.dto.inspections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegItemProjection {

        private Integer id;

        private Integer regStatusId;

        private String regStatusText;

        private Integer authorId;

        private Integer docType;

        @DateTimeFormat(pattern = "dd.MM.yyyy")
        private Date dateProcessing;

        @DateTimeFormat(pattern = "dd.MM.yyyy")
        private Date date;

        private String regNumber;

        private Integer observationPointId;

        private String link;

}
