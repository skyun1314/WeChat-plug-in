package com.nearme.common.util;

import android.text.TextUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectHelp {
    public static Object getObjectByConstructor(String str, Class[] clsArr, Object[] objArr) {
        Object obj = null;
        try {
            Constructor declaredConstructor = Class.forName(str).getDeclaredConstructor(clsArr);
            declaredConstructor.setAccessible(true);
            obj = declaredConstructor.newInstance(objArr);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return obj;
    }

    public static void modifyFileValue(Object obj, String str, String str2) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(obj, str2);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static Object getFieldValue(Object obj, String str) {
        Object obj2 = null;
        try {
            Field declaredField = obj.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            obj2 = declaredField.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return obj2;
    }

    public static Object invoke(Object obj, String str, Class[] clsArr, Object[] objArr) {
        Object obj2 = null;
        if (!(obj == null || TextUtils.isEmpty(str))) {
            try {
                Method method = getMethod(obj.getClass(), str, clsArr);
                if (method != null) {
                    method.setAccessible(true);
                    obj2 = method.invoke(obj, objArr);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
            }
        }
        return obj2;
    }

    public static Object invokeThrowException(Object obj, String str, Class[] clsArr, Object[] objArr) throws Exception {
        if (obj == null || TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("obj == null or method is null");
        }
        Method method = getMethod(obj.getClass(), str, clsArr);
        if (method != null) {
            method.setAccessible(true);
            return method.invoke(obj, objArr);
        }
        throw new IllegalStateException("method is null");
    }

    public static Method getMethod(Class cls, String str, Class[] clsArr) {
        Method method = null;
        if (cls == null || TextUtils.isEmpty(str)) {
            return method;
        }
        try {
            return cls.getDeclaredMethod(str, clsArr);
        } catch (Exception e) {
            try {
                return cls.getMethod(str, clsArr);
            } catch (Exception e2) {
                if (cls.getSuperclass() != null) {
                    return getMethod(cls.getSuperclass(), str, clsArr);
                }
                return method;
            }
        }
    }

    public static void setFieldValue(Class cls, Object obj, String str, Object obj2) {
        if ((obj != null || cls != null) && !TextUtils.isEmpty(str)) {
            if (obj != null) {
                cls = obj.getClass();
            }
            Field field = getField(cls, str);
            if (field != null) {
                field.setAccessible(true);
                try {
                    field.set(obj, obj2);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static Object getFieldValue(Class cls, Object obj, String str) {
        Object obj2 = null;
        if (!((obj == null && cls == null) || TextUtils.isEmpty(str))) {
            if (obj != null) {
                cls = obj.getClass();
            }
            Field field = getField(cls, str);
            if (field != null) {
                field.setAccessible(true);
                try {
                    obj2 = field.get(obj);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return obj2;
    }

    public static Field getField(Class cls, String str) {
        Field field = null;
        if (!(cls == null || TextUtils.isEmpty(str))) {
            try {
                field = cls.getDeclaredField(str);
            } catch (NoSuchFieldException e) {
                try {
                    field = cls.getField(str);
                } catch (NoSuchFieldException e2) {
                    if (cls.getSuperclass() != null) {
                        field = getField(cls.getSuperclass(), str);
                        e2.printStackTrace();
                    }
                }
                e.printStackTrace();
            }
        }
        return field;
    }

    public static Constructor getConstructor(String str, Class[] clsArr) {
        Constructor constructor = null;
        try {
            Class cls = Class.forName(str);
            if (cls != null) {
                constructor = cls.getDeclaredConstructor(clsArr);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        }
        return constructor;
    }

    public static Constructor getConstructorForInnerClass(String str, Class[] clsArr) {
        int i = 0;
        Constructor constructor = null;
        String str2 = "";
        String[] split = str.split("\\$");
        Class[] clsArr2 = new Class[(clsArr.length + 1)];
        if (split.length >= 1) {
            try {
                Class cls = Class.forName(split[0]);
                clsArr2[0] = cls;
                while (i < clsArr.length) {
                    clsArr2[i + 1] = clsArr[i];
                    i++;
                }
                Class cls2 = Class.forName(str);
                if (!(cls == null || cls2 == null)) {
                    constructor = cls.getDeclaredConstructor(clsArr2);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            }
        }
        return constructor;
    }

    public static Object getObjectFromInnerClass(String str, Class[] clsArr, Object[] objArr, Class[] clsArr2, Object[] objArr2) {
        Object obj = null;
        try {
            if (getConstructorForInnerClass(str, clsArr2) != null) {
                Object[] objArr3 = new Object[(objArr2.length + 1)];
                if (objArr3.length >= 1) {
                    String str2 = "";
                    String[] split = str.split("\\$");
                    if (split.length > 0) {
                        objArr3[0] = getConstructor(split[0], clsArr).newInstance(objArr);
                        for (int i = 0; i < objArr2.length; i++) {
                            objArr3[i + 1] = objArr2[i];
                        }
                        obj = getConstructorForInnerClass(str, clsArr2).newInstance(objArr3);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Class getClassFromName(String str) {
        Class cls = null;
        try {
            cls = Class.forName(str);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }

    public static Object invokeStatic(Class cls, String str, Class[] clsArr, Object[] objArr) {
        Object obj = null;
        if (!(cls == null || TextUtils.isEmpty(str))) {
            try {
                Method method = getMethod(cls, str, clsArr);
                if (method != null) {
                    method.setAccessible(true);
                    obj = method.invoke(null, objArr);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
            }
        }
        return obj;
    }
}