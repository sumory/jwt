package com.sumory.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.Assert;

public class HashMapExtractor<K,V> implements ResultSetExtractor<HashMap<K,V>>{
	
	private String keyColumn;
	
	private RowWrapper<V> rowWrapper;
	
	
	public HashMapExtractor(String keyColumn,RowWrapper<V> rowWrapper){
		Assert.notNull(keyColumn,"key column is required");
		Assert.notNull(rowWrapper, "RowWrapper is required");
		this.keyColumn = keyColumn;
		this.rowWrapper = rowWrapper;
		
	}
	

	@Override
	public HashMap<K,V> extractData(ResultSet rs) throws SQLException, DataAccessException {
		HashMap<K,V> results = new HashMap<K,V>();
		HashSet<String> labels = OrmUtil.getColumLables(rs);
        while (rs.next()){
            try {
				@SuppressWarnings("unchecked")
				K key = (K)rs.getObject(keyColumn);
				results.put(key, rowWrapper.phase(rs,labels));
			} catch (Exception e) {
				throw new SQLException(e);
			}
        }
        return results;
	}

}
