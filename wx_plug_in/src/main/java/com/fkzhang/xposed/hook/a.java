package com.fkzhang.xposed.hook;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import de.robv.android.xposed.XposedBridge;
import java.lang.reflect.Method;

public class a extends DexClassLoader {
    private final ClassLoader ʻ;
    private int ʼ;

    public a(String str, ClassLoader classLoader, int i) {
        super(str, str, str, classLoader);
        this.ʻ = classLoader;
        this.ʼ = i;
    }

    private Class ʻ(String str) {
        if (this.ʼ == -1) {
            return null;
        }
        return (Class) ʻ(DexFile.class.getName(), "defineClassNative", new Class[]{String.class, ClassLoader.class, Integer.TYPE}, new Object[]{str, this.ʻ, Integer.valueOf(this.ʼ)});
    }

    private Object ʻ(String str, String str2, Class[] clsArr, Object[] objArr) {
        Object obj = null;
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
            declaredMethod.setAccessible(true);
            obj = declaredMethod.invoke(null, objArr);
        } catch (Throwable th) {
            XposedBridge.log(th);
        }
        return obj;
    }

    private String[] ʻ() {
        return (String[]) ʻ(DexFile.class.getName(), "getClassNameList", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(this.ʼ)});
    }

    protected Class<?> findClass(String str) throws ClassNotFoundException {
        Class<?> cls = null;
        for (String str2 : ʻ()) {
            if (str2.equals(str)) {
                cls = ʻ(str2.replace('.', '/'));
            } else {
                ʻ(str2.replace('.', '/'));
            }
        }
        return cls == null ? super.findClass(str) : cls;
    }

    protected Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
        return this.ʼ == -1 ? null : super.loadClass(str, z);
    }
}
