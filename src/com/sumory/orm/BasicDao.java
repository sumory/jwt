package com.sumory.orm;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.sumory.constant.Constant;
import com.sumory.orm.annotation.Ignore;
import com.sumory.orm.annotation.Table;

/**
 * 
 * 
 *
 */
public class BasicDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ============================查询接口===========================================
    protected int queryForInt(final String sql, final Object... args) {
        return jdbcTemplate.queryForObject(sql, args, Integer.class);
    }

    protected Long queryForLong(final String sql, final Object... args) {
        return jdbcTemplate.queryForObject(sql, args, Long.class);
    }

    protected Map<String, Object> queryForMap(final String sql, final Object... args) {
        return jdbcTemplate.queryForMap(sql, args);
    }

    protected String queryForString(final String sql, final Object... args) {
        return jdbcTemplate.queryForObject(sql, String.class, args);
    }

    protected List<Map<String, Object>> queryForList(final String sql, final Object... args) {
        return jdbcTemplate.queryForList(sql, args);
    }

    protected <T> T queryForObject(final String sql, final RowWrapper<T> rowWrapper,
            final Object... args) {
        return jdbcTemplate.query(sql, new ObjectExtractor<T>(rowWrapper), args);
    }

    protected <T> ArrayList<T> queryForArrayList(final String sql, final RowWrapper<T> rowWrapper,
            final Object... args) {
        return jdbcTemplate.query(sql, new ArrayListExtractor<T>(rowWrapper), args);
    }

    protected <T> LinkedList<T> queryForLinkedList(final String sql,
            final RowWrapper<T> rowWrapper, final Object... args) {
        return jdbcTemplate.query(sql, new LinkedListExtractor<T>(rowWrapper), args);
    }

    protected <T> HashSet<T> queryForHashSet(final String sql, final RowWrapper<T> rowWrapper,
            final Object... args) {
        return jdbcTemplate.query(sql, new HashSetExtractor<T>(rowWrapper), args);
    }

    protected <T> TreeSet<T> queryForTreeSet(final String sql, final RowWrapper<T> rowWrapper,
            final Object... args) {
        return jdbcTemplate.query(sql, new TreeSetExtractor<T>(rowWrapper), args);
    }

    protected <K, V> HashMap<K, V> queryForHashMap(final String sql,
            final RowWrapper<V> rowWrapper, String keyColumn, final Object... args) {
        return jdbcTemplate.query(sql, new HashMapExtractor<K, V>(keyColumn, rowWrapper), args);
    }

    /**
     * 映射为page对象
     * 
     * @param sql
     * @param rowWrapper
     * @param pageNo
     * @param pageSize
     * @param args
     * @return
     */
    protected <T> Page<T> queryForPage(String sql, final RowWrapper<T> rowWrapper, int pageNo,
            int pageSize, final Object... args) {
        Assert.state(sql.startsWith("select"), "error query sql");
        Page<T> page = new Page<T>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);

        // list
        List<T> list = queryForArrayList(getPageSQL(sql, (pageNo - 1) * pageSize, pageSize),
                rowWrapper, args);
        page.setList(list);

        // total
        sql = sql.replaceFirst("select(.*?)from", "select count(1) from");
        int total = queryForInt(sql, args);
        page.setTotal(total);

        return page;
    }

    // ============================修改接口===========================================

    protected int update(final String sql, final Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    protected int update(final KeyHolder keyHolder, final String sql, final Object... args) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new int[] { 1 });
                int i = 0;
                for (Object arg : args) {
                    i++;
                    ps.setObject(i, arg);
                }
                return ps;
            }
        };
        return jdbcTemplate.update(psc, keyHolder);
    }

    protected int[] batchUpdate(String[] sql) {
        return jdbcTemplate.batchUpdate(sql);
    }

    /**
     * 
     * 
     * @date 2012-12-13 上午11:39:11
     * @param sql
     * @param args Object[]为一条sql的全部参数
     * @return
     */
    protected int[] batchUpdate(final String sql, final List<Object[]> args) {
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Object[] os = args.get(i);
                for (int n = 0; n < os.length; n++) {
                    ps.setObject(n + 1, os[n]);
                }
            }

            @Override
            public int getBatchSize() {
                return args.size();
            }
        });
    }

    protected int[] batchUpdate(final String sql, BatchPreparedStatementSetter pss) {
        return jdbcTemplate.batchUpdate(sql, pss);
    }

    // ===============================================================================================
    /**
     * 生成参数的占位符
     * 
     * @param n 占位符的个数
     * @return
     */
    protected String genPlaceHodler(int n) {
        StringBuilder sb = new StringBuilder(2 * n);
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                sb.append("?");
            }
            else {
                sb.append(",?");
            }
        }
        return sb.toString();
    }

    // ==============================================生成分页sql===========================================
    /**
     * 数据分页查询.
     * 
     * @param queryString
     * @param startIndex
     * @param pageSize
     * @return
     */
    public String getPageSQL(String queryString, Integer startIndex, Integer pageSize) {
        return getPageSQL(queryString, Constant.DB_TYPE, startIndex, pageSize);
    }

    /**
     * 数据分页查询
     * 
     * @param queryString :SQL
     * @param DB_TYPE :数据库类型
     * @param startIndex ,起始索引
     * @param pageSize ,分页大小
     * @return
     */
    private String getPageSQL(String queryString, String DB_TYPE, Integer startIndex,
            Integer pageSize) {
        String pageSQL = "";
        if (DB_TYPE.equals("MySQL")) {
            pageSQL = getMySQLPageSQL(queryString, startIndex, pageSize);
        }
        else if (DB_TYPE.equals("Oracle")) {
            pageSQL = getOraclePageSQL(queryString, startIndex, pageSize);
        }
        return pageSQL;
    }

    /**
     * 构造MySQL数据分页SQL
     * 
     * @param queryString
     * @param startIndex
     * @param pageSize
     * @return
     */
    private String getMySQLPageSQL(String queryString, Integer startIndex, Integer pageSize) {
        String result = "";
        if (null != startIndex && null != pageSize) {
            result = queryString + " limit " + startIndex + "," + pageSize;
        }
        else if (null != startIndex && null == pageSize) {
            result = queryString + " limit " + startIndex;
        }
        else {
            result = queryString;
        }
        return result;
    }

    /**
     * 构造 Oracle数据分页SQL
     * 
     * @param queryString
     * @param startIndex
     * @param pageSize
     * @return
     */
    private String getOraclePageSQL(String queryString, Integer startIndex, Integer pageSize) {
        if (StringUtils.isEmpty(queryString)) {
            return null;
        }
        int endIndex = startIndex + pageSize;
        String endSql = "select * from (select rOraclePageSQL.*,ROWNUM as currentRow from ("
                + queryString + ") rOraclePageSQL where rownum <=" + endIndex
                + ") where currentRow>" + startIndex;
        return endSql;
    }

    // =================================自定义注解============================================================

    /**
     * 删除对象
     * 
     * @param clazz
     * @param id
     * @return
     */
    public int delete(Class<?> clazz, Object id) {
        String table = getTable(clazz);
        String sql = "delete from " + table + " where id = ?";
        logger.debug(sql);
        return update(sql, id);
    }

    /**
     * 保存对象
     * 
     * @param holder
     * @param o
     * @return
     */
    public int save(KeyHolder holder, Object o) {
        Class<?> clazz = o.getClass();
        String table = getTable(clazz);
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
        StringBuilder sql = new StringBuilder(" insert into ");
        sql.append(table).append(" ( ");

        List<Object> params = new ArrayList<Object>();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().toLowerCase().equals("id")
                    || pd.getName().toLowerCase().equals("class")) {
                continue;
            }

            if (isIgnoreField(pd, clazz)) {
                continue;
            }

            if (pd != null && pd.getReadMethod() != null) {
                Object value = readProperties(pd, o);
                if (params.size() != 0) {
                    sql.append(",");
                }
                sql.append(underscoreName(pd.getName()));
                params.add(value);

            }
        }
        sql.append(" ) values ").append(" ( ").append(genPlaceHodler(params.size())).append(" ) ");

        logger.debug(sql.toString());

        return holder == null ? update(sql.toString(), params.toArray()) : update(holder,
                sql.toString(), params.toArray());
    }

    /**
     * 保存对象
     * 
     * @param o
     * @return
     */
    public int save(Object o) {
        return save(null, o);
    }

    /**
     * 更新对象
     * 
     * @param o
     * @return
     */
    public int update(Object o) {
        Class<?> clazz = o.getClass();
        String table = getTable(clazz);
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
        StringBuilder sql = new StringBuilder(" update ");
        sql.append(table).append(" set ");

        List<Object> params = new ArrayList<Object>();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().toLowerCase().equals("id")
                    || pd.getName().toLowerCase().equals("class")) {
                continue;
            }

            if (isIgnoreField(pd, clazz)) {
                continue;
            }

            if (pd != null && pd.getReadMethod() != null) {
                Object value = readProperties(pd, o);
                if (params.size() != 0) {
                    sql.append(",");
                }
                sql.append(underscoreName(pd.getName())).append(" = ? ");
                params.add(value);

            }
        }

        sql.append(" where id = ? ");
        PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, "id");
        Object value = readProperties(pd, o);
        params.add(value);

        logger.debug(sql.toString());
        return update(sql.toString(), params.toArray());
    }

    /**
     * 根据标签读取表名
     * 
     * @param clazz
     * @return
     */
    private String getTable(Class<?> clazz) {
        Assert.isTrue(clazz.isAnnotationPresent(Table.class), " miss table annotation ");
        Table table = (Table) clazz.getAnnotation(Table.class);
        if (StringUtils.isEmpty(table.name())) {
            throw new RuntimeException(" table annotation should be have [ name ] param");
        }
        return table.name();
    }

    private String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            result.append(name.substring(0, 1).toLowerCase());
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                if (s.equals(s.toUpperCase())) {
                    result.append("_");
                    result.append(s.toLowerCase());
                }
                else {
                    result.append(s);
                }
            }
        }
        return result.toString();
    }

    /**
     * 读取属性值
     * 
     * @param pd
     * @param o
     * @return
     */
    private Object readProperties(PropertyDescriptor pd, Object o) {
        try {
            Method readMethod = pd.getReadMethod();
            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                readMethod.setAccessible(true);
            }
            Object value = readMethod.invoke(o);
            return value;
        }
        catch (Throwable e) {
            throw new FatalBeanException("Could not read properties from obj", e);
        }
    }

    /**
     * 是否是数据库无关字段
     * 
     * @param pd
     * @param clazz
     * @return
     */
    private boolean isIgnoreField(PropertyDescriptor pd, Class<?> clazz) {
        try {
            Field field = clazz.getDeclaredField(pd.getName());
            return field.isAnnotationPresent(Ignore.class);
        }
        catch (Throwable e) {
            throw new FatalBeanException(" bad field ", e);
        }
    }

}
