/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.example.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import com.opensymphony.webwork.dispatcher.client.*;


/**
 * Provides a <code>JToolBar</code> that is also capable of acting as a
 * {@link ProgressConsumer ProgressConsumer}. This class uses a series of
 * <code>GIF</code> files to display various status and security levels. These
 * <code>GIF</code> files must be located on the classpath.<BR><BR>
 *
 * We thank the Eclipse project for providing the <code>GIF</code> files
 * shipped with the <code>ProgressConsumerToolBar</code>. The Eclipse project
 * license can be found at <a href="http://www.eclipse.org/legal/cpl-v10.html">
 * http://www.eclipse.org/legal/cpl-v10.html</a>.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class ProgressConsumerToolBar extends JToolBar implements ProgressConsumer {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static String NONE = new String("NONE");
    private static String ZERO = new String("0");

    //~ Instance fields ////////////////////////////////////////////////////////

    private Component parent;
    private ImageIcon securityEncrypted = new ImageIcon(getUrl("com/opensymphony/webwork/example/client/security-encrypted.gif"), "Encrypted Connection (no identity assurance)");
    private ImageIcon securityIdentified = new ImageIcon(getUrl("com/opensymphony/webwork/example/client/security-identified.gif"), "Encrypted and Identity Assured Connection");
    private ImageIcon securityNone = new ImageIcon(getUrl("com/opensymphony/webwork/example/client/security-none.gif"), "Insecure Connection");
    private ImageIcon statusConnecting = new ImageIcon(getUrl("com/opensymphony/webwork/example/client/status-connecting.gif"), "Connecting to Remote");
    private ImageIcon statusNone = new ImageIcon(getUrl("com/opensymphony/webwork/example/client/status-none.gif"), "");
    private ImageIcon statusProcessing = new ImageIcon(getUrl("com/opensymphony/webwork/example/client/status-processing.gif"), "Remote is Processing");
    private ImageIcon statusReceived = new ImageIcon(getUrl("com/opensymphony/webwork/example/client/status-received.gif"), "Received");
    private ImageIcon statusReceiving = new ImageIcon(getUrl("com/opensymphony/webwork/example/client/status-receiving.gif"), "Receiving from Remote");
    private ImageIcon statusRetryDelay = new ImageIcon(getUrl("com/opensymphony/webwork/example/client/status-retry-delay.gif"), "Retrying");
    private ImageIcon statusSending = new ImageIcon(getUrl("com/opensymphony/webwork/example/client/status-sending.gif"), "Sending to Remote");
    private JButton securityButton;
    private JButton statusButton;
    private JLabel outstandingLabel = new JLabel();
    private JProgressBar progressBar = new JProgressBar();
    private Map notifications = new HashMap();
    private Properties lastFactoryInformation;
    private Properties lastSecurityInformation;
    private String currentProgressId;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Create a ProgressConsumerToolbar. The parent Component is required so
     * a dialog can be created if a user clicks on the status or security icon.
     */
    public ProgressConsumerToolBar(Component parent) {
        Properties props = new Properties();
        props.setProperty("Status", "Unavailable (no connection attempted)");
        lastFactoryInformation = props;
        lastSecurityInformation = props;
        this.parent = parent;
        outstandingLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outstandingLabel.setText(ZERO);
        outstandingLabel.setToolTipText("Number of Pending Remote Actions");
        statusButton = new JButton(statusNone);
        statusButton.setBorderPainted(false);
        statusButton.setFocusPainted(false);
        statusButton.setMargin(new Insets(0, 0, 0, 0));
        statusButton.addActionListener(new ButtonListener());
        securityButton = new JButton(statusNone);
        securityButton.setBorderPainted(false);
        securityButton.setFocusPainted(false);
        securityButton.setMargin(new Insets(0, 0, 0, 0));
        securityButton.addActionListener(new ButtonListener());
        add(outstandingLabel);
        add(statusButton);
        add(securityButton);
        add(progressBar);
        currentProgressId = NONE;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Method called by the connection factory to advise of a progress update.
     */
    public synchronized void notify(ProgressNotification progressNotification) {
        if (progressNotification.getStatus() == ProgressNotification.STATUS_NONE) {
            notifications.remove(progressNotification.getId());
        } else {
            notifications.put(progressNotification.getId(), progressNotification);

            if (currentProgressId.equals(NONE)) {
                currentProgressId = progressNotification.getId();
            }
        }

        if (progressNotification.getId().equals(currentProgressId)) {
            if (progressNotification.getStatus() == ProgressNotification.STATUS_NONE) {
                if (notifications.size() > 0) {
                    Iterator iterator = notifications.keySet().iterator();
                    currentProgressId = (String) iterator.next();
                    progressNotification = (ProgressNotification) notifications.get(currentProgressId);
                } else {
                    currentProgressId = NONE;
                }
            }
        }

        if (currentProgressId.equals(NONE)) {
            outstandingLabel.setText(ZERO);
            statusButton.setIcon(statusNone);
            statusButton.setToolTipText("");
            progressBar.setIndeterminate(false);
            progressBar.setValue(0);
            progressBar.setToolTipText("");
        } else {
            int remaining = notifications.size();
            outstandingLabel.setText(new Integer(remaining).toString());
            lastFactoryInformation = progressNotification.getFactoryInformation();
            lastSecurityInformation = progressNotification.getSecurityInformation();

            if (progressNotification.getSecurity() == ProgressNotification.SECURITY_NONE) {
                securityButton.setIcon(securityNone);
                securityButton.setToolTipText(securityNone.getDescription());
            } else if (progressNotification.getSecurity() == ProgressNotification.SECURITY_ENCRYPTED) {
                securityButton.setIcon(securityEncrypted);
                securityButton.setToolTipText(securityEncrypted.getDescription());
            } else if (progressNotification.getSecurity() == ProgressNotification.SECURITY_IDENTIFIED) {
                securityButton.setIcon(securityIdentified);
                securityButton.setToolTipText(securityIdentified.getDescription());
            }

            if (progressNotification.getStatus() == ProgressNotification.STATUS_NONE) {
                statusButton.setIcon(statusNone);
                statusButton.setToolTipText("");
                progressBar.setIndeterminate(false);
                progressBar.setValue(0);
                progressBar.setToolTipText("");
            } else if (progressNotification.getStatus() == ProgressNotification.STATUS_CONNECTING) {
                statusButton.setIcon(statusConnecting);
                statusButton.setToolTipText(statusConnecting.getDescription());
                progressBar.setIndeterminate(true);
                progressBar.setToolTipText("Trying...");
            } else if (progressNotification.getStatus() == ProgressNotification.STATUS_PROCESSING) {
                statusButton.setIcon(statusProcessing);
                statusButton.setToolTipText(statusProcessing.getDescription());
                progressBar.setIndeterminate(true);
                progressBar.setToolTipText("Waiting...");
            } else if (progressNotification.getStatus() == ProgressNotification.STATUS_RECEIVING) {
                statusButton.setIcon(statusReceiving);
                statusButton.setToolTipText(statusReceiving.getDescription());
                progressBar.setIndeterminate(false);

                float completed = (float) progressNotification.getInputReceived();
                float ofTotal = (float) progressNotification.getInputSize();
                progressBar.setValue((int) (completed / ofTotal * 100));
                progressBar.setToolTipText("Receive Progress...");
            } else if (progressNotification.getStatus() == ProgressNotification.STATUS_RECEIVED) {
                statusButton.setIcon(statusReceived);
                statusButton.setToolTipText(statusReceived.getDescription());
                progressBar.setIndeterminate(false);
                progressBar.setValue(0);
                progressBar.setToolTipText("");
            } else if (progressNotification.getStatus() == ProgressNotification.STATUS_RETRY_DELAY) {
                statusButton.setIcon(statusRetryDelay);
                statusButton.setToolTipText(statusRetryDelay.getDescription());
                progressBar.setIndeterminate(true);
                progressBar.setToolTipText("Retrying...");
            } else if (progressNotification.getStatus() == ProgressNotification.STATUS_SENDING) {
                statusButton.setIcon(statusSending);
                statusButton.setToolTipText(statusSending.getDescription());
                progressBar.setIndeterminate(false);

                float completed = (float) progressNotification.getOutputTransmitted();
                float ofTotal = (float) progressNotification.getOutputSize();
                progressBar.setValue((int) (completed / ofTotal * 100));
                progressBar.setToolTipText("Send Progress...");
            }
        }
    }

    private URL getUrl(String filename) {
        URL url = this.getClass().getClassLoader().getResource(filename);

        return url;
    }

    /**
     * Create a <code>JPanel</code> containing the given
     * <code>Properties</code>.
     */
    private Component makeTab(Properties info) {
        JPanel panel = new JPanel();

        PropertiesTableModel propsModel = new PropertiesTableModel();
        propsModel.setupData(info);

        JTable table = new JTable(propsModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setPreferredScrollableViewportSize(new Dimension(500, 400));
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(1000);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        return panel;
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    /**
     * Responds to clicks on the security and status icons, creating a
     * new dialog that displays connection related information.
     */
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            final DemoSwingWorker worker = new DemoSwingWorker() {
                public Object construct() {
                    JTabbedPane tabbedPane = new JTabbedPane();

                    Component factoryInformation = makeTab(lastFactoryInformation);
                    tabbedPane.addTab("Connection", factoryInformation);

                    Component securityInformation = makeTab(lastSecurityInformation);
                    tabbedPane.addTab("Security", securityInformation);

                    JOptionPane.showMessageDialog(parent, tabbedPane, "Connection Information", JOptionPane.INFORMATION_MESSAGE);

                    return "done";
                }
            };

            worker.start();
        }
    }

    /**
     * Produces a <code>JTable</code> containing information from a
     * <code>Properties</code> object.
     */
    private class PropertiesTableModel extends AbstractTableModel {
        private Object[][] data;
        private String[] headers = {"Property", "Value"};

        public int getColumnCount() {
            return headers.length;
        }

        public String getColumnName(int col) {
            return headers[col];
        }

        public int getRowCount() {
            return data.length;
        }

        public Object getValueAt(int row, int col) {
            return (row < data.length) ? data[row][col] : null;
        }

        public void setupData(Properties properties) {
            // Sort properties by key
            List list = new ArrayList(20);
            Iterator iterator = properties.keySet().iterator();

            while (iterator.hasNext()) {
                list.add(iterator.next());
            }

            Collections.sort(list);

            // Add sorted properties to table data model
            data = new Object[properties.size()][2];

            int row = 0;
            iterator = list.iterator();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                data[row][0] = key;
                data[row][1] = properties.get(key);
                row++;
            }

            updateData();
        }

        public void updateData() {
            fireTableDataChanged();
        }
    }
}
