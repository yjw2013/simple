package com.spring.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.spring.exception.BeansException;

/**
 * Bean操作工具类
 * @author 于继伟
 *
 */
public class BeanUtils {
	
	/**
	 * 获取Bean注入所需要的set方法
	 * @param beanObj bean对象
	 * @param name    要获得的Bean对象对应的属性名称
	 * @return
	 */
	public static Method getWriteMethod(Object beanObj, String name) {
		Method method = null;
		// 使用内省技术来实现该方法
		try {
			// 1.分析Bean对象  BeanInfo
			BeanInfo info = Introspector.getBeanInfo(beanObj.getClass());
			// 2.根据BeanInfo获取所有属性的描述器
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			// 3.遍历这些属性描述器
			if(pds != null){
				for(PropertyDescriptor pd : pds){
					// 判断当前遍历的描述器描述的属性是否是我们要找的属性
					// 获得当前描述器描述的属性名称
					String pName = pd.getName();
					// 使用要找的属性名称与当前描述器描述的属性名称比对
					if(pName.equals(name)){
						//比对一致=>找到了,获得写入属性的set方法
						method = pd.getWriteMethod();
					}
				}
			}
			// 4. 返回找到的set方法
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		
		//如果没有找到=>抛出异常提示用户 检查是否创建属性对应的set方法
		if(method==null){
			throw new BeansException("开发人员请注意,请检查"+name+"属性的set方法是否创建...");
		}
		
		return method;
	}
	
}
