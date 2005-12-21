/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.dao;

import org.hibernate.auction.persistence.components.PersistenceManagerAware;
import org.hibernate.auction.persistence.components.PersistenceManager;

/**
 * Created by IntelliJ IDEA.
 */
public abstract class AbstractDAO implements PersistenceManagerAware {
    protected PersistenceManager persistenceManager;

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
}
