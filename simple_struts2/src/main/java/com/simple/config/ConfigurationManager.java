package com.simple.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 读取struts.xml文件
 * @author 于继伟
 *
 */
public class ConfigurationManager {
	
	@SuppressWarnings("unchecked")
	public static Map<String, ActionConfig> getActionConfig() {
		Map<String, ActionConfig> actionMap;
		Document doc = getDocument();
		
		String xpath = "//action";
		List<Element> list = doc.selectNodes(xpath);
		
		if(list == null || list.size() == 0){
			return null;
		}
		actionMap = new HashMap<String, ActionConfig>();
		
		for(Element e : list){
			ActionConfig action = new ActionConfig();
			action.setName(e.attributeValue("name"));
			action.setClassName(e.attributeValue("class"));
			String method = e.attributeValue("method");
			action.setMethod(method == null || "".equals(method.trim()) ? "execute" : method);
			
			List<Element> results = e.elements("result");
			for(Element result : results){
				action.getResult().put(result.attributeValue("name"), result.getText());
			}
			actionMap.put(action.getName(), action);
		}
		
		return actionMap;
	}
	
	private static Document getDocument(){
		SAXReader reader = new SAXReader();
		
		InputStream is = ConfigurationManager.class.getResourceAsStream("/struts.xml");
		
		Document doc;
		try {
			doc = reader.read(is);
			return doc;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getConstant(String key) {
		Document doc = getDocument();
		
		String path = "//constant[@name='"+key+"']";
		
		Element constant = (Element) doc.selectSingleNode(path);
		
		if(constant!=null){
			return constant.attributeValue("value");
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getInterceptors() {
		Document doc = getDocument();
		
		String path = "//interceptor";
		
		List<Element> list = doc.selectNodes(path);
		
		if(list==null || list.size()==0){
			return null;
		}
		
		List<String> interceptors = new ArrayList<String>();
		
		for(Element ele : list){
			interceptors.add(ele.attributeValue("class"));
		}
		return interceptors;
	}
	
}
