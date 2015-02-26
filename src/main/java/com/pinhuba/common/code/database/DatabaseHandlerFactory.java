package com.pinhuba.common.code.database;

public class DatabaseHandlerFactory {
	public static final String MYSQL = "mysql";
	public static final String ORACLE = "oracle";
	
	public static DatabaseHandler getHandler(String url) {
		if(url.indexOf(MYSQL) != -1){
			return new MysqlHandler();
		}else if(url.indexOf(ORACLE) != -1){
			return new OracleHandler();
		}
		return null;
	}
	
}
