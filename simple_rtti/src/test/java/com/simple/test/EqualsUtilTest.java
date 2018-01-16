package com.simple.test;

import org.junit.Test;

import com.simple.bean.User;
import com.simple.rtti.EqualsUtil;

public class EqualsUtilTest {
	
	@Test
	public void test(){
		
		User user1 = new User();
		user1.setUserId("10086");
		user1.setName("张三");
		user1.setAge(22);
		user1.setSalary(12000.0);
		
		User user2 = new User();
		user2.setUserId("10086");
		user2.setName("张三");
		user2.setAge(22);
		user2.setSalary(12000.0);
		
		boolean flag = EqualsUtil.equalsBean(user1, user2);
		System.out.println(flag == true ? "YES" : "NO");
	}
	
}
