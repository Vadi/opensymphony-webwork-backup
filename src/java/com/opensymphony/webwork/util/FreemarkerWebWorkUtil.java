/*
 * Created on 19/04/2004
 */
package com.opensymphony.webwork.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * @author CameronBraid
 */
public class FreemarkerWebWorkUtil extends WebWorkUtil {

    public FreemarkerWebWorkUtil(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }
    
    /**
     * 
     * the selectedList objects are matched to the list.listValue 
     * 
     * listKey and listValue are optional, and if not provided, the list item is used
     * 
     * @param selectedList the name of the action property 
     * 			that contains the list of selected items
     * 			or single item if its not an array or list
     * @param list the name of the action property 
     * 			that contains the list of selectable items
     * @param listKey an ognl expression that is exaluated relative to the list item 
     * 			to use as the key of the ListEntry 
     * @param listValue an ognl expression that is exaluated relative to the list item 
     * 			to use as the value of the ListEntry
     * @return a List of ListEntry
     */
    public List makeSelectList(String selectedList, String list, String listKey, String listValue) {

        List selectList = new ArrayList();
        
        Collection selectedItems = null;
        
        Object i = stack.findValue(selectedList);
        if (i != null) {
            if (i.getClass().isArray()) {
                selectedItems = Arrays.asList((Object[])i);
            } else if (i instanceof Collection) {
                selectedItems = (Collection)i;
            } else {
                // treat it is a single item
                selectedItems = new ArrayList();
                selectedItems.add(i);
            }
        }
        Collection items = (Collection) stack.findValue(list);

        if (items != null) {
            for (Iterator iter = items.iterator(); iter.hasNext();) {
                Object element = (Object) iter.next();
                Object key = null;

                if ((listKey == null) || (listKey.length() == 0)) {
                    key = element;
                } else {
                    key = ognl.findValue(listKey, element);
                }

                Object value = null;

                if ((listValue == null) || (listValue.length() == 0)) {
                    value = element;
                } else {
                    value = ognl.findValue(listValue, element);
                }
                
                boolean isSelected = false;
                if (value != null && selectedItems != null && selectedItems.contains(value)) {
                    isSelected = true;
                }

                selectList.add(new ListEntry(key, value, isSelected));
            }
        }

        return selectList;
    }
    
    public Object findValue(String expression, String className) throws ClassNotFoundException {
        return stack.findValue(expression, Class.forName(className));
    }
    
}
