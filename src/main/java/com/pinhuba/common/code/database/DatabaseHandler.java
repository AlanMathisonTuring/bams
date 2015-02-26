package com.pinhuba.common.code.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseHandler {
	String getName();
	Connection getConn(String url, String userName, String userPwd) throws SQLException, ClassNotFoundException;
	List<String> getRemarks(Connection conn, String tableName) throws Exception;
	String columnTypeToFieldType(int columnType, int columnSize);
	String columnTypeToFieldType2(int columnType, int columnSize);
}
