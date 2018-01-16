package com.simple.rtti;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 将Map的值封装进对象
 * @author 于继伟
 *
 */
public class MapToObjectRtti {
	
	private static final String SET = "set";
	
	/**
	 * 将Map的值放入对象中，前提是Map的key和对象的属性名称要一致
	 * @param map
	 * @param obj
	 */
	public static void map2Obj(Map<String, Object> map, Object obj){
		// 获取obj的Class对象
		Class<? extends Object> clazz = obj.getClass();
		List<Method> setMethods = getSetter(clazz);
		
		// 遍历Map
		for(Map.Entry<String, Object> entry : map.entrySet()){
			String key = entry.getKey(); // key
			Object value = entry.getValue(); // value
			for(Method method : setMethods){
				String fieldName = method.getName().substring(3);
				// 将首字母转化为小写
				fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
				if(fieldName.equals(key)){
					try {
						method.invoke(obj, value);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}
	
	/**
	 * 获取对象的所有的set方法
	 * @param clazz
	 * @return
	 */
	private static List<Method> getSetter(Class<?> clazz){
		Method[] methods = clazz.getMethods();
		List<Method> list = new ArrayList<Method>();
		for(Method method : methods){
			String methodName = method.getName(); // 方法名
			if(methodName.startsWith(SET)){ // 如果是set方法
				list.add(method);
			}
		}
		return list;
	}
	
}
