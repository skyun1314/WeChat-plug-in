package com;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class MyLog {

    static String kongge_str = "";

    /**
     * @param param
     * @return
     */
    public static String haha(Object param) {


        String value = "";
        if (param == null) {
            value = "";
        } else if (param instanceof Integer) {
            value = new StringBuilder(String.valueOf(((Integer) param).intValue())).toString();
        } else if (param instanceof String||param instanceof JSONObject) {
            value = new StringBuilder(param.toString()).toString();
        } else if (param instanceof byte[]) {
            value = new StringBuilder(byte2hex((byte[]) param)).toString();
        } else if (param instanceof char[]) {
            value = new StringBuilder(new String((char[]) param)).toString();
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
            String end = kongge_str + "(List)end--------------------------";
            if (((List) param).size() == 0) {
                end += param.toString() + "\n";
            } else {
                end += ((List) param).getClass() + "\n";
            }

            String value1 = "";
            value = "\n" + kongge_str + "(List)start--------------------------";

            String tmp = kongge_str;
            for (int i = 0; i < ((List) param).size(); i++) {
                Object o = ((List) param).get(i);
                value1 = tmp + value1 + "List:" + "(" + i + ")" + ":" + toString1(o) + "\n";
            }

            value += (param).toString() + "\n" + value1 + end;

        } else if (param instanceof Map || param instanceof HashMap) {
            Map<String, Object> param1 = ((Map<String, Object>) param);

            for (Map.Entry<String, Object> entry : param1.entrySet()) {
                value += "\n" + kongge_str + "key= " + entry.getKey() + "\n" + kongge_str + "value= " + haha(entry.getValue());
            }
        } else if (param instanceof Object) {
            String end = kongge_str + "end--------------------------" + param.getClass().toString() + "\n";
            value = "\n" + kongge_str + "start--------------------------";
            value += param.getClass().toString() + "\n" + kongge_str + toString1(param) + end;


        }


        return value;
    }


    public static void ShowLog2(String index, Object param) {

        Log.e("wodelog", "wodelog:" + index + " === " + haha(param));
        kongge_str = "";
    }

    public static void ShowLog1(Object param) {

        Log.e("wodelog", "wodelog:" + haha(param));
        kongge_str = "";
    }

    private static String toString1(Object clazs) {
        StringBuffer sb = new StringBuffer();
        if (isJavaClass(clazs.getClass())) {
            return clazs.getClass().getSimpleName();
        }
        return getParamAndValue(sb, clazs);
    }

    private static String getParamAndValue(StringBuffer sb, Object clazs) {

        kongge_str = kongge_str + "   ";
        String tmp = kongge_str;
        if (clazs == null) {
            sb.append("");
        } else {
            Field[] fields = clazs.getClass().getDeclaredFields();
            //  Log.setRequestHeader("wodelog", "getParamAndValue:" + fields.length);
            sb.append(clazs.getClass() + ":\n " + kongge_str + "[\n");
            String end = kongge_str + " ] \n";
            for (Field f : fields) {
                f.setAccessible(true);

                try {
                    Object haha = f.get(clazs);

                    sb.append(tmp + new StringBuilder(String.valueOf(f.getType().getName())).append(" ").append(f.getName()).append("=").append(haha(haha)).append("\n").toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sb.append(end);
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



/*
* 调用其他apk函数
* */


    public static Object CMSform(String packageName, String class_name, Context thisatv, String Method_name, Class<?>[] pamaer_type, Object[] pamaer) {
        Object ret = null;
        try {
            Context c = thisatv.createPackageContext(packageName, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
            Class<?> clazzz = c.getClassLoader().loadClass(class_name);
            Method method = clazzz.getMethod(Method_name, pamaer_type);
            Method declaredMethod = clazzz.getDeclaredMethod(Method_name, pamaer_type);
            Object o = clazzz.newInstance();

            method.setAccessible(true);
            declaredMethod.setAccessible(true);
            //   method.invoke(o, pamaer);
            ret = declaredMethod.invoke(o, pamaer);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("wodelog", "CMSform:" + e.toString());
        }
        return ret;
    }


    public static <T> T deserializer(byte[] bArr, Class<T> cls, T t) {

        ///  Log.setRequestHeader("wodelog",RequiredWrapDto.class.getName());
        Log.e("wodelog", "测试类打印byte[]" + new String(bArr));


        try {
            ProtobufIOUtil.mergeFrom(bArr, t, oc(cls));
            return t;
        } catch (Throwable e) {
            Log.e("wodelog", "错误：" + e.toString());
        }
        return null;
    }


    public static byte[] readSaveFile() {
        FileInputStream inputStream;

        try {
            inputStream = new FileInputStream(file_path);
            byte temp[] = new byte[1024];
            int len = 0;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            while ((len = inputStream.read(temp)) > 0) {
                outStream.write(temp, 0, len);
            }
            inputStream.close();
            return outStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String file_path = "/sdcard/do_response.txt";

    public static void savePackageFile(byte[] msg) {
        FileOutputStream outputStream;

        try {

            //outputStream = openFileOutput("do_response.txt", Context.MODE_PRIVATE);
            outputStream = new FileOutputStream(file_path);

            outputStream.write(msg);
            outputStream.flush();
            outputStream.close();
            Log.e("wodelog", "文件写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* compiled from: SchemaUtils */
    public static Schema oc(Class cls) {

           /* Schema<T> nVar = (Schema) axxx.get(cls);
            if (nVar != null) {
                return nVar;
            }*/
        Schema a = RuntimeSchema.createFrom((Class) cls);
        // axxx.put(cls, analysis_home_page);
        return a;
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