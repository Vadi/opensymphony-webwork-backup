/*
 * Copyright (c) 2006, Your Corporation. All Rights Reserved.
 */

package com.opensymphony.webwork.components;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.io.Writer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.TextProvider;
import com.opensymphony.xwork.ActionContext;

/**
 * <code>Date</code>
 *
 * @author <a href="mailto:philip.luppens@gmail.com">Philip Luppens</a>
 * @author <a href="mailto:hermanns@aixcept.de">Rainer Hermanns</a>
 * @version $Id$
 *
 *
 * @ww.tag name="date" tld-body-content="empty" tld-tag-class="com.opensymphony.webwork.views.jsp.DateTag"
 * description="Render a formatted date."
 */
public class Date extends Text {

    private static final Log LOG = LogFactory.getLog(Date.class);

    /*
     * the name of our property which we will use if the optional format
     * parameter is not specified.
     */
    public static final String DATETAG_PROPERTY = "webwork.date";

    public static final String DATETAG_PROPERTY_PAST = "webwork.date.format.past";
    public static final String DATETAG_DEFAULT_PAST = "{0} ago";
    public static final String DATETAG_PROPERTY_FUTURE = "webwork.date.format.future";
    public static final String DATETAG_DEFAULT_FUTURE = "in {0}";

    public static final String DATETAG_PROPERTY_SECONDS = "webwork.date.format.seconds";
    public static final String DATETAG_DEFAULT_SECONDS = "an instant";
    public static final String DATETAG_PROPERTY_MINUTES = "webwork.date.format.minutes";
    public static final String DATETAG_DEFAULT_MINUTES = "{0,choice,1#one minute|1<{0} minutes}";
    public static final String DATETAG_PROPERTY_HOURS = "webwork.date.format.hours";
    public static final String DATETAG_DEFAULT_HOURS = "{0,choice,1#one hour|1<{0} hours}{1,choice,0#|1#, one minute|1<, {1} minutes}";
    public static final String DATETAG_PROPERTY_DAYS = "webwork.date.format.days";
    public static final String DATETAG_DEFAULT_DAYS = "{0,choice,1#one day|1<{0} days}{1,choice,0#|1#, one hour|1<, {1} hours}";
    public static final String DATETAG_PROPERTY_YEARS = "webwork.date.format.years";
    public static final String DATETAG_DEFAULT_YEARS = "{0,choice,1#one year|1<{0} years}{1,choice,0#|1#, one day|1<, {1} days}";


    private String format;
    private boolean nice;

    public Date(OgnlValueStack stack) {
        super(stack);
    }


    /**
     * @ww.tagattribute required="false" rtexprvalue="false"
     * description=" Date or DateTime format pattern"
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @ww.tagattribute required="false" type="Boolean" default="false"
     * description="Wether to print out the date nicely"
     */
    public void setNice(boolean nice) {
        this.nice = nice;
    }

    private TextProvider findProviderInStack() {
        for (Iterator iterator = getStack().getRoot().iterator(); iterator
                .hasNext();) {
            Object o = iterator.next();

            if (o instanceof TextProvider) {
                return (TextProvider) o;
            }
        }
        return null;
    }

    public String formatTime(TextProvider tp, java.util.Date date) {
        StringBuffer sb = new StringBuffer();
        List args = new ArrayList();
        long secs = (new java.util.Date().getTime() - date.getTime()) / 1000;
        long mins = secs / 60;
        int min = (int) mins % 60;
        long hours = mins / 60;
        int hour = (int) hours % 24;
        int days = (int) hours / 24;
        int day = days % 365;
        int years = days / 365;

        if (Math.abs(secs) < 60) {
            args.add(new Long(secs));
            args.add(sb);
            args.add(null);
            sb.append(tp.getText(DATETAG_PROPERTY_SECONDS, DATETAG_DEFAULT_SECONDS, args));

        } else if (hours == 0) {
            args.add(new Long(min));
            args.add(sb);
            args.add(null);
            sb.append(tp.getText(DATETAG_PROPERTY_MINUTES, DATETAG_DEFAULT_MINUTES, args));

        } else if (days == 0) {
            args.add(new Long(hour));
            args.add(new Long(min));
            args.add(sb);
            args.add(null);
            sb.append(tp.getText(DATETAG_PROPERTY_HOURS, DATETAG_DEFAULT_HOURS, args));
        } else if (years == 0) {
            args.add(new Long(days));
            args.add(new Long(hour));
            args.add(sb);
            args.add(null);
            sb.append(tp.getText(DATETAG_PROPERTY_DAYS, DATETAG_DEFAULT_DAYS, args));
        } else {
            args.add(new Object[]{new Long(years)});
            args.add(new Object[]{new Long(day)});
            args.add(sb);
            args.add(null);

            sb.append(tp.getText(DATETAG_PROPERTY_YEARS, DATETAG_DEFAULT_YEARS, args));
        }
        args.clear();
        args.add(sb.toString());
        if (date.before(new java.util.Date())) {
            //looks like this date is passed
            return tp.getText(DATETAG_PROPERTY_PAST, DATETAG_DEFAULT_PAST, args);
        } else {
            return tp.getText(DATETAG_PROPERTY_FUTURE, DATETAG_DEFAULT_FUTURE, args);
        }
    }

    public boolean end(Writer writer, String body) {
        actualName = findString(name);
        String msg = null;
        OgnlValueStack stack = getStack();

        java.util.Date date = null;
        //find the name on the valueStack, and cast it to a date
        try {
            date = (java.util.Date) stack.findValue(actualName);
        } catch (Exception e) {
            LOG.error("Could not convert '" + actualName + "' to a java.util.Date instance");
        }
        if (date != null) {
            TextProvider tp = findProviderInStack();
            if ( tp != null ) {
                if (nice) {
                    msg = formatTime(tp, date);
                } else {
                    if (format == null) {
                        String globalFormat = null;

                        //if the format is not specified, fall back using the defined
                        // property DATETAG_PROPERTY
                        globalFormat = tp.getText(DATETAG_PROPERTY);

                        if (globalFormat != null) {
                            msg = new SimpleDateFormat(globalFormat, ActionContext.getContext().getLocale()).format(date);
                        } else {
                            msg = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, ActionContext.getContext().getLocale()).format(date);
                        }
                    } else {
                        msg = new SimpleDateFormat(format, ActionContext.getContext().getLocale()).format(date);
                    }
                }

                if (msg != null) {
                    try {
                        if (getId() == null) {
                            writer.write(msg);
                        } else {
                            stack.getContext().put(getId(), msg);
                        }
                    } catch (IOException e) {
                        LOG.error("Could not write out Date tag", e);
                    }
                }
            }
        }
        return super.end(writer, "");
    }

    public void addParameter(String key, Object value) {
        addParameter(value);
    }

    public void addParameter(Object value) {
        if (values.isEmpty()) {
            values = new ArrayList(4);
        }

        values.add(value);
    }
}
