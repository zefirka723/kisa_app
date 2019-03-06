package mosecom.dto;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class WellsConstructionProjection implements Serializable {

    private Integer id;

    protected int constructionTypeId;

    private int diametrId;

    // TODO: Число?
    private Double depthFrom;

    // TODO: Число?
    private Double depthTo;
}
