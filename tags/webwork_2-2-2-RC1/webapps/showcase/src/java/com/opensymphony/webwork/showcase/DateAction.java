/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */

package com.opensymphony.webwork.showcase;

import com.opensymphony.xwork.ActionSupport;

import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * <code>DateAction</code>
 *
 * @author Rainer Hermanns
 */
public class DateAction extends ActionSupport {

    private static DateFormat DF = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

    public String getDate() {
        return DF.format(new Date());
    }

    public Date getNow() {
        return new Date();
    }

    public Date getBefore() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
    }

    public Date getAfter() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }
}
