package com.example.zk.myapplication.com;

import android.util.Log;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MyLog {
    public static void ShowLog2(String index, Object param) {
        String value = null;
        if (param instanceof Integer) {
            value = new StringBuilder(String.valueOf(((Integer) param).intValue())).toString();
        } else if (param instanceof String) {
            value = new StringBuilder(String.valueOf((String) param)).toString();
        }else if (param instanceof byte[]) {
            value = new StringBuilder(byte2hex((byte[]) param)).toString();
        }  else if (param instanceof Double) {
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
        } else if (param instanceof Object) {
            Log.e("wodelog", param.toString() + "start--------------------------\n");
            Log.e("wodelog", toString1(param)+ " --------------------------\n");
            Log.e("wodelog", param.toString() + "end--------------------------\n");
            return;
        }
        Log.e("wodelog", "wodelog:" + index + " === " + value);
    }

    public static void ShowLog1(Object param) {
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
        } else if (param instanceof Object) {
            Log.e("wodelog", param.toString() + "start--------------------------\n");
            Log.e("wodelog", toString1(param));
            Log.e("wodelog", param.toString() + "end--------------------------\n");
            return;
        }
        Log.e("wodelog", "wodelog:" + value);
    }

    private static String toString1(Object clazs) {
        StringBuffer sb = new StringBuffer();
        if (isJavaClass(clazs.getClass())) {
            return clazs.getClass().getSimpleName();
        }
        return getParamAndValue(sb, clazs);
    }

    private static String getParamAndValue(StringBuffer sb, Object clazs) {
        Exception e;
        if (clazs == null) {
            sb.append("=null;");
        } else {
            Field[] fields = clazs.getClass().getDeclaredFields();
            Log.e("wodelog", "getParamAndValue:" + fields.length);
            for (Field f : fields) {
                f.setAccessible(true);
                if (isJavaClass(f.getType())) {
                    try {
                        f.get(clazs);
                        sb.append(new StringBuilder(String.valueOf(f.getType().getName())).append(" ").append(f.getName()).append("=").append(f.get(clazs)).append("\n").toString());
                    } catch (IllegalArgumentException e2) {
                        e = e2;
                        e.printStackTrace();
                    } catch (IllegalAccessException e3) {
                        e = e3;
                        e.printStackTrace();
                    }
                } else {
                    try {
                        sb.append(f.getType() + " : " + f.getName() + ":\n [");
                        getParamAndValue(sb, f.get(clazs));
                        sb.append(" ] \n");
                    } catch (IllegalAccessException e4) {
                        e = e4;
                    } catch (IllegalArgumentException e5) {
                        e = e5;
                    }
                }
            }
        }
        return sb.toString();
    }

    public static boolean isJavaClass(Class clz) {
        if ((clz.getClassLoader().toString()).contains("dalvik.system")) {
            return false;
        }
        return true;
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