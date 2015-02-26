package com.pinhuba.common.code.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlHandler implements DatabaseHandler{

	private final static Logger logger = LoggerFactory.getLogger(MysqlHandler.class);
	
	@Override
	public Connection getConn(String url, String userName, String userPwd) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, userName, userPwd);
		logger.info("使用mysql数据库进行代码生成...");
		return conn;
	}

	@Override
	public List<String> getRemarks(Connection conn, String tableName) throws Exception {
		List<String> list = new ArrayList<String>();
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet resultSet = null;
		
		resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
		
		while (resultSet.next()) {
	        String t = resultSet.getString("TABLE_NAME");
	        if(t.equals(tableName)){
	        	ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
	            while(rs.next()){
	                list.add(rs.getString("REMARKS"));
	            }
	        }
	    }
		return list;
	}

	@Override
	public String columnTypeToFieldType(int columnType, int columnSize) {
		if (columnType == Types.INTEGER) {
			return "Integer";
		} else if (columnType == Types.DOUBLE || columnType == Types.FLOAT) {
			return "Double";
		} else {
			return "String";
		}
	}

	@Override
	public String columnTypeToFieldType2(int columnType, int columnSize) {
		String fieldName = columnTypeToFieldType(columnType, columnSize);
		return "java.lang."+fieldName;
	}

	@Override
	public String getName() {
		return "mysql";
	}

}
