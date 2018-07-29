package com.ray.utils;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * 佛祖保佑，此代码无bug，
 * 就算有，也一眼看出。
 *
 * 描述:
 * 数据库工具类
 *
 * @author ray
 * @version 1.0
 * @date 2018-07-14 上午1:11
 **/
public class DbUtils {

	private static Connection connection = null;

	public static Connection getConnection(){
		if (connection == null){
			Map<String, String> config = null;
			try {
				config = DbConfig.getConfig("db.properties");
				Class.forName(config.get("jdbc.driverClass"));
				String url = config.get("jdbc.url");
				String user = config.get("jdbc.user");
				String pwd = config.get("jdbc.password");
				connection = DriverManager.getConnection(url, user, pwd);
				return connection;
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		return connection;
	}
}
