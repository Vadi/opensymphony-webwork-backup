/*
 * Created on Sep 20, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.opensymphony.webwork.dispatcher;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;

/**
 * @author bchoi
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ChartResult implements Result {

    private int width;
    private int height;
    boolean chartSet = false;
    JFreeChart chart;

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

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setChart(JFreeChart chart) {
        this.chart = chart;
        chartSet = true;
    }
}
