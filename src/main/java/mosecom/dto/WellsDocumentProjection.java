package mosecom.dto;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class WellsDocumentProjection implements Serializable{

    private Integer id;

    private int docsWellId;
}
