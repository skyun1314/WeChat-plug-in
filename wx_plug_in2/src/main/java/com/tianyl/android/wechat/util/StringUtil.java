package com.tianyl.android.wechat.util;

/**
 * Created by tianyl on 17/1/14.
 */

public class StringUtil {

    public static boolean isBlank(String str){
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }

}
