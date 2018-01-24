package com.simple.rtti;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.bean.User;

/**
 * FastJson反射工具
 * @author 于继伟
 *
 */
public class FastJsonRtti {
	
	private static final String SET = "set";
	
	private static final String GET = "get";
	
	private static final String GET_CLASS = "getClass";
	
	/**
	 * JSON字符串转对象
	 * @param jsonData
	 * @param clazz
	 * @return
	 */
	public static Object jsonToObject(String jsonData, Class<? extends Object> clazz){
		Object obj = null; 
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		JSONObject json = JSONObject.parseObject(jsonData);
		List<Field> fields = getField(clazz);
		List<Method> setMethods = getSetter(clazz);
		Map<String, Method> map = new HashMap<String, Method>();
		for(Method method : setMethods){
			for(Field field : fields){
				String methodName = method.getName().toLowerCase();
				String fieldName = field.getName().toLowerCase();
				if(methodName.contains(fieldName)){ // 如果方法名包含了字段名称
					map.put(field.getName(), method);
				}
			}
		}
		for(Map.Entry<String, Method> entry : map.entrySet()){
			String key = entry.getKey();
			Method value = entry.getValue();
			try {
				if(!"".equals(json.getString(key)) || null != json.getString(key)){
					Class<?> type = value.getParameterTypes()[0];
					if(type == Integer.class){
						value.invoke(obj, Integer.parseInt(json.getString(key)));
					} else if(type == Double.class){
						value.invoke(obj, Double.parseDouble(json.getString(key)));
					} else if(type == String.class){
						value.invoke(obj, String.valueOf(json.getString(key)));
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	/**
	 * 对象数组转JSON数组
	 * @param objs
	 * @return
	 */
	@SuppressWarnings("null")
	public static JSONArray arrayToJson(Object[] objs){
		if(null == objs && objs.length == 0){
			return null;
		}
		JSONArray jsonArray = new JSONArray();
		for(Object obj : objs){
			JSONObject jsonObject = new JSONObject();
			Class<? extends Object> clazz = obj.getClass();
			List<Method> getMethods = getGetMethod(clazz); // 获取对象所有的get方法
			Field[] fields = clazz.getDeclaredFields();
			for(Method method : getMethods){
				for(Field field : fields){
					String fieldName = field.getName();
					if(fieldName.equalsIgnoreCase(method.getName().substring(3).toLowerCase())){
						try {
							jsonObject.put(fieldName, method.invoke(obj, null));
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	/**
	 * 线性表转化JSON数组
	 * @param list
	 * @return
	 */
	public static JSONArray listToJson(List<Object> list){
		return arrayToJson(list.toArray());
	}
	
	/**
	 * 对象转JSON字符串
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj){
		return JSON.toJSONString(obj);
	}
	
	private static List<Field> getField(Class<? extends Object> clazz){
		return Arrays.asList(clazz.getDeclaredFields());
	}
	
	/**
	 * 获取所有的get方法
	 * @param clazz
	 * @return
	 */
	private static List<Method> getGetMethod(Class<? extends Object> clazz){
		// 或者所有的方法
		Method[] methods = clazz.getMethods();
		
		List<Method> list = new ArrayList<Method>();
		
		for(Method method : methods){
			String methodName = method.getName(); // 方法名
			if(methodName.startsWith(GET) && !methodName.equals(GET_CLASS)){ // 如果是get方法
				list.add(method);
			}
		}
		return list;
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
	
	public static void main(String[] args) {
		User user1 = new User();
		user1.setUserId("10086");
		user1.setName("张三");
		user1.setAge(23);
		user1.setSalary(12000.0);
		
		User user2 = new User();
		user2.setUserId("10000");
		user2.setName("李四");
		user2.setAge(33);
		user2.setSalary(1300.0);
		
		User[] users = new User[2];
		users[0] = user1;
		users[1] = user2;
		
		JSONArray arrayToJson = arrayToJson(users);
		System.out.println(arrayToJson);
	}
	
}
