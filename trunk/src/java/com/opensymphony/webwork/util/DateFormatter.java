/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *	A bean that can be used to format dates
 *      
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 */
public class DateFormatter {
    // Attributes ----------------------------------------------------
    DateFormat parser;
    DateFormat format;
    Date date;

    // Public --------------------------------------------------------
    public DateFormatter() {
        this.parser = new SimpleDateFormat();
        this.format = new SimpleDateFormat();
        this.date = new Date();
    }

    public void setFormat(String format) {
        this.format = new SimpleDateFormat(format);
    }

    public void setParseFormat(String format) {
        this.parser = new SimpleDateFormat(format);
    }

    public String getFormattedDate() {
        return format.format(date);
    }

    public void setDate(String date) {
        try {
            this.date = parser.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(int date) {
        setDate(Integer.toString(date));
    }

    public Date getDate() {
        return this.date;
    }

    public void setTime(long time) {
        date.setTime(time);
    }

    public void setParser(DateFormat parser) {
        this.parser = parser;
    }

    public void setFormat(DateFormat format) {
        this.format = format;
    }
}
