/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.dao;

import org.hibernate.auction.exceptions.InfrastructureException;
import org.hibernate.auction.model.LocalizedText;
import org.hibernate.Query;
import org.hibernate.HibernateException;

import java.util.*;

/**
 * LocalizedTextDAO
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public class LocalizedTextDAO extends AbstractDAO {

    /**
     * Get a List of the LocalizedTexts for the specified Locale
     * @param locale the specified Locale to find the texts for 
     */
    public List getTexts(Locale locale) throws InfrastructureException {
		try {
			Query q = persistenceManager.getSession().getNamedQuery("localeTexts");
			q.setString("localeStr", (locale == null)?null:locale.toString());
            return q.list();
		}
		catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
	}

    /**
     * Get the LocalizedText with the specified Locale and key.
     * @param locale the specified Locale of the LocalizedText
     * @param key the specified key of the LocalizedText
     */
    public LocalizedText getLocalizedText(Locale locale, String key) {
        try {
			Query q = persistenceManager.getSession().getNamedQuery("aText");
			q.setString("localeStr", (locale == null)?null:locale.toString());
            q.setString("key",key);
            return (LocalizedText) q.uniqueResult();
		}
		catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
    }

    /**
     * Get a List of LocalizedTexts which have the specified key.
     * @param key the key of the texts
     */
    public List getTextsForKey(String key) {
        try {
			Query q = persistenceManager.getSession().getNamedQuery("textsForKey");
            q.setString("key",key);
            return  q.list();
		}
		catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
    }

    public void makePersistent(LocalizedText text)
			throws InfrastructureException {

		try {
			persistenceManager.getSession().saveOrUpdate(text);
		} catch (HibernateException ex) {
			throw new InfrastructureException(ex);
		}
    }
}
