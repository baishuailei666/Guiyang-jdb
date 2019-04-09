package com.example.jdb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author 白帅雷
 * @date 2019-01-09
 */
public class DateTimeUtil {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getFormatTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.format(new Date(time).getTime());
    }

}
