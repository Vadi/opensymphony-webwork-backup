/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.security.GeneralSecurityException;
import java.security.KeyStore;

import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


/**
 * A {@link TransportHttpTrust TransportHttpTrust} implementation that
 * trusts certificates from a local keystore. This is the preferred
 * implementation for creating secure client server applications.<BR><BR>
 *
 * If optimal client to server security is required, you must use a properly
 * issued certificate and ensure a public key is available for verification on
 * the client side. The simplest way to accomplish this is by purchasing a
 * certificate from a Certificate Authority that is by default
 * trusted by the JDK. In such a case, you will not need to specify a
 * <code>trustManager</code> in your <code>Properties</code> for
 * {@link TransportHttp TransportHttp} as it will trust such certificates
 * automatically.<BR><BR>
 *
 * Alternatively, you can establish your own self-signed certificates. If you
 * only wish to use a simple public/private key pair, you can use the JDK
 * <code>keytool</code>. If you wish to setup a root certificate (which can
 * then sign certificates for individual servers) you will require a tool such
 * as OpenSSL. In any event, the public key for either your root self-signed
 * certificate, or your server certificate(s), will need to be placed into a
 * keystore. The keystore can then be trusted by the Client Dispatcher via
 * this class.<BR><BR>
 *
 * Please note that it is not possible to accommodate every conceivable public
 * key requirement in this class. If you require more advanced functionality,
 * such as checking certificate revocation lists, or obtaining the keystore
 * from an alternative input stream, you will need to write your own
 * {@link TransportHttpTrust TransportHttpTrust} implementation. However, this
 * is not difficult when based on the code contained in this class.<BR><BR>
 *
 * To use this class, your <code>Properties</code> must contain:<BR><BR>
 *
 * <code>keystoreFile</code>. The name of the keystore. By default this is
 * <code>key.store</code>. You must specify the full path to the keystore, or
 * if the keystore is available on the classpath, you can specify only the
 * keystore filename.<BR><BR>
 *
 * <code>keystorePassword</code>. The keystore password. By default,
 * <code>password</code>.<BR><BR>
 *
 * <code>keystoreAlgorithm</code>. The keystore type. By default,
 * <code>SunX509</code>.<BR><BR>
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class TransportHttpTrustKeystore implements TransportHttpTrust {
    //~ Instance fields ////////////////////////////////////////////////////////

    private String algorithm;
    private String pathKeyStore;

    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean isEncrypted() {
        return true;
    }

    public boolean isIdentified() {
        return true;
    }

    public Properties serviceInformation() {
        Properties props = new Properties();
        props.setProperty("Trust Manager Overview", "Trusts any X509 certificates or certificate chains in keystore");
        props.setProperty("Trust Manager Keystore File", pathKeyStore);
        props.setProperty("Trust Manager Keystore Type", algorithm);

        return props;
    }

    public SSLSocketFactory start(Properties properties) throws ClientException {
        pathKeyStore = properties.getProperty("keystoreFile", "key.store");

        char[] passphrase = properties.getProperty("keystorePassword", "password").toCharArray();
        algorithm = properties.getProperty("keystoreAlgorithm", "SunX509");

        InputStream inputStream;

        try {
            File file = new File(pathKeyStore);

            if (file.exists() == true) {
                inputStream = new FileInputStream(file);
            } else {
                URL url = this.getClass().getClassLoader().getResource(pathKeyStore);

                if (url == null) {
                    throw new ClientException("Not found");
                }

                inputStream = url.openStream();
            }
        } catch (IOException ioe) {
            throw new ClientException(ioe);
        }

        SSLContext ctx = null;

        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(inputStream, passphrase);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);
            tmf.init(ks);
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tmf.getTrustManagers(), null);
            inputStream.close();
        } catch (IOException ioe) {
            throw new ClientException(ioe);
        } catch (GeneralSecurityException gse) {
            throw new ClientException(gse);
        }

        return ctx.getSocketFactory();
    }
}
