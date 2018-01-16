package com.simple.rtti;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象比较
 * @author 于继伟
 *
 */
public class EqualsUtil {
	
	private static final String GET = "get";
	
	/**
	 * 比较两个对象是否是同一个对象
	 * @param src
	 * @param dest
	 * @return
	 */
	public static final boolean equalsBean(Object src, Object dest){
		// 获取src对象和dest对象的Class对象
		Class<? extends Object> srcClazz = src.getClass();
		Class<? extends Object> destClazz = dest.getClass();
		
		// 获取方法
		List<Method> srcMethods = getGetMethod(srcClazz);
		List<Method> destMethods = getGetMethod(destClazz);
		
		// 比较各个属性值是否相同
		boolean flag1 = fieldEquals(srcMethods, destMethods, src, dest);
		
		// 比较每个属性的hash值是否相同
		boolean flag2 = hashEquals(srcClazz, destClazz);
		
		return flag1 && flag2;
	}
	
	/**
	 * 获取所有的get方法
	 * @param clazz
	 * @return
	 */
	private static final List<Method> getGetMethod(Class<? extends Object> clazz){
		// 或者所有的方法
		Method[] methods = clazz.getMethods();
		
		List<Method> list = new ArrayList<Method>();
		
		for(Method method : methods){
			String methodName = method.getName(); // 方法名
			if(methodName.startsWith(GET) && !methodName.equals("getClass")){ // 如果是get方法
				list.add(method);
			}
		}
		return list;
	}
	
	/**
	 * 计算对象的各个属性的hash值
	 * @param clazz
	 * @return
	 */
	private static final String hashCode(Class<? extends Object> clazz){
		StringBuffer sb = new StringBuffer();
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field : fields){
			int hash = field.hashCode();
			sb.append(String.valueOf(hash));
		}
		return sb.toString();
	}
	
	/**
	 * 比较hash值是否相同
	 * @param srcClazz
	 * @param destClazz
	 * @return
	 */
	private static final boolean hashEquals(Class<? extends Object> srcClazz,
			Class<? extends Object> destClazz) {
		String srcHash = hashCode(srcClazz);
		String destHash = hashCode(destClazz);
		return srcHash.equals(destHash);
	}
	
	/**
	 * 比较每个属性值是否相同
	 * @param srcMethods
	 * @param destMethods
	 * @return
	 */
	private static final boolean fieldEquals(List<Method> srcMethods, List<Method> destMethods, Object src, Object dest){
		boolean flag = true;
		for(int i = 0;i < srcMethods.size();i++){
			if(flag == false){
				return false;
			}
			Method srcMethod = srcMethods.get(i);
			for(int j = 0;j < destMethods.size();j++){
				Method destMethod = destMethods.get(j);
				if(destMethod.getName().equals(srcMethod.getName())){ // 如果是同一个方法
					try {
						Object destResult = destMethod.invoke(dest, null);
						Object srcResult = srcMethod.invoke(src, null);
						flag = destResult.equals(srcResult);
						break;
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
		return flag;
	}
	
}
