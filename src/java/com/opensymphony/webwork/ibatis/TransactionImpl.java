package com.opensymphony.webwork.ibatis;

import com.opensymphony.xwork.interceptor.component.Disposable;
import com.opensymphony.xwork.interceptor.component.Initializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;

/**
 * User: patrick
 * Date: Aug 31, 2005
 * Time: 1:56:18 PM
 */
public class TransactionImpl implements Transaction, SqlMapAware, Initializable, Disposable {
    private static final Log LOG = LogFactory.getLog(TransactionImpl.class);

    protected SqlMap sqlMap;
    protected boolean rollBack;
    protected boolean committed;

    public void setSqlMap(SqlMap sqlMap) {
        this.sqlMap = sqlMap;
    }

    public void init() {
        try {
            sqlMap.getClient().startTransaction();
        } catch (SQLException e) {
            LOG.error("Could not begin tx", e);
        }
    }

    public void rollBack() {
        rollBack = true;
    }

    public void commit() {
        if (rollBack) {
            LOG.error("Attempted to commit tx after it was already rolled back");
            return;
        }

        try {
            sqlMap.getClient().commitTransaction();
            committed = true;
        } catch (SQLException e) {
            LOG.error("Could not commit tx", e);
        }
    }

    public void dispose() {
        if (!rollBack) {
            commit();
        }

        try {
            sqlMap.getClient().endTransaction();
        } catch (SQLException e) {
            LOG.error("Error ending tx", e);
        }
    }
}
