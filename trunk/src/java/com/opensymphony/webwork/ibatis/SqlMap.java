package com.opensymphony.webwork.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * User: patrick
 * Date: Aug 31, 2005
 * Time: 1:46:00 PM
 */
public interface SqlMap extends SqlMapExecutor {
    SqlMapClient getClient();
}
