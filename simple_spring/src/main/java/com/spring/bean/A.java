package com.spring.bean;

public class A {
	
	public A() {
		System.out.println("A对象被创建了!");
	}

	private int name;
	
	public int getName() {
		return name;
	}
	
	public void setName(int name) {
		this.name = name;
	}
	
}
