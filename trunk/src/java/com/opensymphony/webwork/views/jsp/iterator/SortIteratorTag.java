/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.iterator;

import com.opensymphony.webwork.views.jsp.WebWorkBodyTagSupport;

import javax.servlet.jsp.JspException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A Tag that sorts a List using a Comparator both passed in as the tag attribute.
 * If 'id' attribute is specified, the sorted list will be placed into the PageContext
 * attribute using the key specified by 'id'. The sorted list will ALWAYS be
 * pushed into the stack and poped at the end of this tag.
 *
 * USAGE 1:
 * <pre>
 * &lt;ww:sort comparator="myComparator" source="myList"&gt;
 * 		&lt;ww:iterator&gt;
 * 			&lt;!-- do something with each sorted elements --&gt;
 * 			&lt;ww:property value="..." /&gt;
 *      &lt;/ww:iterator&gt;
 * &lt;/ww:sort&gt;
 * </pre>
 *
 * USAGE 2:
 * <pre>
 * &lt;ww:sort id="mySortedList comparator="myComparator" source="myList" /&gt;
 *
 * &lt;%
 *    List sortedList = (List) pageContext.getAttribute("mySortedList");
 *    for (Iterator i = sortedList.iterator(); i.hasNext(); ) {
 *    	// do something with each of the sorted elements
 *    }
 * %&gt;
 * </pre>
 *
 *
 * @author Rickard �berg (rickard@dreambean.com)
 * @author tm_jee (tm_jee(at)yahoo.co.uk)
 * @version $Revision$
 */
public class SortIteratorTag extends WebWorkBodyTagSupport {

	String comparatorAttr;
    String sourceAttr;

    public void setComparator(String aComparator) {
        comparatorAttr = aComparator;
    }


    public void setSource(String aName) {
        sourceAttr = aName;
    }

    public int doStartTag() throws JspException {
        List listToSort;
        if (sourceAttr == null) {
            listToSort = (List) findValue("top");
        } else {
            listToSort = (List) findValue(sourceAttr);
        }

        Comparator c = (Comparator) findValue(comparatorAttr);

        Collections.sort(listToSort, c);

        // push sorted list into stack, so nexted tag have access to it
    	getStack().push(listToSort);
        if (getId() != null && getId().length() > 0) {
        	pageContext.setAttribute(getId(), listToSort);
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
    	int returnVal =  super.doEndTag();

   		// pop sorted list from stack at the end of tag
   		getStack().pop();

    	return returnVal;
    }
}
