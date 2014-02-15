package com.sumory.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.Assert;

public class HashSetExtractor<T> implements ResultSetExtractor<HashSet<T>>{
	
	private RowWrapper<T> rowWrapper;
	
	public HashSetExtractor(RowWrapper<T> rowWrapper){
		Assert.notNull(rowWrapper, "RowWrapper is required");
		this.rowWrapper = rowWrapper;
	}
	

	@Override
	public HashSet<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
		HashSet<T> results = new HashSet<T>();
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
