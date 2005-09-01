package com.opensymphony.webwork.ibatis;

import com.ibatis.common.util.PaginatedList;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.opensymphony.util.ClassLoaderUtil;
import com.opensymphony.xwork.interceptor.component.Disposable;
import com.opensymphony.xwork.interceptor.component.Initializable;
import com.opensymphony.xwork.interceptor.component.ResourceAware;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: patrick
 * Date: Aug 31, 2005
 * Time: 1:46:02 PM
 */
public class SqlMapImpl implements SqlMap, ResourceAware, Initializable, Disposable {
    protected SqlMapClient client;

    public void init() {
        InputStream is = ClassLoaderUtil.getResourceAsStream("sql-map-config.xml", getClass());
        InputStreamReader isr = new InputStreamReader(is);
        client = SqlMapClientBuilder.buildSqlMapClient(isr);
    }

    public Set getDependentResources() {
        HashSet set = new HashSet();
        set.add("sql-map-config.xml");
        set.add("mappings/User.xml");

        return set;
    }

    public SqlMapClient getClient() {
        return client;
    }

    public void dispose() {
    }

    public void startBatch() throws SQLException {
        client.startBatch();
    }

    public int executeBatch() throws SQLException {
        return client.executeBatch();
    }

    public Map queryForMap(String id, Object parameterObject, String keyProp, String valueProp) throws SQLException {
        return client.queryForMap(id, parameterObject, keyProp);
    }

    public Object insert(String id, Object parameterObject) throws SQLException {
        return client.insert(id, parameterObject);
    }

    public int update(String id, Object parameterObject) throws SQLException {
        return client.update(id, parameterObject);
    }

    public int delete(String id, Object parameterObject) throws SQLException {
        return client.delete(id, parameterObject);
    }

    public Object queryForObject(String id, Object parameterObject) throws SQLException {
        return client.queryForObject(id, parameterObject);
    }

    public Object queryForObject(String id, Object parameterObject, Object resultObject) throws SQLException {
        return client.queryForObject(id, parameterObject, resultObject);
    }

    public List queryForList(String id, Object parameterObject) throws SQLException {
        return client.queryForList(id, parameterObject);
    }

    public List queryForList(String id, Object parameterObject, int skip, int max) throws SQLException {
        return client.queryForList(id, parameterObject, skip, max);
    }

    public void queryWithRowHandler(String id, Object parameterObject, RowHandler rowHandler) throws SQLException {
        client.queryWithRowHandler(id, parameterObject, rowHandler);
    }

    public PaginatedList queryForPaginatedList(String id, Object parameterObject, int pageSize) throws SQLException {
        return client.queryForPaginatedList(id, parameterObject, pageSize);
    }

    public Map queryForMap(String id, Object parameterObject, String keyProp) throws SQLException {
        return client.queryForMap(id, parameterObject, keyProp);
    }
}
