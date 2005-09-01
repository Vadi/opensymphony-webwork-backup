package com.opensymphony.webwork.ibatis;

/**
 * User: patrick
 * Date: Aug 31, 2005
 * Time: 1:56:13 PM
 */
public interface Transaction {
    void rollBack();

    void commit();
}
