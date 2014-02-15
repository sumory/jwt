package com.sumory.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public interface RowWrapper<T> {

    public T phase(ResultSet rs, HashSet<String> labels) throws SQLException;

}
