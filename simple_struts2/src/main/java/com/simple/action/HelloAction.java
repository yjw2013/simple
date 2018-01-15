package com.simple.action;

public class HelloAction {
	
private int age;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String execute(){
		System.out.println("hello world!"+name+"==>"+age);
		
		return "success";
	}
	
}
