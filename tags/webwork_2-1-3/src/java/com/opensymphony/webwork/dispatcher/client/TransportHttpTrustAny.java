/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * A {@link TransportHttpTrust TransportHttpTrust} implementation that trusts
 * any remote HTTPS certificate. There are security implications with using
 * this class.<BR><BR>
 *
 * This class provides end-to-end encryption for self-signed HTTPS
 * certificates. Whilst convenient if encryption is the only requirement,
 * there is no protection from server impersonation. Refer to the JavaDocs for
 * {@link TransportHttpTrustKeystore TransportHttpTrustKeystore} for a better
 * alternative.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class TransportHttpTrustAny implements TransportHttpTrust {
    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean isEncrypted() {
        return true;
    }

    public boolean isIdentified() {
        return false;
    }

    public Properties serviceInformation() {
        Properties props = new Properties();
        props.setProperty("Trust Manager Overview", "Trusts any X509 Certificate (performs no certificate validation)");
        props.setProperty("Trust Manager Warning", "The certificate has not been authenticated as operated by a given party");

        return props;
    }

    public SSLSocketFactory start(Properties properties) throws ClientException {
        SSLContext ctx = null;

        try {
            X509TrustManager trustAny = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }
            };

            TrustManager[] managers = new TrustManager[1];
            managers[0] = trustAny;
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, managers, null);
        } catch (GeneralSecurityException gse) {
            throw new ClientException(gse);
        }

        return ctx.getSocketFactory();
    }
}
