package com.sumory.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.Assert;

public class LinkedListExtractor<T> implements ResultSetExtractor<LinkedList<T>>{
	
	private RowWrapper<T> rowWrapper;
	
	public LinkedListExtractor(RowWrapper<T> rowWrapper){
		Assert.notNull(rowWrapper, "RowWrapper is required");
		this.rowWrapper = rowWrapper;
	}
	

	@Override
	public LinkedList<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
		LinkedList<T> results = new LinkedList<T>();
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
