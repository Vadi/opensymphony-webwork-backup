/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Date;
import java.util.Properties;


/**
 * Monitors the progress of writing to an OutputStream. Links in with a
 * {@link ProgressConsumer ProgressConsumer} and <code>Properties</code> from
 * a {@link TransportFactory TransportFactory}.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class ProgressOutputStream extends FilterOutputStream {
    //~ Instance fields ////////////////////////////////////////////////////////

    private ProgressConsumer progressConsumer;
    private ProgressNotification notification;
    private int lastNotificationStatus;
    private int nwritten = 0;
    private int size = 0;
    private long lastUpdate;
    private long updateFrequency;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Monitors the progress of an output stream.<BR><BR>
     *
     * The <code>Properties</code> may contain an optional value for
     * <code>updateFrequency</code>. The <code>updateFrequency</code> specifies
     * the number of milliseconds that must pass between notifications to
     * the {@link ProgressConsumer ProgressConsumer} concerning standard
     * stream processing. Certain major processing events such as stream
     * establishment and closure will always result in a notification,
     * irrespective of the <code>updateFrequency</code>. The default value
     * for <code>updateFrequency</code> is 250.
     */
    public ProgressOutputStream(ProgressNotification notification, ProgressConsumer progressConsumer, Properties props, OutputStream out, int contentLength) {
        super(out);
        size = contentLength;
        this.notification = notification;
        this.progressConsumer = progressConsumer;
        this.lastUpdate = new Date().getTime();
        this.lastNotificationStatus = notification.getStatus();
        notification.setOutputSize(size);

        try {
            this.updateFrequency = new Long(props.getProperty("updateFrequency", "250")).longValue();
        } catch (NumberFormatException nfe) {
            updateFrequency = 500;
        }

        try {
            notification.setStatus(ProgressNotification.STATUS_SENDING);
        } catch (ClientException cae) {
        }

        updateBytes(nwritten);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Overrides <code>FilterOutputStream.close</code>
     * to update the progress monitor and close the stream.
     */
    public void close() throws IOException {
        try {
            flush();
        } catch (IOException ignored) {
        }

        updateBytes(nwritten);
        out.close();
    }

    /**
     * Overrides <code>FilterOutputStream.flush</code>
     * to update the progress monitor after the flush.
     */
    public void flush() throws IOException {
        out.flush();
        updateBytes(nwritten);
    }

    /**
     * Overrides <code>FilterOutputStream.write</code>
     * to update the progress monitor after the write.
     */
    public void write(int b) throws IOException {
        out.write(b);
        ++nwritten;
        updateBytes(nwritten);
    }

    /**
     * Overrides <code>FilterOutputStream.read</code>
     * to update the progress monitor after the write.
     */
    public void write(byte[] b) throws IOException {
        int length = b.length;
        out.write(b);

        if (length > 0) {
            nwritten += length;
            updateBytes(nwritten);
        }
    }

    /**
     * Overrides <code>FilterOutputStream.write</code>
     * to update the progress monitor after the write.
     */
    public void write(byte[] b, int off, int len) throws IOException {
        if ((off | len | (b.length - (len + off)) | (off + len)) < 0) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = 0; i < len; i++) {
            write(b[off + i]);
        }
    }

    /**
     * Causes the progress monitor to be notified if appropriate.
     */
    private void updateBytes(int nowWritten) {
        boolean update = false;

        if (nowWritten >= size) {
            if (notification.getStatus() == ProgressNotification.STATUS_SENDING) {
                try {
                    notification.setStatus(ProgressNotification.STATUS_PROCESSING);
                } catch (ClientException ignored) {
                }
            }
        }

        if (lastNotificationStatus != notification.getStatus()) {
            update = true;
        }

        long currentTime = new Date().getTime();

        if (notification.getStatus() == ProgressNotification.STATUS_SENDING) {
            if (currentTime > (lastUpdate + updateFrequency)) {
                update = true;
            }
        }

        if (update == true) {
            lastUpdate = currentTime;
            lastNotificationStatus = notification.getStatus();
            notification.setOutputTransmitted(nowWritten);
            progressConsumer.notify(notification);
        }
    }
}
