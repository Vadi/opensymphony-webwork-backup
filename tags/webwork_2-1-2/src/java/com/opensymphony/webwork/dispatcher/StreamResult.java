package com.opensymphony.webwork.dispatcher;

import com.opensymphony.xwork.ActionInvocation;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Implements an XWork Result that takes an InputStream object available from a chained
 * Action and redirects it to the browser.
 * <p/>
 * The following declaration must be added to the xwork.xml file after the <package>
 * element:
 * <p/>
 * <result-types>
 * <result-type name="stream" class="StreamResult"/>
 * </result-types>
 * <p/>
 * To use the stream result type add the following as part of the action declaration:
 * <p/>
 * <result name="success" type="stream">
 * <param name="contentType">image/jpeg</param>
 * <param name="inputName">imageStream</param>
 * <param name="bufferSize">1024</param>
 * </result>
 * <p/>
 * contentType = the stream mime-type as sent to the web browser
 * inputName = the name of the InputStream property from the chained action (default = "inputStream")
 * bufferSize = the size of the buffer to copy from input to output (defaul = 1024)
 *
 * @author mcrawford
 */
public class StreamResult extends WebWorkResultSupport {

    protected String contentType = "text/plain";

    protected String inputName = "inputStream";

    protected int bufferSize = 1024;

    /**
     * @return Returns the bufferSize.
     */
    public int getBufferSize() {
        return (bufferSize);
    }

    /**
     * @param bufferSize The bufferSize to set.
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * @return Returns the contentType.
     */
    public String getContentType() {
        return (contentType);
    }

    /**
     * @param contentType The contentType to set.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return Returns the inputName.
     */
    public String getInputName() {
        return (inputName);
    }

    /**
     * @param inputName The inputName to set.
     */
    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    /**
     * @see com.opensymphony.xwork.Result#execute(com.opensymphony.xwork.ActionInvocation)
     */
    protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {

        // Find the inputstream from the invocation variable stack
        InputStream oInput = (InputStream) invocation.getStack().findValue(inputName);

        // Find the Response in context
        HttpServletResponse oResponse = (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);

        // Set the content type
        oResponse.setContentType(contentType);

        // Get the outputstream
        OutputStream oOutput = oResponse.getOutputStream();

        // Copy input to output
        byte[] oBuff = new byte[bufferSize];
        int iSize = 0;
        while (-1 != (iSize = oInput.read(oBuff))) {
            oOutput.write(oBuff, 0, iSize);
        }

        // Flush
        oOutput.flush();
    }

}
