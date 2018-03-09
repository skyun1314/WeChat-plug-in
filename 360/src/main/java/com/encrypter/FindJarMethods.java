package com.encrypter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FindJarMethods {

	private static List<String> allClassJava = new ArrayList<>();
	private static List<String> allClassAndroid = new ArrayList<>();
	
	public static Set<String> allClassMethodJava = new HashSet<>();
	public static Set<String> allClassMethodAndroid = new HashSet<>();

	//添加查找field字段
	public static Set<String> allClassFieldsJava = new HashSet<>();
	public static Set<String> allClassFieldsAndroid = new HashSet<>();
	
	public static void getAllMethods() throws IOException, ClassNotFoundException {
		getClassNameJavaFromJar(null);
		getClassNameAndroidFromJar(null);
		getAllFieldsAndMethodsJavaFromJar(null);
		getAllFieldsAndMethodsAndroidFromJar(null);
		
		/*
		getClassNameAndroid(new File("android\\"));
		getClassNameJava(new File("rt\\"));
		getAllMethodsAndroid();
		getAllMethodsJava();
		getAllFieldsAndroid();
		getAllFieldsJava();
		*/
	}
	
	private static void getClassNameJavaFromJar(String path) throws IOException {
		if(path == null || path.isEmpty()) {
			path = System.getProperty("java.home") + "/lib/rt.jar";
		}
		JarFile jarJava = new JarFile(path);
		Enumeration<JarEntry> files = jarJava.entries();
		while(files.hasMoreElements())
		{
			JarEntry entry = (JarEntry) files.nextElement();  
            if (entry.getName().endsWith(".class"))  
            {
            	String clazzName = entry.getName();
            	clazzName = clazzName.replaceAll("/", ".");
            	clazzName = clazzName.substring(0, clazzName.lastIndexOf("."));
				int dollorPos = clazzName.indexOf("$");
				if(dollorPos != -1) continue;
				allClassJava.add(clazzName);
            } 
		}
		jarJava.close();
	}
	
	private static void getClassNameAndroidFromJar(String path) throws IOException {
		if(path == null || path.isEmpty()) {
			path = System.getProperty("user.dir") + "/libs/android.jar";
		}
		JarFile jarJava = new JarFile(path);
		Enumeration<JarEntry> files = jarJava.entries();
		while(files.hasMoreElements())
		{
			JarEntry entry = (JarEntry) files.nextElement();  
            if (entry.getName().endsWith(".class"))  
            {
            	String clazzName = entry.getName();
            	clazzName = clazzName.replaceAll("/", ".");
            	clazzName = clazzName.substring(0, clazzName.lastIndexOf("."));
				int dollorPos = (clazzName.indexOf("$") == -1) ? clazzName.length() : clazzName.indexOf("$");
				clazzName = clazzName.substring(0, dollorPos);
				allClassAndroid.add(clazzName);
            } 
		}
		jarJava.close();
	}
	
	private static void getAllFieldsAndMethodsJavaFromJar(String path) throws ClassNotFoundException {
		if(path == null || path.isEmpty()) {
			path = System.getProperty("java.home") + "/lib/rt.jar";
		}
		URL url = null;
		try {
			url = new URL("file:" + path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
		
		URLClassLoader clazzLoader = new URLClassLoader(new URL[] { url }, Thread.currentThread().getContextClassLoader());
		Class<?> jarClass = null;
		for (int i = 0; i < allClassJava.size(); i++)
		{
			jarClass = clazzLoader.loadClass(allClassJava.get(i));
			if (jarClass != null) {
				
				Field[] fields = jarClass.getFields();
				for (int j = 0; j < fields.length; ++j) {
					String fieldName = fields[j].getName();
					allClassFieldsJava.add(fieldName);
				}
				
				Method[] methods = jarClass.getDeclaredMethods();
				for (int j = 0; j < methods.length; j++) {
					String methodName = methods[j].getName();
					allClassMethodJava.add(methodName);
				}
			}
		}
		try {
			clazzLoader.close();
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	
	private static void getAllFieldsAndMethodsAndroidFromJar(String path) throws ClassNotFoundException {
		if(path == null || path.isEmpty()) {
			path = System.getProperty("user.dir") + "/libs/android.jar";
		}
		URL url = null;
		try {
			url = new URL("file:" + path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
		
		URLClassLoader clazzLoader = new URLClassLoader(new URL[] { url }, Thread.currentThread().getContextClassLoader());
		Class<?> jarClass = null;
		for (int i = 0; i < allClassAndroid.size(); i++)
		{
			jarClass = clazzLoader.loadClass(allClassAndroid.get(i));
			if (jarClass != null) {
				Field[] fields = jarClass.getFields();
				for (int j = 0; j < fields.length; ++j) {
					String fieldName = fields[j].getName();
					allClassFieldsAndroid.add(fieldName);
				}
				
				Method[] methods = jarClass.getDeclaredMethods();
				for (int j = 0; j < methods.length; j++) {
					String methodName = methods[j].getName();
					allClassFieldsAndroid.add(methodName);
				}
			}
		}
		try {
			clazzLoader.close();
		} catch (IOException e) {
			e.printStackTrace();
		};
	}

	/*
	private static void getAllFieldsJava() throws ClassNotFoundException {
		Class<?> jarClass = null;
		//String className;
		
		for (int i = 0; i < allClassJava.size(); i++) {
			//System.out.println(i + "$JavaClass F:" + allClassJava.get(i));
			jarClass = Class.forName(allClassJava.get(i));
			if (jarClass != null) {
				Field[] fields = jarClass.getFields();
				//className = jarClass.getName();
				for (int j = 0; j < fields.length; j++) {
					allClassFieldsJava.add(fields[j].getName());
				}
			}
		}
	}

	private static void getAllFieldsAndroid() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		Class<?> jarClass = null;
		//String className;
		
		for (int i = 0; i < allClassAndroid.size(); i++) {
			//System.out.println(i + "$AndroidClass F:" + allClassAndroid.get(i));
			jarClass = Class.forName(allClassAndroid.get(i));
			if (jarClass != null) {
				Field[] fields = jarClass.getFields();
				//className=jarClass.getName();
				for (int j = 0; j < fields.length; j++) {
					allClassFieldsAndroid.add(fields[j].getName());
				}
			}
		}
	}

	private static void getAllMethodsAndroid() throws ClassNotFoundException {
		
		Class<?> jarClass = null;
		//String className;
		for (int i = 0; i < allClassAndroid.size(); i++) {
			//System.out.println(i + "$AndroidClass M:" + allClassAndroid.get(i));
			jarClass = Class.forName(allClassAndroid.get(i));
			if (jarClass != null) {
				Method[] methods = jarClass.getMethods();
				//className = jarClass.getName();
				for (int j = 0; j < methods.length; j++) {
					allClassMethodAndroid.add(methods[j].getName());
				}
			}
		}
	}

	private static void getAllMethodsJava() throws ClassNotFoundException {
		//int count = 0;
		Class<?> jarClass = null;

		for (int i = 0; i < allClassJava.size(); i++) {
			//System.out.println(i + "$JavaClass M:" + allClassJava.get(i));
			jarClass = Class.forName(allClassJava.get(i));
			if (jarClass != null) {
				Method[] methods = jarClass.getMethods();

				for (int j = 0; j < methods.length; j++) {
					allClassMethodJava.add(methods[j].getName());
				}
			}
		}
	}
	
	private static void getClassNameJava(File file){
//		System.out.println("file="+file.getPath());
		if (!file.isDirectory()) {
			allClassJava.add(file.getName());
			System.out.println(file.getPath().toString());
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					getClassNameJava(files[i]);
				} else {
					String path = file.getPath().toString();
					path = path.replaceAll("\\\\", ".");

					if (files[i].getName().endsWith(".class")) {
						int dotPos = files[i].getName().indexOf(".");
						int dollorPos = files[i].getName().indexOf("$");
						if(dollorPos != -1) continue;
						String className = files[i].getName().substring(0, dotPos);
						className = path + "." + className;
						className = className.substring(3, className.length());
						allClassJava.add(className);
					}

				}
			}
		}
	}

	private static void getClassNameAndroid(File file) throws IOException {

		if (!file.isDirectory()) {
			allClassAndroid.add(file.getName());
			System.out.println(file.getPath().toString());
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					getClassNameAndroid(files[i]);
				} else {
					String path = file.getPath().toString();
					path = path.replaceAll("\\\\", ".");

					if (files[i].getName().endsWith(".class")) {
						int dotPos = files[i].getName().indexOf(".");
						int dollorPos = (files[i].getName().indexOf("$") == -1) ? files[i].getName().length() : files[i].getName().indexOf("$");
						String className = files[i].getName().substring(0, (dotPos < dollorPos ? dotPos : dollorPos));
						className = path + "." + className;
						className = className.substring(8, className.length());
						allClassAndroid.add(className);
					}

				}
			}
		}
	}
	*/
}
