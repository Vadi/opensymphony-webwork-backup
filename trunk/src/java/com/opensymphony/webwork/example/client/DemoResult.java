/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.example.client;

import java.awt.*;

import javax.swing.*;


/**
 * Result panel object for the client dispatcher example
 * @author Peter Kelley
 * @version 1.0
 */
public class DemoResult extends JPanel {
    //~ Instance fields ////////////////////////////////////////////////////////

    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JTextArea messages = new JTextArea();
    JTextField input = new JTextField();
    JTextField invocationNumber = new JTextField();
    JTextField output = new JTextField();

    //~ Constructors ///////////////////////////////////////////////////////////

    public DemoResult() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Setter for property input.
     * @param newInput the new value for field input
     */
    public void setInput(JTextField newInput) {
        input = newInput;
    }

    /**
     * Getter for property input.
     * @return the value of field input
     */
    public JTextField getInput() {
        return input;
    }

    /**
     * Setter for property invocationNumber.
     * @param newInvocationNumber the new value for field invocationNumber
     */
    public void setInvocationNumber(JTextField newInvocationNumber) {
        invocationNumber = newInvocationNumber;
    }

    /**
     * Getter for property invocationNumber.
     * @return the value of field invocationNumber
     */
    public JTextField getInvocationNumber() {
        return invocationNumber;
    }

    /**
     * Getter for property messages.
     * @return the value of field messages
     */
    public JTextArea getMessages() {
        return messages;
    }

    /**
     * Setter for property output.
     * @param newOutput the new value for field output
     */
    public void setOutput(JTextField newOutput) {
        output = newOutput;
    }

    /**
     * Getter for property output.
     * @return the value of field output
     */
    public JTextField getOutput() {
        return output;
    }

    void jbInit() throws Exception {
        jLabel1.setText("Result of Invocation Number:");
        this.setLayout(gridBagLayout1);
        jLabel2.setText("Input:");
        jLabel3.setText("Output:");
        invocationNumber.setText("");
        input.setText("");
        output.setText("");
        jLabel5.setText("Messages:");
        this.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
        this.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
        this.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
        this.add(invocationNumber, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 10), 0, 0));
        this.add(input, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 10), 0, 0));
        this.add(output, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 10), 0, 0));
        this.add(messages, new GridBagConstraints(1, 4, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 10), 0, 0));
        this.add(jLabel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 10), 0, 0));
    }
}
