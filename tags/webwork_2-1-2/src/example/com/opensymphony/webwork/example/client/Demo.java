/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.example.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.opensymphony.webwork.dispatcher.client.ClientException;
import com.opensymphony.webwork.dispatcher.client.TransportFactory;
import com.opensymphony.webwork.dispatcher.client.TransportHttp;
import com.opensymphony.xwork.dispatcher.MappingFactory;
import com.opensymphony.xwork.dispatcher.ObjectDispatcher;
import com.opensymphony.xwork.dispatcher.ResultObjectFactory;
import com.opensymphony.xwork.dispatcher.XMLMappingFactory;
import com.opensymphony.xwork.util.OgnlValueStack;
import java.io.StringWriter;
import java.io.PrintWriter;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.webwork.dispatcher.client.HTTPClientObjectFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.webwork.dispatcher.client.HTTPClientActionProxyFactory;


/**
 * SWING-based demonstration of Client Dispatcher and notification system.
 * Requires <code>demo.properties</code> to be available on classpath.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class Demo extends JFrame {
    //~ Instance fields ////////////////////////////////////////////////////////

    private DemoResult taskOutput;
    private TransportFactory transportFactory;
    private int taskNumber = 1;

    /** logger for this class */
    private static final Log log = LogFactory.getLog(Demo.class);


    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Construct the demo object
     * @param connectionURL The URL to connect to to process the remote action invocations. If this
     * is null it will be searched for in the demo.properties file
     * @throws ClientException
     * @throws IOException
     */
    public Demo(String connectionURL) throws ClientException, IOException {
        super("WebWork2 Client Dispatcher Demonstration");

        // Load in the properties
        URL url = this.getClass().getClassLoader().getResource("com/opensymphony/webwork/example/client/demo.properties");

        if (url == null) {
            throw new IOException("demo.properties not found on classpath");
        }

        InputStream inputStream = url.openStream();
        Properties props = new Properties();
        props.load(inputStream);

        if (connectionURL != null) {
            props.setProperty(TransportHttp.KEY_URL, connectionURL);
        }

        // Create the connection factory
        transportFactory = new TransportHttp(props);

        // Set up the action factory
        ObjectFactory.setObjectFactory(new HTTPClientObjectFactory(transportFactory));
        ActionProxyFactory.setFactory(new HTTPClientActionProxyFactory());

        // Create a progress consumer and attach it to the connection factory
        ProgressConsumerToolBar consumer = new ProgressConsumerToolBar(this);
        transportFactory.setProgressConsumer(consumer);
        consumer.setBorderPainted(true);
        consumer.setFloatable(false);

        //consumer.setToolTipText("Progress Monitor");
        //Create the demo's UI.
        JButton startButton = new JButton("Start New Client Request");
        startButton.setActionCommand("start");
        startButton.addActionListener(new ButtonListener());

        taskOutput = new DemoResult();

        JPanel panel = new JPanel();
        panel.add(startButton);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(panel, BorderLayout.NORTH);
        contentPane.add(consumer, BorderLayout.SOUTH);
        contentPane.add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public static void main(String[] args) throws ClientException, IOException {
        JFrame frame = new Demo(args[0]);
        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

        frame.pack();
        frame.setVisible(true);
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    class ActualTask implements ResultObjectFactory {
        ActualTask() {
            int thisTaskNumber = ++taskNumber;
            taskOutput.getInvocationNumber().setText(taskNumber + "");
            taskOutput.getMessages().setText("");

            OgnlValueStack result = null;
            try {
                MappingFactory mappingFactory = new XMLMappingFactory();
                ObjectDispatcher objectDispatcher = new ObjectDispatcher(mappingFactory);
                result = objectDispatcher.dispatch(taskOutput,
                    "/clientDispatcher",
                    "DemoAction",
                    "demo-mappings",
                    this);
            } catch (Exception ex) {
                StringWriter stackTrace = new StringWriter();

                ex.printStackTrace(new PrintWriter(stackTrace, true));
                taskOutput.getMessages().append("Error during invocation number: " + thisTaskNumber
                                                + ": " + "\n");
                taskOutput.getMessages().append(stackTrace + "\n");
            }
        }

        /**
         * Get a result object to copy the information to
         * @param viewName nominally the name of the result object but we only have 1 in this case
         * @return the task output panel
         */
        public Object getResultObject(String viewName) {
            // only 1 possible object
            log.debug("Returning result object: " + taskOutput);
            if ("error".equals(viewName)) {
                taskOutput.getMessages().append("The server reported an error whilst executing the action\n");
            }
            return taskOutput;
        }
    }

    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            final DemoSwingWorker worker = new DemoSwingWorker() {
                public Object construct() {
                    return new ActualTask();
                }
            };

            worker.start();
        }
    }
}
