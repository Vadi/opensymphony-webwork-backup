package com.opensymphony.webwork.example;

import com.opensymphony.xwork.Action;

/**
 * @author $Author$
 * @version $Revision$
 */
public class ExceptionAction implements Action {
    public String execute() throws Exception {
        throw new Exception("This is expected");
    }
}
