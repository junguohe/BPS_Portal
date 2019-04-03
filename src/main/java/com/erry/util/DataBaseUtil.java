package com.erry.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.erry.common.GlobalConstant;

public class DataBaseUtil {
	
	public static Connection getconn() {
		String driver = GlobalConstant.getSystemConfig("sql.driver");
	    String url = GlobalConstant.getSystemConfig("sql.url");
	    String user = GlobalConstant.getSystemConfig("sql.user");
	    String password = GlobalConstant.getSystemConfig("sql.password");
	    Connection conn = null;
	    try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    return conn;
	}
    
    public void callProcedure(String procedure, Connection conn) throws SQLException {
		CallableStatement call = conn.prepareCall("{call " + procedure + "}");
		call.execute();
		call.close();
	}
    
    /**
	 * 调用存储过程执行指定作业
	 * 
	 * @param conn
	 * @param jobName 
	 * @return
	 * @throws Exception
	 */
	public int startJob(Connection conn, String jobName) throws Exception {
		CallableStatement cstmt = conn.prepareCall("{call  runJob(?,?)}");
		cstmt.setString(1, jobName);
		cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
		cstmt.execute();
		int status = cstmt.getInt(2);
		cstmt.close();
		return status;
	}
      
    
    public void close(Connection conn) {
    	if (conn != null) {
    		try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
}
