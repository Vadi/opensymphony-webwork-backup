/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import java.util.Date;
import java.util.Properties;


/**
 * Stores information of interest to {@link ProgressConsumer ProgressConsumer}
 * implementations. This information is updated by
 * {@link TransportFactory#execute(ClientRequestInvocation)
 * TransportFactory.execute(ClientRequestInvocation)},
 * {@link ProgressInputStream ProgressInputStream} and
 * {@link ProgressOutputStream ProgressOutputStream}.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class ProgressNotification {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The {@link TransportFactory TransportFactory} is trying to initially
     * contact the remote server.
     */
    public static final int STATUS_CONNECTING = 1;

    /**
     * The {@link TransportFactory TransportFactory} is sending the request
     * to the remote server.
     */
    public static final int STATUS_SENDING = 2;

    /**
     * The {@link TransportFactory TransportFactory} is awaiting a result from
     * the remote server.
     */
    public static final int STATUS_PROCESSING = 3;

    /**
     * The {@link TransportFactory TransportFactory} is receiving a result from
     * the remote server.
     */
    public static final int STATUS_RECEIVING = 4;

    /**
     * The {@link TransportFactory TransportFactory} sucessfully received the
     * result from the remote server.
     */
    public static final int STATUS_RECEIVED = 5;

    /**
     * The {@link TransportFactory TransportFactory} experienced a connection
     * problem, and is currently pausing before it will attempt to retry.
     */
    public static final int STATUS_RETRY_DELAY = 6;

    /**
     * The {@link TransportFactory TransportFactory} is not attempting any
     * operation. This does not indicate success or failure. This status
     * indicates the {@link ProgressNotification ProgressNotification} object
     * has just been created (ie just before
     * {@link ProgressNotification#STATUS_SENDING
     * ProgressNotification.STATUS_SENDING}
     * or it has finished (ie after
     * {@link ProgressNotification#STATUS_RECEIVING
     * ProgressNotification.STATUS_RECEIVING}
     * or
     * {@link ProgressNotification# STATUS_RETRY_DELAY
     * ProgressNotification. STATUS_RETRY_DELAY}).
     */
    public static final int STATUS_NONE = 10;

    /**
     * The {@link TransportFactory TransportFactory} does not assert there is
     * any encryption or identification of the remote server.
     */
    public static final int SECURITY_NONE = 1;

    /**
     * The {@link TransportFactory TransportFactory} asserts that communications
     * with the remote server are encrypted, but does not assert the identity of
     * the remote server.
     */
    public static final int SECURITY_ENCRYPTED = 2;

    /**
     * The {@link TransportFactory TransportFactory} asserts that communications
     * with the remote server are both encrypted, and the identity of the remote
     * server has been validated. A {@link TransportFactory TransportFactory}
     * should not make this assertion if the identity of the remote server
     * has not been confirmed by an appropriate public key certificate chain.
     */
    public static final int SECURITY_IDENTIFIED = 3;

    //~ Instance fields ////////////////////////////////////////////////////////

    private Date execution;
    private Properties factoryInformation = new Properties();
    private Properties securityInformation = new Properties();
    private String id;
    private int inputReceived;
    private int inputSize;
    private int outputSize;
    private int outputTransmitted;
    private int security;
    private int status;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Create a new {@link ProgressNotification ProgressNotification} for the
     * provided <code>id</code>. Because a
     * {@link TransportFactory#execute(ClientRequestInvocation)
     * TransportFactory.execute(ClientRequestInvocation)} will likely have many
     * stages to its execution (none, create, sending, processing, receiving
     * etc), many {@link ProgressNotification ProgressNotification}s may be
     * created. The <code>id</code> is guaranteed by the
     * {@link TransportFactory TransportFactory} to remain the same between
     * successive {@link ProgressNotification ProgressNotification}s for a
     * single {@link TransportFactory#execute(ClientRequestInvocation)
     * TransportFactory.execute(ClientRequestInvocation)} method call.
     */
    public ProgressNotification(String id) throws ClientException {
        if (id != null) {
            this.id = id;
            this.execution = new Date();
            setStatus(STATUS_NONE);
            setSecurity(SECURITY_NONE);
        } else {
            throw new ClientException("ID invalid");
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Date getExecution() {
        return execution;
    }

    /**
     * Sets a representation of information about the
     * {@link TransportFactory TransportFactory}. By convention, this
     * information is likely to be available to end users of the application.
     */
    public void setFactoryInformation(Properties factoryInformation) throws ClientException {
        if (factoryInformation != null) {
            this.factoryInformation = factoryInformation;
        } else {
            throw new ClientException("Invalid factory information");
        }
    }

    public Properties getFactoryInformation() {
        return this.factoryInformation;
    }

    public String getId() {
        return id;
    }

    public void setInputReceived(int inputReceived) {
        if (inputReceived > inputSize) {
            this.inputReceived = inputSize;
        } else {
            this.inputReceived = inputReceived;
        }
    }

    /**
     * Returns number of bytes received of the
     * {@link RemoteResult RemoteResult}. It should not be
     * expected that this will ever equal <code>inputSize</code>. Instead,
     * the status will change to STATUS_RECEIVED when the input has been fully
     * received.
     */
    public int getInputReceived() {
        return inputReceived;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
        this.inputReceived = 0;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setOutputSize(int outputSize) {
        this.outputTransmitted = 0;
        this.outputSize = outputSize;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public void setOutputTransmitted(int outputTransmitted) {
        if (outputTransmitted > outputSize) {
            this.outputTransmitted = outputSize;
        } else {
            this.outputTransmitted = outputTransmitted;
        }
    }

    /**
     * Returns number of bytes transmitted of the
     * {@link ClientRequestInvocation ClientRequestInvocation}. It should not
     * be expected that this will ever equal <code>outputSize</code>. Instead,
     * the status will change to STATUS_PROCESSING when the output has been
     * fully sent.
     */
    public int getOutputTransmitted() {
        return outputTransmitted;
    }

    public void setSecurity(int security) throws ClientException {
        if ((security == SECURITY_NONE) || (security == SECURITY_ENCRYPTED) || (security == SECURITY_IDENTIFIED)) {
            this.security = security;
        } else {
            throw new ClientException("Invalid security");
        }
    }

    public int getSecurity() {
        return this.security;
    }

    /**
     * Sets a representation of information about the security of
     * the connection (if any). By convention, this information is likely
     * to be available to end users of the application.
     */
    public void setSecurityInformation(Properties securityInformation) throws ClientException {
        if (securityInformation != null) {
            this.securityInformation = securityInformation;
        } else {
            throw new ClientException("Invalid security information");
        }
    }

    public Properties getSecurityInformation() {
        return this.securityInformation;
    }

    public void setStatus(int status) throws ClientException {
        if ((status == STATUS_SENDING) || (status == STATUS_CONNECTING) || (status == STATUS_RECEIVED) || (status == STATUS_PROCESSING) || (status == STATUS_RECEIVING) || (status == STATUS_RETRY_DELAY) || (status == STATUS_NONE)) {
            this.status = status;
        } else {
            throw new ClientException("Invalid status");
        }
    }

    public int getStatus() {
        return this.status;
    }
}
