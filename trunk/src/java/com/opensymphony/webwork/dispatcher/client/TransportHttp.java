/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.security.MessageDigest;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;


/**
 * A connection factory for HTTP and HTTPS (which is the primary connection
 * factory supported by the Client Dispatcher).
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class TransportHttp extends TransportFactoryBase {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(TransportHttp.class);
    public static final String REPLY_CONTENT_SIZE = new String("Reply_Content_Size");
    public static final String KEY_LOOKUP_SSL = "com.opensymphony.webwork.dispatcher.client.lookupSslInformation";
    public static final String KEY_URL = "com.opensymphony.webwork.dispatcher.client.url";
    public static final String KEY_RETRY_MAXIMUM = "com.opensymphony.webwork.dispatcher.client.retryMaximum";
    public static final String KEY_RETRY_DELAY = "com.opensymphony.webwork.dispatcher.client.retryDelay";
    public static final String KEY_TRUST_MANAGER = "com.opensymphony.webwork.dispatcher.client.trustManager";

    //~ Instance fields ////////////////////////////////////////////////////////

    private Properties factoryInformation;
    private URL url;
    private boolean lookupSslInformation;
    private int retryMaximum;
    private int securityLevel = ProgressNotification.SECURITY_NONE;
    private long retryDelay;

    //~ Constructors ///////////////////////////////////////////////////////////

    public TransportHttp(Properties properties) throws ClientException {
        setProperties(properties);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Modify the <code>Properties</code> in use by the factory.
     * <BR>
     * <BR> The <code>Properties</code> are expected to contain, at minimum, a
     * <code>com.opensymphony.webwork.dispatcher.client.url</code> property, such as
     * <code>http://localhost:8080/admin/ClientDispatcher</code>.
     * <BR>
     * <BR> Optional properties include <code>com.opensymphony.webwork.client.retryMaximum</code>,
     * which is the number of times to retry in the event of an <code>IOException</code> (defaults
     * to 3). Another optional property is <code>com.opensymphony.webwork.dispatcher.client.retryDelay</code>,
     * which is the number of milliseconds to sleep between retries (defaults to 5,000).
     * <BR>
     * <BR> If the <code>com.opensymphony.webwork.dispatcher.client.url</code> is a HTTPS URL, by default the
     * class will attempt to connect via HTTPS using the default Java trust configuration. The
     * optional property <code>com.opensymphony.webwork.dispatcher.client.trustManager</code> can be used to
     * refer to a class that implements the {@link TransportHttpTrust TransportHttpTrust} interface.
     * <BR>
     * <BR> If the <code>com.opensymphony.webwork.dispatcher.client.url</code> is a HTTPS URL, you may specify
     * an optional property, <code>com.opensymphony.webwork.dispatcher.client.lookupSslInformation</code>. True
     * by default, this property tells the class whether to incorporate information about the SSL
     * certificate chain into {@link ProgressNotification#setSecurityInformation(Properties)
     * ProgressNotification.setSecurityInformation(Properties)}. If set to false, a performance gain
     * will be achieved.
     *
     * @param properties the properties to use to set up the factory
     * @throws ClientException
     */
    public void setProperties(Properties properties) throws ClientException {
        super.setProperties(properties);

        try {
            this.url = new URL(properties.getProperty(KEY_URL));
        } catch (MalformedURLException mfe) {
            throw new ClientException(mfe);
        }

        Boolean ssl = new Boolean(properties.getProperty(KEY_LOOKUP_SSL, "true"));
        lookupSslInformation = ssl.booleanValue();

        try {
            this.retryMaximum = new Integer(properties.getProperty(KEY_RETRY_MAXIMUM, "3")).intValue();
            this.retryDelay = new Long(properties.getProperty(KEY_RETRY_DELAY, "5000")).longValue();
        } catch (NumberFormatException nfe) {
            throw new ClientException(nfe);
        }

        factoryInformation = new Properties();
        factoryInformation.setProperty("Transport", "WebWork 2 HTTP(S) Provider");

        factoryInformation.setProperty("URL", url.toString());
        factoryInformation.setProperty("Retry Maximum", new Integer(retryMaximum).toString());
        factoryInformation.setProperty("Retry Delay", new Long(retryDelay).toString());

        if (url.getProtocol().equals("https")) {
            factoryInformation.setProperty("Lookup SSL Information", new Boolean(lookupSslInformation).toString());

            String trustManager = properties.getProperty(KEY_TRUST_MANAGER, "none");

            if (trustManager.equals("none")) {
                log.debug("HTTPS URL; using standard Java certificate trusts");
            } else {
                log.debug("HTTPS URL; creating customised trust manager " + trustManager);

                SSLSocketFactory sslSocketFactory;

                try {
                    Class clazz = Class.forName(trustManager);
                    TransportHttpTrust trust = (TransportHttpTrust) clazz.newInstance();
                    sslSocketFactory = trust.start(properties);
                    factoryInformation.setProperty("Trust Manager", trustManager);
                    factoryInformation.putAll(trust.serviceInformation());

                    if (trust.isEncrypted() == true) {
                        securityLevel = ProgressNotification.SECURITY_ENCRYPTED;
                    }

                    if (trust.isIdentified() == true) {
                        securityLevel = ProgressNotification.SECURITY_IDENTIFIED;
                    }
                } catch (ClassCastException cce) {
                    throw new ClientException(cce);
                } catch (ClassNotFoundException cnfe) {
                    throw new ClientException(cnfe);
                } catch (InstantiationException ie) {
                    throw new ClientException(ie);
                } catch (IllegalAccessException iae) {
                    throw new ClientException(iae);
                }

                HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
                log.debug("Completed setup of customised SSL Socket Factory");
            }
        }
    }

    /**
     * Should not be called directly. Normal users should only use the
     * <code>execute()</code> method of their {@link ClientRequest ClientRequest}
     * proxy object.
     */
    public RemoteResult execute(ClientRequestInvocation invocation) throws ClientException {
        RemoteResult actionResult = null;

        String id = generateRequestId();

        ProgressNotification notification = new ProgressNotification(id);
        notification.setFactoryInformation(factoryInformation);
        notification.setSecurity(securityLevel);

        int count = 0;

        int transmitSize = -1;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(invocation);
            oos.flush();
            oos.close();
            transmitSize = baos.size();
        } catch (IOException ignored) {
        }

        while ((actionResult == null) && (count < retryMaximum)) {
            try {
                notification.setStatus(ProgressNotification.STATUS_NONE);
                getProgressConsumer().notify(notification);

                count++;

                HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                uc.setUseCaches(false);
                uc.setDefaultUseCaches(false);
                uc.setDoInput(true);
                uc.setDoOutput(true);
                uc.setRequestProperty("Content-Type", "application/octet-stream");

                if (sessionId.equals(SESSION_UNDEFINED)) {
                    log.debug("Session ID is undefined (" + id + ")");
                } else {
                    uc.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
                    log.debug("Sending session ID: " + sessionId + " (" + id + ")");
                }

                if (log.isDebugEnabled()) {
                    log.debug("Attempt " + count + " to connect to " + url + " (" + id + ")");
                }

                notification.setStatus(ProgressNotification.STATUS_CONNECTING);
                getProgressConsumer().notify(notification);
                uc.connect();

                Properties security = new Properties();
                security.setProperty("Status", "Not a secure connection");

                if (url.getProtocol().equals("https") && !lookupSslInformation) {
                    security.setProperty("Status", "X509 Certificates not looked up");
                }

                if (url.getProtocol().equals("https") && lookupSslInformation) {
                    if (uc instanceof HttpsURLConnectionImpl) {
                        HttpsURLConnectionImpl sslUc = (HttpsURLConnectionImpl) uc;
                        X509Certificate[] cert = (X509Certificate[]) sslUc.getServerCertificates();
                        security.setProperty("Status", "Found " + cert.length + " X509 certificates");

                        for (int i = 0; i < cert.length; i++) {
                            try {
                                byte[] abyte0 = cert[i].getEncoded();
                                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                                byte[] abyte1 = messageDigest.digest(abyte0);
                                String decryptedDigest = toHexString(abyte1);
                                security.setProperty("Cert " + i + " Fingerprint (SHA)", decryptedDigest);
                            } catch (Exception ignored) {
                            }

                            String validity = "Valid";

                            try {
                                cert[i].checkValidity();
                            } catch (CertificateExpiredException ex) {
                                validity = "Expired!";
                            } catch (CertificateNotYetValidException ex) {
                                validity = "Not Yet Valid";
                            }

                            security.setProperty("Cert " + i + " Validity", validity);

                            security.setProperty("Cert " + i + " Valid From", cert[i].getNotBefore().toString());
                            security.setProperty("Cert " + i + " Valid Until", cert[i].getNotAfter().toString());
                            security.setProperty("Cert " + i + " Version", new Integer(cert[i].getVersion()).toString());
                            security.setProperty("Cert " + i + " Serial No", cert[i].getSerialNumber().toString());
                            security.setProperty("Cert " + i + " Cipher", sslUc.getCipherSuite());
                            security.setProperty("Cert " + i + " Subject", cert[i].getSubjectDN().getName());
                            security.setProperty("Cert " + i + " Issuer", cert[i].getIssuerDN().getName());
                        }
                    } else {
                        security.setProperty("Status", "Unknown HTTPS Provider");
                    }
                }

                notification.setSecurityInformation(security);

                ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new ProgressOutputStream(notification, getProgressConsumer(), getProperties(), uc.getOutputStream(), transmitSize)));
                out.writeObject(invocation);
                out.flush();
                out.close();

                int contentLength = -1;

                try {
                    contentLength = uc.getHeaderFieldInt(REPLY_CONTENT_SIZE, -1);
                } catch (Exception ex) {
                    if (log.isDebugEnabled()) {
                        log.debug("Could not determine content length (" + id + ")");
                    }
                }

                BufferedInputStream bis = new BufferedInputStream(new ProgressInputStream(notification, getProgressConsumer(), getProperties(), uc.getInputStream(), contentLength));

                ObjectInputStream in = new ObjectInputStream(bis);
                actionResult = (RemoteResult) in.readObject();
                in.close();

                notification.setStatus(ProgressNotification.STATUS_NONE);
                getProgressConsumer().notify(notification);

                if (!sessionId.equals(actionResult.getSessionId())) {
                    log.debug("Received new session ID: " + actionResult.getSessionId() + " (" + id + ")");
                    sessionId = actionResult.getSessionId();
                }
            } catch (IOException ioe) {
                if (log.isDebugEnabled()) {
                    log.debug(ioe);
                }

                if (count < retryMaximum) {
                    log.debug("Delaying retry for " + retryDelay + " milliseconds (" + id + ")");
                    notification.setStatus(ProgressNotification.STATUS_RETRY_DELAY);
                    getProgressConsumer().notify(notification);

                    try {
                        Thread.sleep(retryDelay);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    log.debug("Maximum retry count reached (" + id + ")");
                    notification.setStatus(ProgressNotification.STATUS_NONE);
                    getProgressConsumer().notify(notification);
                    throw new ClientException("Maximum retry count reached; aborting");
                }
            } catch (ClassNotFoundException ce) {
                log.error(ce);
                notification.setStatus(ProgressNotification.STATUS_NONE);
                getProgressConsumer().notify(notification);
                throw new ClientException(ce);
            }
        }

        return actionResult;
    }

    /**
     * Converts a byte into hexadecimal format, appending the hexadecimal
     * representation to the <code>StringBuffer</code>.
     */
    private void byte2hex(byte byte0, StringBuffer stringbuffer) {
        char[] ac = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F'
        };
        int i = (byte0 & 0xf0) >> 4;
        int j = byte0 & 0xf;
        stringbuffer.append(ac[i]);
        stringbuffer.append(ac[j]);
    }

    /**
     * Converts a byte array into a String, with each byte
     * shown in hexadecimal format with a colon character for separation.
     */
    private String toHexString(byte[] abyte0) {
        StringBuffer stringbuffer = new StringBuffer();
        int i = abyte0.length;

        for (int j = 0; j < i; j++) {
            byte2hex(abyte0[j], stringbuffer);

            if (j < (i - 1)) {
                stringbuffer.append(":");
            }
        }

        return stringbuffer.toString();
    }
}
