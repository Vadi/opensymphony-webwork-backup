/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.dwr;

import com.opensymphony.xwork.ActionSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class WordCheckAction extends ActionSupport {


    private List possibleWords = new ArrayList();

    private String wordToCheckFor = "";

    public void setWordToCheckFor(String wordToCheckFor) {
        this.wordToCheckFor = wordToCheckFor;
    }

    public String getWordToCheckFor() {
        return wordToCheckFor;
    }

    public String getMsg() {
        return "testing 1 2 3";
    }


    public String[] getPossibleWords() {
        return (String[]) possibleWords.toArray(new String[0]);
    }


    public String execute() throws Exception {
        List words = WordList.getInstance().getAllWords();
        for (Iterator i = words.iterator(); i.hasNext(); ) {
            String word = (String) i.next();
            if (word.indexOf(wordToCheckFor) >= 0) {
                possibleWords.add(word);
            }
        }
        return SUCCESS;
    }
}
