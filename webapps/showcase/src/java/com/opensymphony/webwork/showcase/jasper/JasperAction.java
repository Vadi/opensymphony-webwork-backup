/*
 * Copyright (c) 2002-2005 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.jasper;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JasperCompileManager;

import com.opensymphony.webwork.showcase.person.Person;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.webwork.ServletActionContext;

/**
 * @author Philip Luppens
 * @author Rainer Hermanns
 */
public class JasperAction extends ActionSupport {

    private List myList;

    /*
      * (non-Javadoc)
      *
      * @see com.opensymphony.xwork.ActionSupport#execute()
      */
    public String execute() throws Exception {

        // create some imaginary persons
        Person p1 = new Person(new Long(1), "Patrick", "Lightbuddie");
        Person p2 = new Person(new Long(2), "Jason", "Carrora");
        Person p3 = new Person(new Long(3), "Alexandru", "Papesco");
        Person p4 = new Person(new Long(4), "Jay", "Boss");
        Person p5 = new Person(new Long(5), "Rainer", "Hermanos");

        /*
           * store everything in a list - normally, this should be coming from a
           * database but for the sake of simplicity, I left that out
           */
        myList = new ArrayList();
        myList.add(p1);
        myList.add(p2);
        myList.add(p3);
        myList.add(p4);
        myList.add(p5);


        /*
           * Here we compile our xml jasper template to a jasper file.
           * Note: this isn't exactly considered 'good practice'.
           * You should either use precompiled jasper files (.jasper) or provide some kind of check
           * to make sure you're not compiling the file on every request.
           * If you don't have to compile the report, you just setup your data source (eg. a List), and skip this
           */
        try {
            JasperCompileManager.compileReportToFile(
                    ServletActionContext.getServletContext().getRealPath("/jasper/sample_report.jrxml"),
                    ServletActionContext.getServletContext().getRealPath("/jasper/sample_report.jasper"));
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        //if all goes well ..
        return SUCCESS;
    }

    /**
     * @return Returns the myList.
     */
    public List getMyList() {
        return myList;
    }

}
