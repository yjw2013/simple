package com.simple.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.simple.bean.User;
import com.simple.rtti.MapToObjectRtti;

public class MapToObjectRttiTest {
	
	@Test
	public void test(){
		
		User user = new User();
//		user.setUserId("10086");
//		user.setName("张三");
//		user.setAge(22);
//		user.setSalary(12000.0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", "10086");
		map.put("name", "张三");
		map.put("age", 22);
		map.put("salary", 12000.0);
		
		System.out.println(user);
		MapToObjectRtti.map2Obj(map, user);
		System.out.println(user);
	}
	
}
