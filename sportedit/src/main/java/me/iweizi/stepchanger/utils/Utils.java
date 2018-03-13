package me.iweizi.stepchanger.utils;

import java.util.Calendar;

/**
 * Created by iweiz on 2017/9/8.
 * 工具类
 */

public class Utils {
    public static long beginOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }
}
