package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String noneUTCDatePattern = "yyyyMMddHHmmss";

    public static String getNoneUTCDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(noneUTCDatePattern);
        return simpleDateFormat.format(new Date());
    }
}
