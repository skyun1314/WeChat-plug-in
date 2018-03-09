package com.oppo;

import android.util.Log;

import com.oppo.cdo.common.domain.dto.RequiredWrapDto;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeEnv;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Created by zk on 2018/1/19.
 */

public class MySerialize {

    public static <T> byte[] serializeList(T t) {

        Class cls = t.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);


        try {
            Field field = RuntimeEnv.class.getField("MORPH_NON_FINAL_POJOS");
            field.setAccessible(true);
            field.set(null, Boolean.valueOf(true));
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }

        return ProtobufIOUtil.toByteArray(t, oc.a(cls), buffer);
    }


    public static <T> T deserializer(byte[] bArr, Class<T> cls, T t) {

      ///  Log.setRequestHeader("wodelog",RequiredWrapDto.class.getName());
      //  Log.setRequestHeader("wodelog",new String(bArr));


        try {

            ProtobufIOUtil.mergeFrom(bArr, t, oc.a(cls));
            return t;
        } catch (Throwable th) {
            IllegalStateException illegalStateException = new IllegalStateException(th);
        }
        return null;
    }





    /* compiled from: SchemaUtils */
    public static class oc {
        private static ConcurrentHashMap<Class<?>, Schema<?>> axxx = new ConcurrentHashMap();

        public static <T> Schema<T> a(Class<T> cls) {
           /* Schema<T> nVar = (Schema) axxx.get(cls);
            if (nVar != null) {
                return nVar;
            }*/
            Schema a = RuntimeSchema.createFrom((Class) cls);
           // axxx.put(cls, analysis_home_page);
            return a;
        }
    }
}
