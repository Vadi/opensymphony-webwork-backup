/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;


/**
 * A {@link ProgressConsumer ProgressConsumer} that writes notifications to the
 * standard output stream.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class ProgressConsumerString implements ProgressConsumer {
    //~ Instance fields ////////////////////////////////////////////////////////

    private String lastStartedId = "none";

    //~ Methods ////////////////////////////////////////////////////////////////

    public synchronized void notify(ProgressNotification progressNotification) {
        String status = "uknown";
        boolean showProgress = false;
        int completed = 0;
        int ofTotal = -1;

        if (progressNotification.getStatus() == ProgressNotification.STATUS_NONE) {
            status = "Not Executing";
        } else if (progressNotification.getStatus() == ProgressNotification.STATUS_CONNECTING) {
            status = "Connecting to Remote";
        } else if (progressNotification.getStatus() == ProgressNotification.STATUS_PROCESSING) {
            status = "Remote is Processing";
        } else if (progressNotification.getStatus() == ProgressNotification.STATUS_RECEIVING) {
            status = "Receiving from Remote";
            showProgress = true;
            completed = progressNotification.getInputReceived();
            ofTotal = progressNotification.getInputSize();
        } else if (progressNotification.getStatus() == ProgressNotification.STATUS_RECEIVED) {
            status = "Received";
        } else if (progressNotification.getStatus() == ProgressNotification.STATUS_RETRY_DELAY) {
            status = "Retrying";
        } else if (progressNotification.getStatus() == ProgressNotification.STATUS_SENDING) {
            status = "Sending to Remote";
            showProgress = true;
            completed = progressNotification.getOutputTransmitted();
            ofTotal = progressNotification.getOutputSize();
        } else {
            status = "Unsupported Status";
        }

        if (progressNotification.getStatus() == ProgressNotification.STATUS_SENDING) {
            if (!lastStartedId.equals(progressNotification.getId())) {
                System.out.println("ID: " + progressNotification.getId() + "; Factory: " + progressNotification.getFactoryInformation() + "; Security: " + progressNotification.getSecurityInformation());
                lastStartedId = progressNotification.getId();
            }
        }

        if (showProgress) {
            System.out.println("ID: " + progressNotification.getId() + "; " + status + "; " + completed + " of " + ofTotal);
        } else {
            System.out.println("ID: " + progressNotification.getId() + "; " + status);
        }
    }
}
