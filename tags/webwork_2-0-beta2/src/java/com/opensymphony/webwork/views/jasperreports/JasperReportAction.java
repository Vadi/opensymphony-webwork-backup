/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jasperreports;

import com.opensymphony.webwork.ServletActionContext;

import com.opensymphony.xwork.ActionSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <code>JasperReportAction</code>
 *
 * @author <a href="mailto:hermanns@aixcept.de">Rainer Hermanns</a>
 * @version $Id$
 */
public class JasperReportAction extends ActionSupport implements JasperReportConstants {
    //~ Static fields/initializers /////////////////////////////////////////////

    /** jakarta-commons-logging reference */
    private final static Log log = LogFactory.getLog(JasperReportAction.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    /** data source static param */
    private String dataSource;

    /** format static param */
    private String format;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Sets the dataSource for the requested report from the static params.
     *
     * @param dataSource - datasource attribute from static params
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Returns the dataSource for the requested report from the static params.
     *
     * @return dataSource - datasource attribute from static params
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * Sets the output format of the report.
     *
     * @param format - the output format of the report.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Returns the output format of the report.
     *
     * @return format - the output format of the report.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the static params format and dataSource from static params as request attributes and forwards to the
     * JasperReportViewServlet.
     *
     * @return SUCCESS, if all parameters are given, ERROR otherwise
     * @throws Exception
     */
    public String execute() throws Exception {
        super.execute();

        if (format == null) {
            format = FORMAT_PDF;
        }

        if (dataSource == null) {
            addActionError("No datasource was specified...");

            if (log.isErrorEnabled()) {
                log.error("JasperReportAction - [execute]: No dataSource specified...");
            }

            return ERROR;
        }

        if (log.isDebugEnabled()) {
            log.debug("JasperReportAction - [execute]: Creating JasperReport for dataSource=" + dataSource + ", format=" + format);
        }

        ServletActionContext.getRequest().setAttribute("dataSource", dataSource);
        ServletActionContext.getRequest().setAttribute("format", format);

        // Forward to the next page
        return SUCCESS;
    }
}
