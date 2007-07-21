/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.action;

import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.webwork.showcase.dao.Dao;
import com.opensymphony.webwork.showcase.model.IdEntity;

import java.util.Collection;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AbstractCRUDAction.
 *
 * @author <a href="mailto:gielen@it-neering.net">Rene Gielen</a>
 * @author tmjee
 */

public abstract class AbstractCRUDAction extends ActionSupport {

    private static final Log log = LogFactory.getLog(AbstractCRUDAction.class);

    private Collection availableItems;
    private String[] toDelete;

    protected abstract Dao getDao();


    public Collection getAvailableItems() {
        return availableItems;
    }

    public String[] getToDelete() {
        return toDelete;
    }

    public void setToDelete(String[] toDelete) {
        this.toDelete = toDelete;
    }

    public String list() throws Exception {
        this.availableItems = getDao().findAll();
        if (log.isDebugEnabled()) {
            log.debug("AbstractCRUDAction - [list]: " + (availableItems !=null?""+availableItems.size():"no") + " items found");
        }
        return execute();
    }

    public String delete() throws Exception {
        if (toDelete != null) {
            int count=0;
            for (int i = 0, j=toDelete.length; i < j; i++) {
                count = count + getDao().delete(toDelete[i]);
            }
            if (log.isDebugEnabled()) {
                log.debug("AbstractCRUDAction - [delete]: " + count + " items deleted.");
            }
        }
        return SUCCESS;
    }

    /**
     * Utility method for fetching already persistent object from storage for usage in params-prepare-params cycle.
     *
     * @param tryId     The id to try to get persistent object for
     * @param tryObject The object, induced by first params invocation, possibly containing id to try to get persistent
     *                  object for
     * @return The persistent object, if found. <tt>null</tt> otherwise.
     */
    protected IdEntity fetch(Serializable tryId, IdEntity tryObject) {
        IdEntity result = null;
        if (tryId != null) {
            result = getDao().get(tryId);
        } else if (tryObject != null) {
            result = getDao().get(tryObject.getId());
        }
        return result;
    }
}
