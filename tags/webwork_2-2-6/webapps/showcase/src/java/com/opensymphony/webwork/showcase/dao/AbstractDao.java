/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.dao;

import com.opensymphony.webwork.showcase.model.IdEntity;
import com.opensymphony.webwork.showcase.exception.CreateException;
import com.opensymphony.webwork.showcase.exception.UpdateException;
import com.opensymphony.webwork.showcase.exception.StorageException;
import com.opensymphony.webwork.showcase.application.Storage;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AbstractDao.
 *
 * @author <a href="mailto:gielen@it-neering.net">Rene Gielen</a>
 * @author tmjee
 */

public abstract class AbstractDao implements Serializable, Dao {

    private static final Log log = LogFactory.getLog(AbstractDao.class);

    private Storage storage;

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public IdEntity get(Serializable id) {
        return getStorage().get(getFeaturedClass(), id);
    }

    public Serializable create(IdEntity object) throws CreateException {
        return getStorage().create(object);
    }

    public IdEntity update(IdEntity object) throws UpdateException {
        return getStorage().update(object);
    }

    public Serializable merge(IdEntity object) throws StorageException {
        return getStorage().merge(object);
    }

    public int delete(Serializable id) throws CreateException {
        return getStorage().delete(getFeaturedClass(), id);
    }

    public int delete(IdEntity object) throws CreateException {
        return getStorage().delete(object);
    }

    public Collection findAll() {
        return getStorage().findAll(getFeaturedClass());
    }

}
