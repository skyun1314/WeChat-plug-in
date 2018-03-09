package com.encrypter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.d2j.dex.writer.ev.EncodedAnnotation.AnnotationElement;
import com.googlecode.d2j.dex.writer.item.FieldIdItem;
import com.googlecode.d2j.dex.writer.item.MethodIdItem;
import com.googlecode.d2j.dex.writer.item.ProtoIdItem;
import com.googlecode.d2j.dex.writer.item.StringIdItem;
import com.googlecode.d2j.dex.writer.item.TypeIdItem;

public class Debuger {
	public static boolean _DEBUG = true;
	private static int MODE = 2;	//1:SIMPLE 2：FULL
	
	private static List<String> stringIdList = new ArrayList<String>();
	private static List<String> typeIdList = new ArrayList<String>();
	private static List<String> protoIdList = new ArrayList<String>();
	private static List<String> fieldIdList = new ArrayList<String>();
	private static List<String> methodIdList = new ArrayList<String>();
	private static List<String> annotationList = new ArrayList<String>();
	
	private static List<String> doEncryptListMethod = new ArrayList<String>();
	private static List<String> doEncryptListField = new ArrayList<String>();
	
	public static void AddStringInfo(final StringIdItem item) {
		if(!_DEBUG)	return;
		String info = item.stringData.offset + ": " + item.stringData.string;
		stringIdList.add(info);
	}
	
	public static void AddTypeInfo(final TypeIdItem item) {
		if(!_DEBUG)	return;
		String info = item.descriptor.index + ": " + item.descriptor.stringData.string;
		typeIdList.add(info);
	}
	
	public static void AddProtoInfo(final ProtoIdItem item) {
		if(!_DEBUG)	return;
		String info = item.ret.index + ": " + item.ret.descriptor.stringData.string;
		protoIdList.add(info);
	}
	
	public static void AddFieldInfo(final FieldIdItem item) {
		if(!_DEBUG)	return;
		String info = item.index + "@->" + item.clazz.index + ": " + item.clazz.descriptor.stringData.string + "-> " + item.name.stringData.string;
		fieldIdList.add(info);
	}
	
	public static void AddMethodInfo(final MethodIdItem item) {
		if(!_DEBUG)	return;
		String info = item.clazz.index + ": " + item.clazz.descriptor.stringData.string + " ->" + item.name.stringData.string;
		methodIdList.add(info);
	}
	
	public static void AddAnnotationInfo(final AnnotationElement ae, final int offset) {
		if(!_DEBUG)	return;
		String info = offset + " :" + ae.name.index + " ->" + ae.name.stringData.string;
		annotationList.add(info);
	}
	
	public static void AddDoEncryptInfoField(final String clazz, final String name, final String type, final String encryptedName) {
		if(!_DEBUG)	return;
		String info = null;
		if(MODE == 2) {
			info = clazz + "$->" + name + " <" + type + ">" + "@->" + encryptedName;
		} else if(MODE == 1) {
			info = name + "@->" + encryptedName;
		}
		doEncryptListField.add(info);
	}
	
	public static void AddDoEncryptInfoMethod(final String name, final String encryptedName) {
		if(!_DEBUG)	return;
		String info = name + "@->" + encryptedName;
		doEncryptListMethod.add(info);
	}
	
	private static List<String> inPackageFieldList = new ArrayList<String>();
	public static void AddInPackageField(final String name) {
		if(!_DEBUG)	return;
		inPackageFieldList.add(name);
	}
	
	public static void PrintInfos() throws FileNotFoundException {
		if(!_DEBUG)	return;
		int size = 0;
		File fps = new File("G:/stringList.txt");
        File fpt = new File("G:/typeList.txt");
        File fpp = new File("G:/protoList.txt");
        File fpf = new File("G:/fieldList.txt");
        File fpm = new File("G:/methodList.txt");
        File fpea = new File("G:/annotationList.txt");
        
        File fpmethod = new File("G:/doEncryptListMethod.txt");
        File fpfield = new File("G:/doEncryptListField.txt");
        
        File fppackage = new File("G:/packageField.txt");
        
        PrintWriter pw= new PrintWriter(fps);
        size = stringIdList.size();
        for(int i = 0; i < size; ++i)
        {
        	pw.println(stringIdList.get(i));
        }
        pw.close();
        
        pw= new PrintWriter(fpt);
        size = typeIdList.size();
        for(int i = 0; i < size; ++i)
        {
        	pw.println(typeIdList.get(i));
        }
        pw.close();
        
        pw= new PrintWriter(fpp);
        size = protoIdList.size();
        for(int i = 0; i < size; ++i)
        {
        	pw.println(protoIdList.get(i));
        }
        pw.close();
        
        pw= new PrintWriter(fpf);
        size = fieldIdList.size();
        for(int i = 0; i < size; ++i)
        {
        	pw.println(fieldIdList.get(i));
        }
        pw.close();
        
        pw= new PrintWriter(fpm);
        size = methodIdList.size();
        for(int i = 0; i < size; ++i)
        {
        	pw.println(methodIdList.get(i));
        }
        pw.close();
        
        pw= new PrintWriter(fpea);
        size = annotationList.size();
        for(int i = 0; i < size; ++i)
        {
        	pw.println(annotationList.get(i));
        }
        pw.close();
        
        pw= new PrintWriter(fpmethod);
        size = doEncryptListMethod.size();
        for(int i = 0; i < size; ++i)
        {
        	pw.println(doEncryptListMethod.get(i));
        }
        pw.close();
        
        pw= new PrintWriter(fpfield);
        size = doEncryptListField.size();
        for(int i = 0; i < size; ++i)
        {
        	pw.println(doEncryptListField.get(i));
        }
        pw.close();
        
        pw= new PrintWriter(fppackage);
        size = inPackageFieldList.size();
        for(int i = 0; i < size; ++i)
        {
        	pw.println(inPackageFieldList.get(i));
        }
        pw.close();
	}
}
