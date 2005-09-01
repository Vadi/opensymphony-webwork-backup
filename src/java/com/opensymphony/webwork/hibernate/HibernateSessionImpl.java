package com.opensymphony.webwork.hibernate;

import com.opensymphony.xwork.interceptor.component.Disposable;
import com.opensymphony.xwork.interceptor.component.Initializable;
import org.hibernate.*;
import org.hibernate.stat.SessionStatistics;

import java.io.Serializable;
import java.sql.Connection;

/**
 * User: plightbo
 * Date: Aug 10, 2005
 * Time: 12:06:19 AM
 */
public class HibernateSessionImpl implements HibernateSession, HibernateConfigurationAware, Initializable, Disposable {
    HibernateConfiguration config;
    Session session;

    public void setHibernateConfiguration(HibernateConfiguration config) {
        this.config = config;
    }

    public void init() {
        session = config.getSessionFactory().openSession();
    }

    public void dispose() {
        session.flush();
        session.close();
    }

    public EntityMode getEntityMode() {
        return session.getEntityMode();
    }

    public Session getSession(EntityMode entityMode) {
        return session.getSession(entityMode);
    }

    public void flush() throws HibernateException {
        session.flush();
    }

    public void setFlushMode(FlushMode flushMode) {
        session.setFlushMode(flushMode);
    }

    public FlushMode getFlushMode() {
        return session.getFlushMode();
    }

    public void setCacheMode(CacheMode cacheMode) {
        session.setCacheMode(cacheMode);
    }

    public CacheMode getCacheMode() {
        return session.getCacheMode();
    }

    public SessionFactory getSessionFactory() {
        return session.getSessionFactory();
    }

    public Connection connection() throws HibernateException {
        return session.connection();
    }

    public Connection disconnect() throws HibernateException {
        return session.disconnect();
    }

    public void reconnect() throws HibernateException {
        session.reconnect();
    }

    public void reconnect(Connection connection) throws HibernateException {
        session.reconnect(connection);
    }

    public Connection close() throws HibernateException {
        return session.close();
    }

    public void cancelQuery() throws HibernateException {
        session.cancelQuery();
    }

    public boolean isOpen() {
        return session.isOpen();
    }

    public boolean isConnected() {
        return session.isConnected();
    }

    public boolean isDirty() throws HibernateException {
        return session.isDirty();
    }

    public Serializable getIdentifier(Object object) throws HibernateException {
        return session.getIdentifier(object);
    }

    public boolean contains(Object object) {
        return session.contains(object);
    }

    public void evict(Object object) throws HibernateException {
        session.evict(object);
    }

    public Object load(Class aClass, Serializable serializable, LockMode lockMode) throws HibernateException {
        return session.load(aClass, aClass, lockMode);
    }

    public Object load(String string, Serializable serializable, LockMode lockMode) throws HibernateException {
        return session.load(string, serializable, lockMode);
    }

    public Object load(Class aClass, Serializable serializable) throws HibernateException {
        return session.load(aClass, serializable);
    }

    public Object load(String string, Serializable serializable) throws HibernateException {
        return session.load(string, serializable);
    }

    public void load(Object object, Serializable serializable) throws HibernateException {
        session.load(object, serializable);
    }

    public void replicate(Object object, ReplicationMode replicationMode) throws HibernateException {
        session.replicate(object, replicationMode);
    }

    public void replicate(String string, Object object, ReplicationMode replicationMode) throws HibernateException {
        session.replicate(string, object, replicationMode);
    }

    public Serializable save(Object object) throws HibernateException {
        return session.save(object);
    }

    public void save(Object object, Serializable serializable) throws HibernateException {
        session.save(object, serializable);
    }

    public Serializable save(String string, Object object) throws HibernateException {
        return session.save(string, object);
    }

    public void save(String string, Object object, Serializable serializable) throws HibernateException {
        session.save(string, object, serializable);
    }

    public void saveOrUpdate(Object object) throws HibernateException {
        session.saveOrUpdate(object);
    }

    public void saveOrUpdate(String string, Object object) throws HibernateException {
        session.saveOrUpdate(string, object);
    }

    public void update(Object object) throws HibernateException {
        session.update(object);
    }

    public void update(Object object, Serializable serializable) throws HibernateException {
        session.update(object, serializable);
    }

    public void update(String string, Object object) throws HibernateException {
        session.update(string, object);
    }

    public void update(String string, Object object, Serializable serializable) throws HibernateException {
        session.update(string, object, serializable);
    }

    public Object merge(Object object) throws HibernateException {
        return session.merge(object);
    }

    public Object merge(String string, Object object) throws HibernateException {
        return session.merge(string, object);
    }

    public void persist(Object object) throws HibernateException {
        session.persist(object);
    }

    public void persist(String string, Object object) throws HibernateException {
        session.persist(string, object);
    }

    public void delete(Object object) throws HibernateException {
        session.delete(object);
    }

    public void lock(Object object, LockMode lockMode) throws HibernateException {
        session.load(object, lockMode);
    }

    public void lock(String string, Object object, LockMode lockMode) throws HibernateException {
        session.lock(string, object, lockMode);
    }

    public void refresh(Object object) throws HibernateException {
        session.refresh(object);
    }

    public void refresh(Object object, LockMode lockMode) throws HibernateException {
        session.refresh(object, lockMode);
    }

    public LockMode getCurrentLockMode(Object object) throws HibernateException {
        return session.getCurrentLockMode(object);
    }

    public Transaction beginTransaction() throws HibernateException {
        return session.beginTransaction();
    }

    public Criteria createCriteria(Class aClass) {
        return session.createCriteria(aClass);
    }

    public Criteria createCriteria(Class aClass, String string) {
        return session.createCriteria(aClass, string);
    }

    public Criteria createCriteria(String string) {
        return session.createCriteria(string);
    }

    public Criteria createCriteria(String string, String string1) {
        return session.createCriteria(string, string1);
    }

    public Query createQuery(String string) throws HibernateException {
        return session.createQuery(string);
    }

    public SQLQuery createSQLQuery(String string) throws HibernateException {
        return session.createSQLQuery(string);
    }

    public Query createFilter(Object object, String string) throws HibernateException {
        return session.createFilter(object, string);
    }

    public Query getNamedQuery(String string) throws HibernateException {
        return session.getNamedQuery(string);
    }

    public void clear() {
        session.clear();
    }

    public Object get(Class aClass, Serializable serializable) throws HibernateException {
        return session.get(aClass, serializable);
    }

    public Object get(Class aClass, Serializable serializable, LockMode lockMode) throws HibernateException {
        return session.get(aClass, serializable, lockMode);
    }

    public Object get(String string, Serializable serializable) throws HibernateException {
        return session.get(string, serializable);
    }

    public Object get(String string, Serializable serializable, LockMode lockMode) throws HibernateException {
        return session.get(string, serializable, lockMode);
    }

    public String getEntityName(Object object) throws HibernateException {
        return session.getEntityName(object);
    }

    public Filter enableFilter(String string) {
        return session.enableFilter(string);
    }

    public Filter getEnabledFilter(String string) {
        return session.getEnabledFilter(string);
    }

    public void disableFilter(String string) {
        session.disableFilter(string);
    }

    public SessionStatistics getStatistics() {
        return session.getStatistics();
    }
}
