/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.dwr;

import java.util.List;
import java.util.ArrayList;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class WordList {

    private List words = new ArrayList() {
        {
            add("ax");
            add("air");
            add("ear");
            add("ace");
            add("add");
            add("age");
            add("ago");
            add("aid");
            add("aim");
            add("all");
            add("any");
            add("ape");
            add("arc");
            add("arm");
            add("art");
            add("ass");
            add("awe");
            add("eat");
            add("era");
            add("oaf");
            add("bar");
            add("bay");
            add("car");
            add("day");
            add("far");
            add("gay");
            add("jar");
            add("jaw");
            add("law");
            add("lay");
            add("mar");
            add("par");
            add("paw");
            add("pay");
            add("raw");
            add("ray");
        }
    };


    private static WordList wordList;

    private WordList() {}

    public static WordList getInstance() {
        if (wordList == null) {
            wordList = new WordList();
        }
        return wordList;
    }

    public List getAllWords() {
        return words;
    }
}
