/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on Sep 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.ServletActionContext;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;


/**
 * @author bchoi
 */
public class ChartResult implements Result {
    //~ Instance fields ////////////////////////////////////////////////////////

    JFreeChart chart;
    boolean chartSet = false;
    private int height;
    private int width;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setChart(JFreeChart chart) {
        this.chart = chart;
        chartSet = true;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void execute(ActionInvocation invocation) throws Exception {
        JFreeChart chart = null;

        if (chartSet) {
            chart = this.chart;
        } else {
            chart = (JFreeChart) invocation.getStack().findValue("chart");
        }

        if (chart == null) {
            throw new NullPointerException("No chart found");
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        OutputStream os = response.getOutputStream();
        ChartUtilities.writeChartAsPNG(os, chart, width, height);
        os.flush();
    }
}
