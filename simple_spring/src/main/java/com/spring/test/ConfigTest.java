package com.spring.test;

import java.util.Map;

import com.spring.config.Bean;
import com.spring.config.parse.ConfigManager;

public class ConfigTest {
	
	public static void main(String[] args) {
		Map<String, Bean> config = ConfigManager.getConfig("/applicationContext.xml");
		
		System.out.println(config);
	}
	
}
