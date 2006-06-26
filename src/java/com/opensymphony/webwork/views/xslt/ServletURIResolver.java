/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;


/**
 * ServletURIResolver is a URIResolver that can retrieve resources from the servlet context using the scheme "res".
 * e.g.
 *
 * A URI resolver is called when a stylesheet uses an xsl:include, xsl:import, or document() function to find the
 * resource (file).
 *
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 */
public class ServletURIResolver implements URIResolver {
    //~ Static fields/initializers /////////////////////////////////////////////

    private Log log = LogFactory.getLog(getClass());
    static final String protocol = "res:";

    //~ Instance fields ////////////////////////////////////////////////////////

    private ServletContext sc;

    //~ Constructors ///////////////////////////////////////////////////////////

    public ServletURIResolver(ServletContext sc) {
        log.trace("ServletURIResolver: " + sc);
        this.sc = sc;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Source resolve(String href, String base) throws TransformerException {
        log.debug("ServletURIResolver resolve(): href=" + href + ", base=" + base);
        if (href.startsWith(protocol)) {
            String res = href.substring(protocol.length());
            log.debug("Resolving resource <" + res + ">");

            InputStream is = sc.getResourceAsStream(res);

            if (is == null) {
                throw new TransformerException(
                        "Resource " + res + " not found in resources.");
            }

            return new StreamSource(is);
        }

        throw new TransformerException(
                "Cannot handle procotol of resource " + href);
    }
}
