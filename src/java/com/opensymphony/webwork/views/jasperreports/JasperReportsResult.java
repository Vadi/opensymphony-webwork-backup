package com.opensymphony.webwork.views.jasperreports;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;
import dori.jasper.engine.*;
import dori.jasper.engine.export.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class JasperReportsResult extends WebWorkResultSupport implements JasperReportConstants {
    private final static Log LOG = LogFactory.getLog(JasperReportsResult.class);

    private String dataSource;
    private String format;

    protected String IMAGES_URI = "/images/";

    protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        if (format == null) {
            format = FORMAT_PDF;
        }

        if (dataSource == null) {
            String message = "No dataSource specified...";
            LOG.error(message);
            throw new RuntimeException(message);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating JasperReport for dataSource = " + dataSource + ", format = " + format);
        }

        HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);

        //construct the data source for the report
        OgnlValueStack stack = ServletActionContext.getContext().getValueStack();
        String dataSource = "" + request.getAttribute("dataSource");
        OgnlValueStackDataSource stackDataSource = new OgnlValueStackDataSource(stack, dataSource);

        //get the output format
        String outputFormat = "" + request.getAttribute("format");

        // (Map) ActionContext.getContext().getSession().get("IMAGES_MAP");
        if (outputFormat == null) {
            outputFormat = FORMAT_PDF;
        }

        if (!"contype".equals(request.getHeader("User-Agent"))) {
            // Determine the directory that the report file is in and set the reportDirectory parameter
            ServletContext servletContext
                    = ((ServletConfig) invocation.getInvocationContext().
                    get(ServletActionContext.SERVLET_CONFIG)).getServletContext();
            String systemId = servletContext.getRealPath(finalLocation);
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
                LOG.error("Error building report for uri " + systemId, e);
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
                        response.setContentType("text/html");

                        Map imagesMap = new HashMap();

                        request.getSession().setAttribute("IMAGES_MAP", imagesMap);
                        exporter = new JRHtmlExporter();
                        exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
                        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, IMAGES_URI);
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
                LOG.error(message, e);
                throw new ServletException(e.getMessage(), e);
            }

            response.setContentLength(output.length);

            ServletOutputStream ouputStream;

            try {
                ouputStream = response.getOutputStream();
                ouputStream.write(output);
                ouputStream.flush();
                ouputStream.close();
            } catch (IOException e) {
                LOG.error("Error writing report output", e);
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
                LOG.error("Error writing report output", e);
                throw new ServletException(e.getMessage(), e);
            }
        }
    }

    /**
     * Run a Jasper report to CSV format and put the results in a byte array
     *
     * @param jasperPrint The Print object to render as CSV
     * @param exporter    The exporter to use to export the report
     * @return A CSV formatted report
     * @throws dori.jasper.engine.JRException If there is a problem running the report
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

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

