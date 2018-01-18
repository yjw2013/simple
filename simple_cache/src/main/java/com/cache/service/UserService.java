package com.cache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cache.bean.User;
import com.cache.bean.UserExample;
import com.cache.mapper.UserMapper;
import com.cache.redis.RedisService;
import com.cache.util.FastJsonUtil;

@Service
public class UserService {
	
	private static final String USER_KEY = "USER";
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private UserMapper userMapper;
	
	public User selectUser(int userId){
		String jsonData = redisService.get(USER_KEY + userId);
		if("".equals(jsonData) || null == jsonData){
			User user = userMapper.selectByPrimaryKey(userId);
			redisService.set(USER_KEY + userId, JSON.toJSONString(user));
			return user;
		}
		return (User) FastJsonUtil.jsonToObject(jsonData, User.class);
	}
	
	public boolean insert(User user){
		return userMapper.insert(user) == 1;
	}
	
}
