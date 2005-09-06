package com.opensymphony.webwork.sandbox.typeconversion;

import com.opensymphony.webwork.components.Set;
import com.opensymphony.xwork.ActionSupport;

import java.util.List;
import java.util.Map;

/**
 * User: plightbo
 * Date: Sep 5, 2005
 * Time: 5:42:14 PM
 */
public class TypeConversionAction extends ActionSupport {
    Map beanMap;
    List beanList;
    Set beanSet;
    Map stringMap;
    List stringList;
    Set stringSet;
    Bean bean;

    public String execute() throws Exception {
        System.out.println(beanMap);

        return SUCCESS;
    }

    public Map getBeanMap() {
        return beanMap;
    }

    public void setBeanMap(Map beanMap) {
        this.beanMap = beanMap;
    }

    public List getBeanList() {
        return beanList;
    }

    public void setBeanList(List beanList) {
        this.beanList = beanList;
    }

    public Set getBeanSet() {
        return beanSet;
    }

    public void setBeanSet(Set beanSet) {
        this.beanSet = beanSet;
    }

    public Map getStringMap() {
        return stringMap;
    }

    public void setStringMap(Map stringMap) {
        this.stringMap = stringMap;
    }

    public List getStringList() {
        return stringList;
    }

    public void setStringList(List stringList) {
        this.stringList = stringList;
    }

    public Set getStringSet() {
        return stringSet;
    }

    public void setStringSet(Set stringSet) {
        this.stringSet = stringSet;
    }

    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }
}
