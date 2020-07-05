package mosecom.utils;

import mosecom.model.licencereport.dictionary.Measurement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static mosecom.model.licencereport.dictionary.Measurement.TM3YEAR;

public class MeasurementConverter {

    public static Double convertValueToM3Day(Double value, Date date, Measurement measurement) {
        switch (measurement) {
            case TM3DAY:
                return value * 1000;
            case M3HOUR:
                return value * 24;
            case LSEK:
                return value * 86.4;
            case M3MONTH:
                return value / date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().lengthOfMonth();
            case TM3YEAR:
                return value * 1000 / date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().lengthOfYear();
        }
        return value;
    }

}
