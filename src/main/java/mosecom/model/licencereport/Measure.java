package mosecom.model.licencereport;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
@Data
public class Measure implements Serializable {
    List<String> values = Arrays.asList("м3/сут", "м3/час", "л/сек", "м3/мес", "м3/год");
}
