package com.erry.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectUtil {
	public static Object invoke(Object object, String methodName){
		return invoke(object, methodName, null, null);
	}
	public static Object invoke(Object object, String methodName, Object[] objects){
		return invoke(object, methodName, objectArrayToClassArray(objects), objects);
	}
	public static Object invoke(Object object, String methodName, Class<?>[] parameterTypes, Object[] objects){
		Object returnValue = null;
		if(parameterTypes == null)parameterTypes = new Class<?>[]{};
		if(objects == null)objects = new Object[]{};
		try {
			Method method = object.getClass().getMethod(methodName, parameterTypes);
			returnValue = method.invoke(object, objects);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	private static Class<?>[] objectArrayToClassArray(Object[] objects){
		List<Class<?>> classList = new ArrayList<Class<?>>();
		for(Object o: objects){
			classList.add(o.getClass());
		}
		Class<?>[] array = new Class<?>[classList.size()];
		return classList.toArray(array);
	}
	public static boolean isSameObject(Object... objects){
		if(objects==null)return false;
		if(objects.length ==0) return false;
		if(objects[0]==null)return false;
		if(isSameClass(objects)==false)
		if(isSameClass(objects)==false)return false;
		List<Method> methods = new ArrayList<Method>();
		try {
			methods =Arrays.asList(Class.forName(objects[0].getClass().getName().toString()).getDeclaredMethods());
			return collectionsCheck(Arrays.asList(objects),methods);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isSameClass(Object... objects){
//		if(isOneObject(objects)==true)return true;
		for(int i = 0; i < objects.length - 1; i++){
			for(int j = i + 1; j< objects.length; j++){
				if (objects[i] == objects[j])continue;
				if (objects[i].getClass()!=objects[j].getClass()){
					return false;
				}
			}
		}
		return true;
	}
	public static boolean collectionsCheck(List<Object> objects,List<Method> methods){
		boolean result = true;
		if(objects==null||methods==null)return false;
		if(objects.isEmpty()||methods.isEmpty())return false;
		try {
			Class.forName(objects.get(0).getClass().getName().toString());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object a,b;
		for(Method method : methods){
			if(method.getName().startsWith("get")==false)continue;
			for(int i = 0; i < objects.size()-1; i++){
				for(int j = i + 1; j<objects.size(); j++){
					if(result == false) return false;
						try {
							a = (Object) method.invoke(objects.get(i), null);
							b = (Object) method.invoke(objects.get(j), null);
							if(a == null && b == null){
								continue;
							}else if(a ==null || b== null){
								return false;
							}else{
								if(a.toString().equals(b.toString())==false)
									return false;
							}
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
//							result=false;
						}
//						a = method.get((Cat)(objects.get(i)));
//						b = method.get((Cat)(objects.get(j)));
				}
			}
		}
		return true;
	}

	public static Method getMethod(Object o, String methodName, Class[] classes){
		Method method = null;
		try {
			method = o.getClass().getMethod(methodName, classes);
		} catch (NoSuchMethodException | SecurityException e) {
//			e.printStackTrace();
		}
		return method;
	}
}
