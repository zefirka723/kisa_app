package mosecom.dto.inspections;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class FileProjection {

    private Integer id;

    private int wellInspectionId;
}
