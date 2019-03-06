package mosecom.dto;

import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
public class WellsGeologyProjection implements Serializable {

    private Integer id;

    protected Integer horisontId;

    private Float botElev;

}
