/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jasperreports;

import com.opensymphony.webwork.ServletActionContext;

import com.opensymphony.xwork.util.OgnlValueStack;

import dori.jasper.engine.JRException;
import dori.jasper.engine.JRExporter;
import dori.jasper.engine.JRExporterParameter;
import dori.jasper.engine.JasperExportManager;
import dori.jasper.engine.JasperFillManager;
import dori.jasper.engine.JasperManager;
import dori.jasper.engine.JasperPrint;
import dori.jasper.engine.JasperReport;
import dori.jasper.engine.export.JRCsvExporter;
import dori.jasper.engine.export.JRHtmlExporter;
import dori.jasper.engine.export.JRHtmlExporterParameter;
import dori.jasper.engine.export.JRXlsExporter;
import dori.jasper.engine.export.JRXmlExporter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Provide a view of a webwork action as a jasper report. The report format will be determined by
 * the format parameter passed in the xwork action definition.
 * Example action definition:
 *
 * &lt;action name="jasperTest" class="com.opensymphony.webwork.views.jasperreports.example.OrderListAction"&gt;
 *    &lt;param name="dataSource"&gt;orders&lt;/param&gt;
 *    &lt;param name="format"&gt;PDF&lt;/param&gt;
 *    &lt;result name="success" type="dispatcher"&gt;
 *        &lt;param name="location"&gt;orderList.jasper&lt;/param&gt;
 *    &lt;/result&gt;
 *    &lt;interceptor-ref name="defaultStack"/&gt;
 * &lt;/action&gt;
 *
 * Valid report format strings are the following:
 * &lt;ul&gt;
 * &lt;li&gt;PDF&lt;/li&gt;
 * &lt;li&gt;XML&lt;/li&gt;
 * &lt;li&gt;HTML&lt;/li&gt;
 * &lt;li&gt;XLS&lt;/li&gt;
 * &lt;li&gt;CSV&lt;/li&gt;
 * &lt;/ul&gt;
 * It will then look for a compiled report definition of the form &lt;code&gt;&lt;view name&gt;.jasper&lt;/code&gt;,
 * run it to the appropriate output format and stream the results as the HTTP response.
 *
 * Ported to WebWork2:
 *
 * @author &lt;a href="hermanns@aixcept.de"&gt;Rainer Hermanns&lt;/a&gt;
 * @version $Id$
 */
public class JasperReportViewServlet extends HttpServlet implements JasperReportConstants {
    //~ Static fields/initializers /////////////////////////////////////////////

    protected static Log log = LogFactory.getLog(JasperReportViewServlet.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String IMAGES_URI = "/images/";

    //~ Methods ////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init()
     */
    public void init() throws ServletException {
        super.init();

        if (getServletConfig().getInitParameter("IMAGES_URI") != null) {
            IMAGES_URI = getServletConfig().getInitParameter("IMAGES_URI");
        }
    }

    /**
     * Service a HTTP request
     * @param request the request to service
     * @param response the response to send to the client
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        //construct the data source for the report
        OgnlValueStack stack = ServletActionContext.getContext().getValueStack();
        String dataSource = "" + request.getAttribute("dataSource");
        OgnlValueStackDataSource stackDataSource = new OgnlValueStackDataSource(stack, dataSource);

        //get the output format
        String outputFormat = "" + request.getAttribute("format");

        if (outputFormat == null) {
            outputFormat = FORMAT_PDF;
        }

        if (!"contype".equals(request.getHeader("User-Agent"))) {
            // Determine the directory that the report file is in and set the reportDirectory parameter
            String systemId = getServletContext().getRealPath(request.getServletPath());
            Map parameters = new OgnlValueStackShadowMap(stack);
            File directory = new File(systemId.substring(0, systemId.lastIndexOf(File.separator)));
            parameters.put("reportDirectory", directory);

            byte[] output = null;
            JasperPrint jasperPrint = null;

            // Fill the report and produce a print object
            try {
                JasperReport jasperReport = JasperManager.loadReport(systemId);
                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, stackDataSource);
            } catch (JRException e) {
                log.error("Error building report for uri " + systemId, e);
                throw new ServletException(e.getMessage(), e);
            }

            // Export the print object to the desired output format
            try {
                if (outputFormat.equals(FORMAT_PDF)) {
                    response.setContentType("application/pdf");

                    // response.setHeader("Content-disposition", "inline; filename=report.pdf");
                    output = JasperExportManager.exportReportToPdf(jasperPrint);
                } else {
                    JRExporter exporter = null;

                    if (outputFormat.equals(FORMAT_CSV)) {
                        response.setContentType("text/plain");
                        exporter = new JRCsvExporter();
                    } else if (outputFormat.equals(FORMAT_HTML)) {
                        // todo fixme... currently not working...
                        response.setContentType("text/html");
                        exporter = new JRHtmlExporter();
                        ((JRHtmlExporter) exporter).setParameter(JRHtmlExporterParameter.IMAGES_URI, IMAGES_URI);
                    } else if (outputFormat.equals(FORMAT_XLS)) {
                        response.setContentType("application/vnd.ms-excel");
                        exporter = new JRXlsExporter();
                    } else if (outputFormat.equals(FORMAT_XML)) {
                        response.setContentType("text/xml");
                        exporter = new JRXmlExporter();
                    } else {
                        throw new ServletException("Unknown report format: " + outputFormat);
                    }

                    output = exportReportToBytes(jasperPrint, exporter);
                }
            } catch (JRException e) {
                String message = "Error producing " + outputFormat + " report for uri " + systemId;
                log.error(message, e);
                throw new ServletException(e.getMessage(), e);
            }

            response.setContentLength(output.length);

            ServletOutputStream ouputStream;

            try {
                if (log.isDebugEnabled()) {
                    log.debug("Writing " + output.length + " bytes to output stream");
                }

                ouputStream = response.getOutputStream();
                ouputStream.write(output);
                ouputStream.flush();
                ouputStream.close();
            } catch (IOException e) {
                log.error("Error writing report output", e);
                throw new ServletException(e.getMessage(), e);
            }
        } else {
            // Code to handle "contype" request from IE
            try {
                ServletOutputStream outputStream;
                response.setContentType("application/pdf");
                response.setContentLength(0);
                outputStream = response.getOutputStream();
                outputStream.close();
            } catch (IOException e) {
                log.error("Error writing report output", e);
                throw new ServletException(e.getMessage(), e);
            }
        }
    }

    /**
     * Run a Jasper report to CSV format and put the results in a byte array
     * @param jasperPrint The Print object to render as CSV
     * @param exporter The exporter to use to export the report
     * @return A CSV formatted report
     * @throws JRException If there is a problem running the report
     */
    private byte[] exportReportToBytes(JasperPrint jasperPrint, JRExporter exporter) throws JRException {
        byte[] output;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

        exporter.exportReport();

        output = baos.toByteArray();

        return output;
    }
}
