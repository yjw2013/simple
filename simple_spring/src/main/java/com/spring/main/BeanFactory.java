package com.spring.main;

/**
 * Bean工厂
 * @author 于继伟
 *
 */
public interface BeanFactory {
	
	// 根据Bean的name获得Bean对象的方法
	Object getBean(String beanName);
	
}
