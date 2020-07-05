package mosecom.model.licencereport.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Measurement {

    M3DAY("м3/сут"),
    M3HOUR( "м3/час"),
    LSEK( "л/сек"),
    M3MONTH( "м3/мес"),
    TM3YEAR( "тыс. м3/год"),
    TM3DAY("тыс. м3/сут");

    private String label;
}
