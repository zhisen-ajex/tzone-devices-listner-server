package com.ajex.temperatureserver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String timestampToDate(long timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        return sd;
    }
}
