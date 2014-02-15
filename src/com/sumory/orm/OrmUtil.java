package com.sumory.orm;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;

import org.springframework.jdbc.support.JdbcUtils;

public class OrmUtil {
	
	public static HashSet<String> getColumLables(ResultSet rs) throws SQLException{
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		HashSet<String> lables = new HashSet<String>(count, 1);
		for(int n = 1; n<= count; n++){
			String name = JdbcUtils.lookupColumnName(rsmd, n);
			lables.add(name);
		}
		return lables;
	} 
}
