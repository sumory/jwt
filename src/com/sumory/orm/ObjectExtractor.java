package com.sumory.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.Assert;

public class ObjectExtractor<T> implements ResultSetExtractor<T>{
	
	private RowWrapper<T> rowWrapper;
	
	public ObjectExtractor(RowWrapper<T> rowWrapper){
		Assert.notNull(rowWrapper, "RowWrapper is required");
		this.rowWrapper = rowWrapper;
	}
	

	@Override
	public T extractData(ResultSet rs) throws SQLException, DataAccessException {
        return rs.next() ? rowWrapper.phase(rs, OrmUtil.getColumLables(rs)):null;
	}

}
