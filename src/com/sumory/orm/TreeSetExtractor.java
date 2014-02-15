package com.sumory.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.TreeSet;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.Assert;

public class TreeSetExtractor<T> implements ResultSetExtractor<TreeSet<T>>{
	
	private RowWrapper<T> rowWrapper;
	
	public TreeSetExtractor(RowWrapper<T> rowWrapper){
		Assert.notNull(rowWrapper, "RowWrapper is required");
		this.rowWrapper = rowWrapper;
	}
	

	@Override
	public TreeSet<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
		TreeSet<T> results = new TreeSet<T>();
		HashSet<String> labels = OrmUtil.getColumLables(rs);
        while (rs.next()){
            try {
				results.add(rowWrapper.phase(rs,labels));
			} catch (Exception e) {
				throw new SQLException(e);
			}
        }
        return results;
	}

}
