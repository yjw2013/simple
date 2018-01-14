package com.spring.config.parse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.spring.config.Bean;
import com.spring.config.Property;

/**
 * 解析配置文件
 * @author 于继伟
 *
 */
public class ConfigManager {
		
	// 读取 配置文件 并返回读取结果
	@SuppressWarnings("unchecked")
	public static Map<String, Bean> getConfig(String path){
		// 创建用于一个返回Map的对象
		Map<String, Bean> map = new HashMap<String, Bean>();
		// dom4j实现
		// 1.创建解析器
		SAXReader reader = new SAXReader();
		// 2.加载配置文件=>document对象
		InputStream is = ConfigManager.class.getResourceAsStream(path);
		Document doc = null;
		try {
			doc = reader.read(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 3.定义xpath表达式，取出所有Bean元素
		String xpath = "//bean";
		// 4.对Bean元素进行遍历
		List<Element> list = doc.selectNodes(xpath);
		if(list.size() > 0 && null != list){ // 非空校验
			for(Element beanEle : list){
				Bean bean = new Bean();
				// 将bean元素的name/class 属性封装到Bean对象中
				String name = beanEle.attributeValue("name");
				String className = beanEle.attributeValue("class");
				String scope = beanEle.attributeValue("scope");
				
				bean.setName(name);
				bean.setClassName(className);
				if(scope != null){
					bean.setScope(scope);
				}
				
				// 获得Bean元素下的所有Property子元素,将属性name/value/ref封装到Property对象中
				List<Element> children = beanEle.elements("property");
				
				if(children != null){
					for(Element child : children){
						Property prop = new Property();
						
						String pName = child.attributeValue("name");
						String pValue = child.attributeValue("value");
						String  pRef = child.attributeValue("ref");
						
						prop.setName(pName);
						prop.setRef(pRef);
						prop.setValue(pValue);
						
						// 将Property封装为Bean对象
						bean.getProperties().add(prop);
					}
				}
				map.put(name, bean);
			}
		}
		return map;
	}
	
}
