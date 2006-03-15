/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */

package com.opensymphony.webwork.showcase;

import com.opensymphony.xwork.ActionSupport;

import java.text.DateFormat;
import java.util.Date;

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
}
