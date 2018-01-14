package com.spring.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring环境中的代表Bean的实体类
 * @author 于继伟
 *
 */
public class Bean {
	
	private String name;
	private String className;
	private String scope = "singleton";
	
	private List<Property> properties = new ArrayList<Property>();

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "Bean [name=" + name + ", className=" + className + ", scope="
				+ scope + ", properties=" + properties + "]";
	}
	
}
