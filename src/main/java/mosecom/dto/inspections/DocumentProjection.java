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
public class DocumentProjection {

        private Integer id;

        private Integer authorId;

        private String comment;

        @DateTimeFormat(pattern = "dd.MM.yyyy")
        private Date date;

        private Integer organizationId;

        private Integer pages;

        private String regNumber;

        private Integer regStatusId;

        private Integer secrecyId;

        private String storage;

        private Integer docType;

}
