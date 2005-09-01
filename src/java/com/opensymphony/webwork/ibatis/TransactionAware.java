package com.opensymphony.webwork.ibatis;

/**
 * User: patrick
 * Date: Aug 31, 2005
 * Time: 1:55:37 PM
 */
public interface TransactionAware {
    void setTransaction(Transaction transaction);
}
