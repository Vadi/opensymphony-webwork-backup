/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.Properties;


/**
 * Monitors the progress of reading from an InputStream. Links in with a
 * {@link ProgressConsumer ProgressConsumer} and <code>Properties</code> from
 * a {@link TransportFactory TransportFactory}.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class ProgressInputStream extends FilterInputStream {
    //~ Instance fields ////////////////////////////////////////////////////////

    private ProgressConsumer progressConsumer;
    private ProgressNotification notification;
    private int lastNotificationStatus;
    private int nread = 0;
    private int size = 0;
    private long lastUpdate;
    private long updateFrequency;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Monitors the progress of an input stream.<BR><BR>
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
    public ProgressInputStream(ProgressNotification notification, ProgressConsumer progressConsumer, Properties props, InputStream in, int contentLength) {
        super(in);
        size = contentLength;
        this.notification = notification;
        this.progressConsumer = progressConsumer;
        this.lastUpdate = new Date().getTime();
        this.lastNotificationStatus = notification.getStatus();
        notification.setInputSize(size);

        try {
            this.updateFrequency = new Long(props.getProperty("updateFrequency", "250")).longValue();
        } catch (NumberFormatException nfe) {
            updateFrequency = 500;
        }

        try {
            notification.setStatus(ProgressNotification.STATUS_RECEIVING);
        } catch (ClientException ignored) {
        }

        updateBytes(nread);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Overrides <code>FilterInputStream.close</code>
     * to update the progress monitor and close the stream.
     */
    public void close() throws IOException {
        updateBytes(nread);
        in.close();
    }

    /**
     * Overrides <code>FilterInputStream.read</code>
     * to update the progress monitor after the read.
     */
    public int read() throws IOException {
        int c = in.read();

        if (c >= 0) {
            ++nread;
            updateBytes(nread);
        }

        return c;
    }

    /**
     * Overrides <code>FilterInputStream.read</code>
     * to update the progress monitor after the read.
     */
    public int read(byte[] b) throws IOException {
        int nr = in.read(b);

        if (nr > 0) {
            nread += nr;
            updateBytes(nread);
        }

        return nr;
    }

    /**
     * Overrides <code>FilterInputStream.read</code>
     * to update the progress monitor after the read.
     */
    public int read(byte[] b, int off, int len) throws IOException {
        int nr = in.read(b, off, len);

        if (nr > 0) {
            nread += nr;
            updateBytes(nread);
        }

        return nr;
    }

    /**
     * Overrides <code>FilterInputStream.reset</code>
     * to reset the progress monitor as well as the stream.
     */
    public synchronized void reset() throws IOException {
        in.reset();
        nread = size - in.available();
        updateBytes(nread);
    }

    /**
     * Overrides <code>FilterInputStream.skip</code>
     * to update the progress monitor after the skip.
     */
    public long skip(long n) throws IOException {
        long nr = in.skip(n);

        if (nr > 0) {
            nread += nr;
            updateBytes(nread);
        }

        return nr;
    }

    /**
     * Causes the progress monitor to be notified if appropriate.
     */
    private void updateBytes(int nowRead) {
        boolean update = false;

        if (nowRead >= size) {
            if (notification.getStatus() == ProgressNotification.STATUS_RECEIVING) {
                try {
                    notification.setStatus(ProgressNotification.STATUS_RECEIVED);
                } catch (ClientException ignored) {
                }
            }
        }

        if (lastNotificationStatus != notification.getStatus()) {
            update = true;
        }

        long currentTime = new Date().getTime();

        if (notification.getStatus() == ProgressNotification.STATUS_RECEIVING) {
            if (currentTime > (lastUpdate + updateFrequency)) {
                update = true;
            }
        }

        if (update == true) {
            lastUpdate = currentTime;
            lastNotificationStatus = notification.getStatus();
            notification.setInputReceived(nowRead);
            progressConsumer.notify(notification);
        }
    }
}
