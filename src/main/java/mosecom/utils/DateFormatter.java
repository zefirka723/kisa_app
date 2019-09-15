package mosecom.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    //static class DateFromStringUtil {
        public static Date getDateFromString(String stringDate) throws ParseException {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            return format.parse(stringDate);
        }
    //}
}
