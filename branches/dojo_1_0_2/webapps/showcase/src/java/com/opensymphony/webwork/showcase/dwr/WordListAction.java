/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.dwr;

import com.opensymphony.xwork.ActionSupport;

import java.util.List;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class WordListAction extends ActionSupport {

    public List getAllWords() {
        return WordList.getInstance().getAllWords();
    }


    public String execute() throws Exception {
        return SUCCESS;
    }
}
