/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.standard;

import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import webwork.action.ActionSupport;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

/**
 * XML "execution" wrapper.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @version $Revision$
 * @see <related>
 */
public class XML
        extends ActionSupport {
    // Static --------------------------------------------------------

    // Attributes ----------------------------------------------------
    String documentName;
    Document document;
    DocumentBuilder db;

    // Action implementation -----------------------------------------
    public String execute()
            throws Exception {
        try {
            URL documentUrl = getDocumentURL();
            document = parseDocument(documentUrl);
        } catch (Exception e) {
            return handleException(e);
        }

        return SUCCESS;
    }

    // Public --------------------------------------------------------
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Document getDocument() {
        return document;
    }

    // Protected -----------------------------------------------------
    protected Document parseDocument(URL documentUrl)
            throws IOException, ParserConfigurationException, SAXException {
        //if (db == null)
        //{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //dbf.setValidating(true);
        //dbf.setIgnoreComments(false);
        //dbf.setIgnoreElementContentWhitespace(false);
        //dbf.setCoalescing(false);
        //dbf.setExpandEntityReferences(true);

        db = dbf.newDocumentBuilder();
        //}

        Document doc = db.parse(documentUrl.openStream());
        return doc;
    }

    protected URL getDocumentURL() {
        URL documentUrl;
        String ext = documentName.substring(documentName.lastIndexOf("."));
        String realDocumentName = documentName.substring(0, documentName.length() - ext.length());
        realDocumentName = realDocumentName.replace('.', '/');
        realDocumentName += ext;
        LogFactory.getLog(this.getClass()).debug("Document: " + realDocumentName);

        documentUrl = getClass().getClassLoader().getResource(realDocumentName);
        LogFactory.getLog(this.getClass()).debug("DocumentUrl: " + documentUrl);
        return documentUrl;
    }

    protected String handleException(Exception e) {
        e.printStackTrace();
        return ERROR;
    }

}
