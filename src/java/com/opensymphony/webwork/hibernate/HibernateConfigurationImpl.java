package com.opensymphony.webwork.hibernate;

import com.opensymphony.util.ClassLoaderUtil;
import com.opensymphony.xwork.interceptor.component.Disposable;
import com.opensymphony.xwork.interceptor.component.Initializable;
import com.opensymphony.xwork.interceptor.component.ResourceAware;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * User: plightbo
 * Date: Aug 9, 2005
 * Time: 8:58:24 PM
 */
public class HibernateConfigurationImpl implements HibernateConfiguration,
        Initializable, Disposable, ResourceAware {
    SessionFactory sessionFactory;
    Set resources = new HashSet();

    public void init() {
        System.out.println("HibernateConfiguration.init");
        URL url = ClassLoaderUtil.getResource("hibernate.cfg.xml", getClass());
        Configuration configuration = new Configuration().configure(url);

        resources.add("hibernate.cfg.xml");
        for (Iterator iterator = configuration.getImports().entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String name = (String) entry.getKey();
            resources.add(name.replaceAll("\\.", "/") + ".hbm.xml");
        }

        sessionFactory = configuration.buildSessionFactory();
        new SchemaUpdate(configuration).execute(true, true);
    }

    public Set getDependentResources() {
        return resources;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void dispose() {
        System.out.println("HibernateConfiguration.dispose");
        sessionFactory.close();
    }
}
