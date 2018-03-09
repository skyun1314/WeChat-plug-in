package com.encrypter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.d2j.dex.writer.item.BaseItem;
import com.googlecode.d2j.dex.writer.item.ClassDataItem;
import com.googlecode.d2j.dex.writer.item.ClassDefItem;
import com.googlecode.d2j.dex.writer.item.ConstPool;
import com.googlecode.d2j.dex.writer.item.FieldIdItem;
import com.googlecode.d2j.dex.writer.item.MethodIdItem;
import com.googlecode.d2j.dex.writer.item.StringIdItem;
import com.googlecode.d2j.dex.writer.item.TypeIdItem;

public class Checker {
	public static void init() {
		if(strInInsnSet != null)			strInInsnSet.clear();
		if(nativeMethodSet != null)			nativeMethodSet.clear();
		if(namesInParcelableSet != null) 	namesInParcelableSet.clear();
		if(typeKeywordSet != null)			typeKeywordSet.clear();
		if(encryptedSet != null)			encryptedSet.clear();
		if(customViewSet != null)			customViewSet.clear();
		
		initTypeKeywordSet();
		
		try {
			FindJarMethods.getAllMethods();
		} catch(ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void init(ConstPool cp) {
		FindUserDefPackage(cp);
	}
	
	public static boolean checkField(FieldIdItem field) {
		String clazz = field.clazz.descriptor.stringData.string;
		String type = field.getTypeString();
		String name = field.name.stringData.string;
		
		if(!checkCustomPackageNameSet(clazz))	return false;
		if(checkInEncryptedSet(name))			return false;
		if(checkInJar(name))					return false;
		if(checkInsnSet(name))					return false;
		if(checkNativeMethodSet(name))			return false;
		if(checkParcelableSet(name))			return false;
		if(checkTypeKeywordSet(name))			return false;
		if(checkCustomViewSet(name))			return false;
		if(clazz.indexOf("$") != -1)			return false;
		if(type.indexOf("$") != -1)				return false;
		
		return true;
	}
	
	public static boolean checkMethod(MethodIdItem method) {
		String clazz = method.clazz.descriptor.stringData.string;
		String name = method.name.stringData.string;
		
		if(!checkCustomPackageNameSet(clazz))	return false;
		if(checkInEncryptedSet(name))			return false;
		if(checkInJar(name))					return false;
		if(checkInsnSet(name))					return false;
		if(checkNativeMethodSet(name))			return false;
		if(checkParcelableSet(name))			return false;
		if(checkTypeKeywordSet(name))			return false;
		if(checkCustomViewSet(name))			return false;
		if(clazz.indexOf("$") != -1)			return false;
		if(clazz.indexOf("BuildConfig") != -1)	return false;
		if(clazz.indexOf("lib") != -1)			return false;
		if(name.indexOf("$") != -1)				return false;
		if(name.indexOf("<") != -1)				return false;		//可以过滤<init>方法
		if(name.indexOf("_") != -1)				return false;
		
		return true;
	}
	
	//检测是否存在于系统字段或方法名中
	private static boolean checkInJar(String name) {
		return	FindJarMethods.allClassMethodAndroid.contains(name)
				|| FindJarMethods.allClassMethodJava.contains(name)
				|| FindJarMethods.allClassFieldsAndroid.contains(name)
				|| FindJarMethods.allClassFieldsJava.contains(name);
	}
	
	//指令中使用的字串集
	private static Set<String> strInInsnSet = new HashSet<String>();		//存放指令中用到的StringIdItem
	public static void appendStrInInsnSet(BaseItem idxItem) {
		Class<? extends BaseItem> clazz = idxItem.getClass();
        if(clazz.getSimpleName().compareTo("StringIdItem") == 0) {
        	StringIdItem item = (StringIdItem)idxItem;
        	if(item != null) {
        		strInInsnSet.add(item.stringData.string);
        	}
        }
	}
	
	private static boolean checkInsnSet(String name) {
		return strInInsnSet.contains(name);
	}
	
	//native方法集
	private static Set<String> nativeMethodSet = new HashSet<String>();
	public static void appendNativeMethodSet(MethodIdItem idxItem) {
		nativeMethodSet.add(idxItem.name.stringData.string);
	}
	
	private static boolean checkNativeMethodSet(String name) {
		return nativeMethodSet.contains(name);
	}
	
	//可序列化的类集
	private static Set<String> namesInParcelableSet = new HashSet<String>();
	public static void appendNamesInParcelableSet(ClassDefItem defItem) {
		//添加可序列化的字段和方法名
    	if(!defItem.isParcelable()) 	return;
    	ClassDataItem classData = defItem.classData;
    	appendAllInClazzData(namesInParcelableSet, classData);
	}
	
	private static boolean checkParcelableSet(String name) {
		return namesInParcelableSet.contains(name);
	}
	
	private static Set<String> typeKeywordSet = new HashSet<String>();			//关键字过滤集
	private static void initTypeKeywordSet() {
		typeKeywordSet.add("V");
		typeKeywordSet.add("Z");
		typeKeywordSet.add("B");
		typeKeywordSet.add("S");
		typeKeywordSet.add("C");
		typeKeywordSet.add("I");
		typeKeywordSet.add("J");
		typeKeywordSet.add("F");
		typeKeywordSet.add("D");
		typeKeywordSet.add("L");
		typeKeywordSet.add("[");
	}
	
	private static boolean checkTypeKeywordSet(String name) {
		return typeKeywordSet.contains(name);
	}
	
	//查找用户自定义包
	private static Set<String> customPackageNameSet = new HashSet<String>();
	private static List<String> customPackageNameList = null;
	private static void FindUserDefPackage(ConstPool cp) {
		if(customPackageNameSet == null) {
			customPackageNameSet = new HashSet<String>();
		}
		customPackageNameSet.clear();
		
		List<TypeIdItem> typeIdList = Arrays.asList(cp.types.values().toArray(new TypeIdItem[]{}));
		int size = typeIdList.size();
		for(int i = 0; i < size; ++i)
		{
			String des = typeIdList.get(i).descriptor.stringData.string;
			if (!des.startsWith("Landroid")
				&& !des.startsWith("Ljava")
				&& !des.startsWith("Lorg")
				&& !des.startsWith("Ldavilk"))

				if (des.indexOf("/R$") != -1) {
					int j = des.indexOf("R$");
					customPackageNameSet.add(des.substring(0, j));
				}
		}
		customPackageNameList = Arrays.asList(customPackageNameSet.toArray(new String[]{}));
		return;
	}
	
	private static boolean checkCustomPackageNameSet(String clazz) {
		for(int i = 0; i < customPackageNameList.size(); ++i)
		{
			boolean flag = clazz.startsWith(customPackageNameList.get(i));
			if(flag) return true;
		}
		return false;
	}
	
	//存放已经加密过的字符串，用于后边的比较
	private static Set<String> encryptedSet = new HashSet<String>();
	public static void appendEncryptedSet(String name) {
		encryptedSet.add(name);
	}
	
	private static boolean checkInEncryptedSet(String name) {
		return encryptedSet.contains(name);
	}
	
	//自定义View排除
	private static Set<String> customViewSet = new HashSet<String>();
	public static void appendCustomViewSet(ClassDefItem defItem) {
		if(!defItem.isViewChild())	return;
		ClassDataItem classData = defItem.classData;
		boolean isRealCustomView = false;
		if(classData.virtualMethods != null && !classData.virtualMethods.isEmpty()) {
			for(int i = 0; i < classData.virtualMethods.size(); ++i)
    		{
    			String name = classData.virtualMethods.get(i).method.name.stringData.string;
    			if(name.compareTo("onMeasure") == 0 || name.compareTo("onDraw") == 0) {
    				isRealCustomView = true;
    				break;
    			}
    		}
		}
		
		if(!isRealCustomView)	return;
		appendAllInClazzData(customViewSet, classData);
	}
	
	private static boolean checkCustomViewSet(String name) {
		return customViewSet.contains(name);
	}
	
	private static void appendAllInClazzData(Set<String> set, ClassDataItem classData) {
		if(classData.instanceFields != null && !classData.instanceFields.isEmpty()) {
    		for(int i = 0; i < classData.instanceFields.size(); ++i)
    		{
    			String name = classData.instanceFields.get(i).field.name.stringData.string;
    			set.add(name);
    		}
    	}
    		
    	if(classData.staticFields != null && !classData.staticFields.isEmpty()) {
    		for(int i = 0; i < classData.staticFields.size(); ++i)
    		{
    			String name = classData.staticFields.get(i).field.name.stringData.string;
    			set.add(name);
    		}
    	}
    		
		if(classData.directMethods != null && !classData.directMethods.isEmpty()) {
			for(int i = 0; i < classData.directMethods.size(); ++i)
    		{
    			String name = classData.directMethods.get(i).method.name.stringData.string;
    			set.add(name);
    		}
		}

		if(classData.virtualMethods != null && !classData.virtualMethods.isEmpty()) {
			for(int i = 0; i < classData.virtualMethods.size(); ++i)
    		{
    			String name = classData.virtualMethods.get(i).method.name.stringData.string;
    			set.add(name);
    		}
		}
	}
}
