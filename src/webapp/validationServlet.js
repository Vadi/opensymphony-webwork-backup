/**
 *
 * Common code to interface with the validationServlet 
 *
 */

function ValidationServlet(servletUrl) {

	this.servletUrl = servletUrl;
	this.xmlhttp=null;

	// These are in order of decreasing likelihood; this will change in time.
	XMLHTTP_PROGIDS = ['Msxml2.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.4.0'];

	newXMLHttp = function() {

		var xmlhttp;
		if(typeof XMLHttpRequest !== 'undefined') {
			xmlhttp = new XMLHttpRequest();
		}
		else if(typeof ActiveXObject !== 'undefined') {
			var last_e = null;
		
			for(var i=0; i<XMLHTTP_PROGIDS.length; ++i) {
				var progid = XMLHTTP_PROGIDS[i];
				try {
					xmlhttp = new ActiveXObject(progid);
				}
				catch(e) {
					last_e = e;
				}
				if(this.xmlhttp) {
					XMLHTTP_PROGIDS = [progid];  // so faster next time
					break;
				}
			}
		}
		return xmlhttp;
	}
	
	this.getValidateXml = function(form, nameSpace, actionName) {
	    var xml = '<r a="' + actionName + '" ns="' + nameSpace +'">\n';
	    for (var i = 0; i < form.elements.length; i++) {
	        var e = form.elements[i];
			// TODO get the value of the input dependant on the type of the input
			// TODO xml escape
	        xml = xml + '<p n="' + e.name + '">' + e.value + '</p>\n';
	    }
	    xml = xml + '</r>\n';
	    return xml;
	}

	this.validate = function(input, nameSpace, actionName) {
		var xmlhttp = newXMLHttp();
		if (xmlhttp == null) {
			return null;
		}

		var sv = this;
		
	    xmlhttp.open("POST", this.servletUrl, true);
    	xmlhttp.onreadystatechange = function() {
	        if (xmlhttp.readyState == 4) {
	        	if (xmlhttp.responseXML) {
		            sv.handleXmlResponse(input, xmlhttp.responseXML);
				}
	        }
	    }
	    
	    var xml = this.getValidateXml(input.form, nameSpace, actionName);
	    xmlhttp.send(xml);
    }
    
	extractErrorMessages = function(node) {
        var actionErrorMessagesXml = node.getElementsByTagName("errorMessage");
        var ary = new Array();
        for (var i = 0; i < actionErrorMessagesXml.length; i++) {
        	ary[i] = actionErrorMessagesXml[i].textContent;
        }
        return ary;
	}
	
	this.handleXmlResponse = function(input, xml) {

        var root = xml.childNodes[0];

        // build a javascript object to hold the errors
        var errors = new Object();

		// add the actionErrors
        errors.actionErrors = new Array();

		errors.actionErrors = extractErrorMessages(root.getElementsByTagName("actionErrors")[0]);

		// add the fieldErrors
		errors.fieldErrors = new Object();
		var fieldErrorsXml = root.getElementsByTagName("fieldErrors");
        for (var i = 0; i < fieldErrorsXml.length; i++) {
        	var fieldName = fieldErrorsXml[i].getAttribute("fieldName");
            errors.fieldErrors[fieldName] = extractErrorMessages(fieldErrorsXml[i]);
		}

        
        // make the callback with the errors
        this.onErrors(input, errors);
    }

	// default implementation delegates to individual on??Error handlers
	// @param validateElement - the form element that triggered the validate(element) call
	// @param errors - a javascript object representing the action errors and field errors
	this.onErrors = function(validateElement, errors) {
		this.onActionErrors(validateElement, errors);
		for (fe in errors.fieldErrors) {
			this.onFieldError(validateElement, fe, errors.fieldErrors[fe]);
		}
	}

	// default implementation calls onActionError for each error message
	// @param validateElement - the form element that triggered the validate(element) call
	// @param errorMessages - an array of string messages
	this.onActionErrors = function(validateElement, errorMessages) {
		for (i = 0; i < errorMessages.length; i++) {
			this.onActionError(validateElement, errorMessages[i]);
		}
	}

	// default implementation does nothing
	// @param validateElement - the form element that triggered the validate(element) call
	// @param errorMessages
	this.onActionError = function(validateElement, errorMessage) {
	}

	// default implementation does nothing
	// @param validateElement - the form element that triggered the validate(element) call
	// @param fieldName - the name of the field that the errorMessage belongs to
	// @errorMessages - an array of string error messages
	this.onFieldError = function(validateElement, fieldName, errorMessages) {
	}
	
	return this;

}
