package com.pinhuba.common.code.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleHandler implements DatabaseHandler {

	private final static Logger logger = LoggerFactory.getLogger(MysqlHandler.class);
	// oracle字段类型为NUMBER 并且长度为11的， 处理为java的Integer类型
	private static final int ORACLE_INTEGER_LENGTH = 11;
	private String schemaPattern = null;

	@Override
	public Connection getConn(String url, String userName, String userPwd) throws ClassNotFoundException, SQLException {
		Properties props = new Properties();
		props.put("user", userName);
		props.put("password", userPwd);
		props.put("remarksReporting", "true");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(url, props);
		logger.info("使用oracle数据库进行代码生成...");

		schemaPattern = userName.toUpperCase();// schemaPattern 大写用户名
		logger.info("设置schemaPattern:{}...", schemaPattern);

		return conn;
	}

	@Override
	public List<String> getRemarks(Connection conn, String tableName) throws Exception {
		List<String> list = new ArrayList<String>();
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet resultSet = null;

		tableName = tableName.toUpperCase();
		resultSet = dbmd.getTables(conn.getCatalog(), schemaPattern, null, new String[] { "TABLE" });

		while (resultSet.next()) {
			String t = resultSet.getString("TABLE_NAME");
			if (t.equals(tableName)) {
				ResultSet rs = dbmd.getColumns(conn.getCatalog(), schemaPattern, tableName, "%");
				while (rs.next()) {
					list.add(rs.getString("REMARKS"));
				}
			}
		}
		return list;
	}

	@Override
	public String columnTypeToFieldType(int columnType, int columnSize) {
		if (columnType == Types.NUMERIC) {

			if (columnSize == ORACLE_INTEGER_LENGTH)
				return "Integer";
			else
				return "Double";

		} else {
			return "String";
		}
	}

	@Override
	public String columnTypeToFieldType2(int columnType, int columnSize) {
		String fieldName = columnTypeToFieldType(columnType, columnSize);
		return "java.lang." + fieldName;
	}

	@Override
	public String getName() {
		return "oracle";
	}
}
