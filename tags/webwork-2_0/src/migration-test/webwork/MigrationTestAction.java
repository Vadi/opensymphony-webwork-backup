/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package webwork;

import webwork.action.ActionSupport;
import webwork.action.CommandDriven;
import webwork.action.PrepareAction;


/**
 * MigrationTestAction
 * @author Jason Carreira
 * Date: Nov 6, 2003 9:05:13 PM
 */
public class MigrationTestAction extends ActionSupport implements PrepareAction, CommandDriven {
    //~ Instance fields ////////////////////////////////////////////////////////

    private boolean doExecuteCalled = false;
    private boolean doTestCommandCalled = false;
    private boolean prepared = false;

    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean isDoExecuteCalled() {
        return doExecuteCalled;
    }

    public boolean isDoTestCommandCalled() {
        return doTestCommandCalled;
    }

    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public String doTestCommand() throws Exception {
        doTestCommandCalled = true;

        return SUCCESS;
    }

    public void prepare() throws Exception {
        prepared = true;
    }
}
