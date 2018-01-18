package com.cache.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cache.bean.User;
import com.cache.service.UserService;

@Controller
public class CacheController {
	
	@Autowired
	private UserService UserSerivce;
	
	@RequestMapping("cache.do")
	public void cache(HttpServletRequest request, HttpServletResponse response){
		System.out.println("三级缓存开始...");
		User user = UserSerivce.selectUser(1);
		System.out.println(user);
	}
	
}	


