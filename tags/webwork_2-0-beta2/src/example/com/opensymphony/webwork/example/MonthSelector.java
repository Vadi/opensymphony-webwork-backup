package com.opensymphony.webwork.example;

import com.opensymphony.xwork.ActionSupport;

/**
 *	Month selector
 *      
 *	@see <related>
 *	@author Rickard Öberg (<email>)
 *	@version $Revision$
 */
public class MonthSelector
        extends ActionSupport {
    // Attributes ----------------------------------------------------
    int month = -1;

    // Public --------------------------------------------------------
    public void setMonth(int aMonth) {
        this.month = aMonth;
    }

    public int getMonth() {
        return month;
    }
}
