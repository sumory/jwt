package com.sumory.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sumory.util.PropertiesHelper;

public class Constant {
	
	private static Logger logger = LoggerFactory.getLogger(Constant.class);
	
	public static PropertiesHelper helper;
	
	static{
		
		try{
			helper = new PropertiesHelper("system.properties");
		}catch (Exception e) {
			logger.error(" system properties load error " ,e );
		}
		
	}
	

	public static String DB_TYPE = helper.getString("db_type", "Oracle");

}
