package com.emddi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateSQL {
    private static final SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");


    public static String toSqlFormat(String s)  {
        Date date = null;
        try {
            date = displayDateFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return sqlDateFormat.format(date);
    }

    public static String toDisplayFormat(String s) {
        Date date = null;
        try {
            date = sqlDateFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return displayDateFormat.format(date);
    }

    public static String currentDateTime() {
        return (String) LocalDateTime.now(zoneId).toString().replace("T"," ").subSequence(0,19);
    }

    public static String currentDateTimeMs() {
        return (String) LocalDateTime.now(zoneId).toString().replace("T"," ");
    }
}
