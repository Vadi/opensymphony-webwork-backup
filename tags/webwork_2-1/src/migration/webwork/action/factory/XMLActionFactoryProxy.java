/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action.factory;

import org.apache.commons.logging.LogFactory;
import webwork.action.Action;
import webwork.action.standard.XML;

/**
 * Obtain the XML action if the action suffix is <code>".xml"</code>.
 *
 * @author Philipp Meier <meier@o-matic.de>
 * @version $Revision$
 */
public class XMLActionFactoryProxy extends ActionFactoryProxy {
    // Attributes ----------------------------------------------------

    // Constructors --------------------------------------------------
    public XMLActionFactoryProxy(ActionFactory aFactory) {
        super(aFactory);
    }

    // ActionFactory overrides ---------------------------------------
    /**
     * If the suffix of the action is <code>".xml"</code>, get the XML action.
     *
     * @param name
     * @param aName
     * @return the XML-action or action corresponding to the given name
     * @throws ServletException
     * @throws Exception
     */
    public Action getActionImpl(String aName) throws Exception {
        // Check for xml extension
        if (aName.endsWith(".xml")) {
            String xmlName = aName.substring(0, aName.length() - 4).replace('.', '/') + ".xml";
            LogFactory.getLog(this.getClass()).debug("Documentname: " + xmlName);
            XML xml = (XML) ActionFactory.getAction("XML");
            xml.setDocumentName(xmlName);

            //if (xml.getDocument() == null)
            //   throw new IllegalArgumentException("XML '"+aName+"' does not exist");

            return xml;
        } else {
            return getNextFactory().getActionImpl(aName);
        }
    }
}
