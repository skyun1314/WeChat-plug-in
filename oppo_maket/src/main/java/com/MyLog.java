package com;

import android.util.Log;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyLog {


    public static String haha(Object param) {
        String value = null;
        if (param instanceof Integer) {
            value = new StringBuilder(String.valueOf(((Integer) param).intValue())).toString();
        } else if (param instanceof String) {
            value = new StringBuilder(String.valueOf((String) param)).toString();
        } else if (param instanceof byte[]) {
            value = new StringBuilder(byte2hex((byte[]) param)).toString();
        } else if (param instanceof Double) {
            value = new StringBuilder(String.valueOf(((Double) param).doubleValue())).toString();
        } else if (param instanceof Float) {
            value = new StringBuilder(String.valueOf(((Float) param).floatValue())).toString();
        } else if (param instanceof Long) {
            value = new StringBuilder(String.valueOf(((Long) param).longValue())).toString();
        } else if (param instanceof Boolean) {
            value = new StringBuilder(String.valueOf(((Boolean) param).booleanValue())).toString();
        } else if (param instanceof Date) {
            value = ((Date) param).toString();
        } else if (param instanceof List) {
            value = ((List) param).toString();
        } else if (param instanceof Map || param instanceof HashMap) {

            Map<String, Object> param1 = ((Map<String, Object>) param);

            for (Map.Entry<String, Object> entry : param1.entrySet()) {
                value+= "\nkey= " + entry.getKey() + " \n value= " + haha(entry.getValue());
            }
        } else if (param instanceof Object) {

            value = "\n" + "start--------------------------" + param.toString() + "\n" + toString1(param) + "end--------------------------" + param.toString() + "\n";

        }


        return value;
    }


    public static void ShowLog2(String index, Object param) {

        Log.e("wodelog", "wodelog:" + index + " === " + haha(param));
    }

    public static void ShowLog1(Object param) {

        Log.e("wodelog", "wodelog:" + haha(param));
    }

    private static String toString1(Object clazs) {
        StringBuffer sb = new StringBuffer();
        if (isJavaClass(clazs.getClass())) {
            return clazs.getClass().getSimpleName();
        }
        return getParamAndValue(sb, clazs);
    }

    private static String getParamAndValue(StringBuffer sb, Object clazs) {
        if (clazs == null) {
            sb.append("=null;");
        } else {
            Field[] fields = clazs.getClass().getDeclaredFields();
            //  Log.e("wodelog", "getParamAndValue:" + fields.length);
            for (Field f : fields) {
                f.setAccessible(true);

                try {
                    Object haha = f.get(clazs);


                    if (isJavaClass(f.getType())) {

                        if (haha instanceof byte[]) {
                            haha = new StringBuilder(byte2hex((byte[]) haha)).toString();
                        } else if (haha instanceof char[]) {
                            haha = new StringBuilder(new String((char[]) haha)).toString();
                        }

                        sb.append(new StringBuilder(String.valueOf(f.getType().getName())).append(" ").append(f.getName()).append("=").append(haha).append("\n").toString());
                    } else {
                        sb.append(f.getType() + " : " + f.getName() + ":\n [\n");
                        getParamAndValue(sb, haha);
                        sb.append(" ] \n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    public static boolean isWrapClass(Class clazz) {
        return
                (clazz.equals(String.class) ||
                        clazz.equals(Integer.class) ||
                        clazz.equals(Byte.class) ||
                        clazz.equals(Long.class) ||
                        clazz.equals(Double.class) ||
                        clazz.equals(Float.class) ||
                        clazz.equals(Character.class) ||
                        clazz.equals(Short.class) ||
                        clazz.equals(BigDecimal.class) ||
                        clazz.equals(BigInteger.class) ||
                        clazz.equals(Boolean.class) ||
                        clazz.equals(Date.class) ||
                        clazz.isPrimitive()
                );
    }


    public static boolean isJavaClass(Class clz) {

        boolean xx;

        if (isWrapClass(clz)) {
            xx = true;
        } else {
            boolean contains = clz.getClassLoader().toString().contains("dalvik.system");
            if (contains) {
                xx = false;
            } else {
                xx = true;
            }

        }


        return xx;

    }


    public static final String byte2hex(byte b[]) {
        if (b == null) {
            throw new IllegalArgumentException(
                    "Argument b ( byte array ) is null! ");
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

}