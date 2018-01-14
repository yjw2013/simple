package com.spring.main;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.spring.config.Bean;
import com.spring.config.Property;
import com.spring.config.parse.ConfigManager;
import com.spring.exception.BeansException;
import com.spring.utils.BeanUtils;

/**
 * 类路径加载配置文件
 * @author 于继伟
 *
 */
public class ClassPathXmlApplicationContext implements BeanFactory{
	
	/**
	 * 希望在ClassPathXmlApplicationContext类一创建
	 * 就初始化Spring容器(装载Bean实例的)
	 */
	
	// 配置信息
	private Map<String, Bean> config;
	// 使用一个Map来做Spring的容器,放置我们Spring所管理的对象
	private Map<String, Object> context = new HashMap<String, Object>();
	
	public ClassPathXmlApplicationContext(String path){
		// 1.读取配置文件获取需要初始化的Bean信息
		config = ConfigManager.getConfig(path);
		// 2.遍历配置 初始化Bean
		if(null != config){
			for(Entry<String, Bean> en : config.entrySet()){
				// 获取配置中的Bean信息
				String beanName = en.getKey();
				Bean bean = en.getValue();
				
				Object existBean = context.get(beanName);
				/**
				 * 因为createBean方法中也会向Context中放置Bean
				 * 我们在初始化之前先要判断容器中是否已经存在了这个Bean.再去完成初始化的工作
				 * 并且我们的Bean的scope属性值为singleton,才将Bean放入容器中
				 */
				if(existBean == null && bean.getScope().equals("singleton")){
					// 根据bean配置 创建bean对象
					Object beanObj = createBean(bean);
					// 3.将初始化好的bean放入容器中
					context.put(beanName, beanObj);
				}
			}
		}
	}
	
	/**
	 * 根据Bean配置创建Bean实例
	 * 
	 * 	<bean name="A" class="cn.itcast.bean.A"  >
			<!-- 将A的属性配置,spring会自动将配置的值注入到A中 -->
			<property name="name" value="tom" ></property>
		</bean>
	 * 
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Object createBean(Bean bean) {
		// 1.获得需要创建Bean的Class
		String className = bean.getClassName();
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new BeansException("请开发人员注意，检查Bean的Class属性是否配置正确..." + className);
		}
		// 获得class => 将class对应的对象创建出来
		Object beanObj = null;
		try {
			beanObj = clazz.newInstance(); // 调用空参构造
		} catch (Exception e) {
			e.printStackTrace();
			throw new BeansException("请开发人员注意,请检查Bean是否有无参数的构造方法..." + className);
		}
		
		// 2.需要获得Bean的属性,将其注入
		if(bean.getProperties() != null){
			for(Property prop : bean.getProperties()){
				/**
				 * 注入分两种情况
				 * 获得需要注入的属性名称
				 */
				String name = prop.getName();
				String value = prop.getValue();
//				String ref = prop.getRef();
				// 注入值类型属性方式2:使用BeanUtils工具类完成属性的注入
				if(value != null){ // 说明有值类型的属性需要注入
					Map<String,String[]> paramMap = new HashMap<String, String[]>();
					paramMap.put(name, new String[]{value});
					// 调用BeanUtils方法将值类型的属性注入(该种注入=>可以自动完成类型转换)
					try {
						org.apache.commons.beanutils.BeanUtils.populate(beanObj,paramMap);
					} catch (Exception e) {
						e.printStackTrace();
						throw new BeansException("请开发人员注意,请检查" + name + "的属性...");
					}
				}
				
				// 用于注入Bean类型的属性
				if(prop.getRef() != null){
					// 2.麻烦 = >其他Bean的注入
					// 根据属性名称获得注入属性对应的Set方法
					Method setMethod = BeanUtils.getWriteMethod(beanObj,name);
					// 因为要注入其他bean到当前bean中,我们先从容器中查找当前要注入的Bean是否已经创建,并放入容器中
					Object existBean =  context.get(prop.getRef());	
					
					if(existBean == null ){
						//说明容器中还不存在我们要注入的Bean
						//将Bean创建
						existBean = createBean(config.get(prop.getRef()));
						//将创建好的Bean放入容器中
						if(config.get(prop.getRef()).getScope().equals("singleton")){
							context.put(prop.getRef(), existBean);
						}
					}
					
					// 调用set方法注入即可
					try {
						setMethod.invoke(beanObj, existBean);
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException("开发人员请注意,Bean的属性"+name+"没有对应的set方法,或方法参数不正确"+className);
					}
				}
			}
		}
		
		return beanObj;
	}

	@Override
	public Object getBean(String beanName) {
		Object bean = context.get(beanName);
		
		//如果bean的scope配置为prototype .那么 context中就不会包含该Bean对象
		if(bean==null){
			//如果不存在该Bean对象,那么就创建这个Bean对象
			bean = createBean(config.get(beanName));
		}
		
		return bean;
	}
	
}
