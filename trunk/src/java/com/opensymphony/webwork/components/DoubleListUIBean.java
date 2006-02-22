package com.opensymphony.webwork.components;

import java.util.Map;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DoubleListUIBean is the standard superclass of all webwork double list handling components.
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @author tm_jee
 * @version $Revision$
 * @since 2.2
 */
public abstract class DoubleListUIBean extends ListUIBean {
	
	protected String emptyOption;
    protected String headerKey;
    protected String headerValue;
    protected String multiple;
    protected String size;
	
    protected String doubleList;
    protected String doubleListKey;
    protected String doubleListValue;
    protected String doubleName;
    protected String doubleValue;
    protected String formName;
    
    protected String doubleId;
    protected String doubleDisabled;
    protected String doubleMultiple;
    protected String doubleSize;
    protected String doubleHeaderKey;
    protected String doubleHeaderValue;
    protected String doubleEmptyOption;
    
    protected String doubleCssClass;
    protected String doubleCssStyle;
    
    protected String doubleOnclick;
    protected String doubleOndblclick;
    protected String doubleOnmousedown;
    protected String doubleOnmouseup;
    protected String doubleOnmouseover;
    protected String doubleOnmousemove;
    protected String doubleOnmouseout;
    protected String doubleOnfocus;
    protected String doubleOnblur;
    protected String doubleOnkeypress;
    protected String doubleOnkeydown;
    protected String doubleOnkeyup;
    protected String doubleOnselect;
    protected String doubleOnchange;
    

    public DoubleListUIBean(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        //Object doubleName = null;
        
        if (emptyOption != null) {
            addParameter("emptyOption", findValue(emptyOption, Boolean.class));
        }

        if (multiple != null) {
            addParameter("multiple", findValue(multiple, Boolean.class));
        }

        if (size != null) {
            addParameter("size", findString(size));
        }

        if ((headerKey != null) && (headerValue != null)) {
            addParameter("headerKey", findString(headerKey));
            addParameter("headerValue", findString(headerValue));
        }
        
        
        if (doubleMultiple != null) {
        	addParameter("doubleMultiple", findValue(doubleMultiple, Boolean.class));
        }
        
        if (doubleSize != null) {
        	addParameter("doubleSize", findString(doubleSize));
        }
        
        if (doubleDisabled != null) {
        	addParameter("doubleDisbled", findValue(doubleDisabled, Boolean.class));
        }

        if (doubleName != null) {
            addParameter("doubleName", findString(this.doubleName));
        }

        if (doubleList != null) {
            addParameter("doubleList", doubleList);
        }
        
        Object tmpDoubleList = findValue(doubleList);
        if (doubleListKey != null) {
            addParameter("doubleListKey", doubleListKey);
        }else if (tmpDoubleList instanceof Map) {
        	addParameter("doubleListKey", "key");
        }
        
        if (doubleListValue != null) {
            addParameter("doubleListValue", doubleListValue);
        }else if (tmpDoubleList instanceof Map) {
        	addParameter("doubleListValue", "value");
        }


        if (formName != null) {
            addParameter("formName", findString(formName));
        } else {
            // ok, let's look it up
            Component form = findAncestor(Form.class);
            if (form != null) {
                addParameter("formName", form.getParameters().get("name"));
            }
        }

        Class valueClazz = getValueClassType();

        if (valueClazz != null) {
            if (doubleValue != null) {
                addParameter("doubleNameValue", findValue(doubleValue, valueClazz));
            } else if (doubleName != null) {
                addParameter("doubleNameValue", findValue(doubleName.toString(), valueClazz));
            }
        } else {
            if (doubleValue != null) {
                addParameter("doubleNameValue", findValue(doubleValue));
            } else if (doubleName != null) {
                addParameter("doubleNameValue", findValue(doubleName.toString()));
            }
        }
        
        Form form = (Form) findAncestor(Form.class);
        if (doubleId != null) {
            // this check is needed for backwards compatibility with 2.1.x
            if (altSyntax()) {
                addParameter("doubleId", findString(doubleId));
            } else {
                addParameter("doubleId", doubleId);
            }
        } else if (form != null) {
            addParameter("doubleId", form.getParameters().get("id") + "_" +escape(this.doubleName));
        }
        
        if (doubleOnclick != null) {
        	addParameter("doubleOnclick", findString(doubleOnclick));
        }
        
        if (doubleOndblclick != null) {
        	addParameter("doubleOndblclick", findString(doubleOndblclick));
        }
        
        if (doubleOnmousedown != null) {
        	addParameter("doubleOnmousedown", findString(doubleOnmousedown));
        }
        
        if (doubleOnmouseup != null) {
        	addParameter("doubleOnmouseup", findString(doubleOnmouseup));
        }
        
        if (doubleOnmouseover != null) {
        	addParameter("doubleOnmouseover", findString(doubleOnmouseover));
        }
        
        if (doubleOnmousemove != null) {
        	addParameter("doubleOnmousemove", findString(doubleOnmousemove));
        }
        
        if (doubleOnmouseout != null) {
        	addParameter("doubleOnmouseout", findString(doubleOnmouseout));
        }
        
        if (doubleOnfocus != null) {
        	addParameter("doubleOnfocus", findString(doubleOnfocus));
        }
        
        if (doubleOnblur != null) {
        	addParameter("doubleOnblur", findString(doubleOnblur));
        }
        
        if (doubleOnkeypress != null) {
        	addParameter("doubleOnkeypress", findString(doubleOnkeypress));
        }
        
        if (doubleOnkeydown != null) {
        	addParameter("doubleOnkeydown", findString(doubleOnkeydown));
        }
        
        if (doubleOnselect != null) {
        	addParameter("doubleOnselect", findString(doubleOnselect));
        }
        
        if (doubleOnchange != null) {
        	addParameter("doubleOnchange", findString(doubleOnchange));
        }
        
        if (doubleCssClass != null) {
        	addParameter("doubleCss", findString(doubleCssClass));
        }
        
        if (doubleCssStyle != null) {
        	addParameter("doubleStyle", findString(doubleCssStyle));
        }
        
        if (doubleHeaderKey != null && doubleHeaderValue != null) {
        	addParameter("doubleHeaderKey", findString(doubleHeaderKey));
        	addParameter("doubleHeaderValue", findString(doubleHeaderValue));
        }
        
        if (doubleEmptyOption != null) {
        	addParameter("doubleEmptyOption", findValue(doubleEmptyOption, Boolean.class));
        }
    }

    /**
     * @ww.tagattribute required="true"
     * description="The second iterable source to populate from."
     */
    public void setDoubleList(String doubleList) {
        this.doubleList = doubleList;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The key expression to use for second list"
     */
    public void setDoubleListKey(String doubleListKey) {
        this.doubleListKey = doubleListKey;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The value expression to use for second list"
     */
    public void setDoubleListValue(String doubleListValue) {
        this.doubleListValue = doubleListValue;
    }

    /**
     * @ww.tagattribute required="true"
     * description="The name for complete component"
     */
    public void setDoubleName(String doubleName) {
        this.doubleName = doubleName;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The value expression for complete component"
     */
    public void setDoubleValue(String doubleValue) {
        this.doubleValue = doubleValue;
    }

    /**
     * @ww.tagattribute required="false"
     * description="The form name this component resides in and populates to"
     */
    public void setFormName(String formName) {
        this.formName = formName;
    }
    
    public String getFormName() {
    	return formName;
    }
    
    /**
     * @ww.tagattribute required="false"
     * description="The css class for the second list"
     */
    public void setDoubleCssClass(String doubleCssClass) {
    	this.doubleCssClass = doubleCssClass;
    }
    
    public String getDoubleCssClass() {
    	return doubleCssClass;
    }
    
    /**
     * @ww.tagattribute required="false"
     * description="The css style for the second list"
     */
    public void setDoubleCssStyle(String doubleCssStyle) {
    	this.doubleCssStyle = doubleCssStyle;
    }
    
    public String getDoubleCssStyle() {
    	return doubleCssStyle;
    }
    
    /**
     * @ww.tagattribute required="false"
     * description="The header key for the second list"
     */
    public void setDoubleHeaderKey(String doubleHeaderKey) {
    	this.doubleHeaderKey = doubleHeaderKey;
    }
    
    public String getDoubleHeaderKey() {
    	return doubleHeaderKey;
    }
    
    /**
     * @ww.tagattribute required="false"
     * description="The header value for the second list"
     */
    public void setDoubleHeaderValue(String doubleHeaderValue) {
    	this.doubleHeaderValue = doubleHeaderValue;
    }
    
    public String getDoubleHeaderValue() {
    	return doubleHeaderValue;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Decides if the second list will add an empty option"
     */
    public void setDoubleEmptyOption(String doubleEmptyOption) {
    	this.doubleEmptyOption = doubleEmptyOption;
    }
    
    public String getDoubleEmptyOption() {
    	return this.doubleEmptyOption;
    }

    
	public String getDoubleDisabled() {
		return doubleDisabled;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Decides if a disable attribute should be added to the second list"
     */
	public void setDoubleDisabled(String doubleDisabled) {
		this.doubleDisabled = doubleDisabled;
	}

	public String getDoubleId() {
		return doubleId;
	}

	/**
     * @ww.tagattribute required="false"
     * description="The id of the second list"
     */
	public void setDoubleId(String doubleId) {
		this.doubleId = doubleId;
	}

	public String getDoubleMultiple() {
		return doubleMultiple;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Decides if multiple attribute should be set on the second list"
     */
	public void setDoubleMultiple(String doubleMultiple) {
		this.doubleMultiple = doubleMultiple;
	}

	public String getDoubleOnblur() {
		return doubleOnblur;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onblur attribute of the second list"
     */
	public void setDoubleOnblur(String doubleOnblur) {
		this.doubleOnblur = doubleOnblur;
	}

	public String getDoubleOnchange() {
		return doubleOnchange;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onchange attribute of the second list"
     */
	public void setDoubleOnchange(String doubleOnchange) {
		this.doubleOnchange = doubleOnchange;
	}

	public String getDoubleOnclick() {
		return doubleOnclick;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onclick attribute of the second list"
     */
	public void setDoubleOnclick(String doubleOnclick) {
		this.doubleOnclick = doubleOnclick;
	}

	public String getDoubleOndblclick() {
		return doubleOndblclick;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the ondbclick attribute of the second list"
     */
	public void setDoubleOndblclick(String doubleOndblclick) {
		this.doubleOndblclick = doubleOndblclick;
	}

	public String getDoubleOnfocus() {
		return doubleOnfocus;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onfocus attribute of the second list"
     */
	public void setDoubleOnfocus(String doubleOnfocus) {
		this.doubleOnfocus = doubleOnfocus;
	}

	public String getDoubleOnkeydown() {
		return doubleOnkeydown;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onkeydown attribute of the second list"
     */
	public void setDoubleOnkeydown(String doubleOnkeydown) {
		this.doubleOnkeydown = doubleOnkeydown;
	}

	public String getDoubleOnkeypress() {
		return doubleOnkeypress;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onkeypress attribute of the second list"
     */
	public void setDoubleOnkeypress(String doubleOnkeypress) {
		this.doubleOnkeypress = doubleOnkeypress;
	}

	public String getDoubleOnkeyup() {
		return doubleOnkeyup;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onkeyup attribute of the second list"
     */
	public void setDoubleOnkeyup(String doubleOnkeyup) {
		this.doubleOnkeyup = doubleOnkeyup;
	}

	public String getDoubleOnmousedown() {
		return doubleOnmousedown;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onmousedown attribute of the second list"
     */
	public void setDoubleOnmousedown(String doubleOnmousedown) {
		this.doubleOnmousedown = doubleOnmousedown;
	}

	public String getDoubleOnmousemove() {
		return doubleOnmousemove;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onmousemove attribute of the second list"
     */
	public void setDoubleOnmousemove(String doubleOnmousemove) {
		this.doubleOnmousemove = doubleOnmousemove;
	}

	public String getDoubleOnmouseout() {
		return doubleOnmouseout;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onmouseout attribute of the second list"
     */
	public void setDoubleOnmouseout(String doubleOnmouseout) {
		this.doubleOnmouseout = doubleOnmouseout;
	}

	public String getDoubleOnmouseover() {
		return doubleOnmouseover;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onmouseover attribute of the second list"
     */
	public void setDoubleOnmouseover(String doubleOnmouseover) {
		this.doubleOnmouseover = doubleOnmouseover;
	}

	public String getDoubleOnmouseup() {
		return doubleOnmouseup;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onmouseup attribute of the second list"
     */
	public void setDoubleOnmouseup(String doubleOnmouseup) {
		this.doubleOnmouseup = doubleOnmouseup;
	}

	public String getDoubleOnselect() {
		return doubleOnselect;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the onselect attribute of the second list"
     */
	public void setDoubleOnselect(String doubleOnselect) {
		this.doubleOnselect = doubleOnselect;
	}

	public String getDoubleSize() {
		return doubleSize;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the size attribute of the second list"
     */
	public void setDoubleSize(String doubleSize) {
		this.doubleSize = doubleSize;
	}

	public String getDoubleList() {
		return doubleList;
	}

	/**
     * @ww.tagattribute required="false"
     * description="Set the list key of the second attribute"
     */
	public String getDoubleListKey() {
		return doubleListKey;
	}

	public String getDoubleListValue() {
		return doubleListValue;
	}

	public String getDoubleName() {
		return doubleName;
	}

	public String getDoubleValue() {
		return doubleValue;
	}
	
	/**
     * @ww.tagattribute required="false" default="false" type="Boolean"
     * description="Decides of an empty option is to be inserted in the second list"
     */
    public void setEmptyOption(String emptyOption) {
        this.emptyOption = emptyOption;
    }

    /**
     * Cannot be empty! "'-1'" and "''" is correct, "" is bad.
     * @ww.tagattribute required="false"
     * description="Set the header key of the second list"
     */
    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set the header value of the second list"
     */
    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    /**
     * TODO: Passing a Collection may work too?
     * @ww.tagattribute required="false"
     * description="Creates a multiple select. The tag will pre-select multiple values if the values are passed as an Array (of appropriate types) via the value attribute."
     */
    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    /**
     * @ww.tagattribute required="false" type="Integer"
     * description="Size of the element box (# of elements to show)"
     */
    public void setSize(String size) {
        this.size = size;
    }
}
