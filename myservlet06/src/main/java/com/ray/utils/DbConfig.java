package com.ray.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 佛祖保佑，此代码无bug，
 * 就算有，也一眼看出。
 *
 * @author ray
 * @version 1.0
 * @date 2018-07-14 上午1:15
 **/
public class DbConfig {

	private static Map<String, String> config = null;

	public static Map<String, String> getConfig(String path){
		if (config == null){
			config = new HashMap<>(16);
			try {
				InputStream fis = DbConfig.class.getClassLoader().getResourceAsStream(path);
				Properties prop = new Properties();
				prop.load(fis);
				Enumeration keys = prop.propertyNames();
				while (keys.hasMoreElements()){
					String key = (String) keys.nextElement();
					config.put(key, prop.getProperty(key));
				}
				return config;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return config;
	}
}
