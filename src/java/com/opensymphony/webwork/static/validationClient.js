/**
 *
 * Common code to interface with the validationServlet 
 *
 */
function xmlEncode(s) {
	return s;
}

function ValidationClient(servletUrl) {

	this.servletUrl = servletUrl;

	makeValidateXml = function(form, nameSpace, actionName) {
	    var xml = '<r a="' + xmlEncode(actionName) + '" ns="' + xmlEncode(nameSpace) +'">\n';
	    for (var i = 0; i < form.elements.length; i++) {
	        var e = form.elements[i];
			// TODO get the value of the input dependant on the type of the input
	        xml = xml + '<p n="' + xmlEncode(e.name) + '">' + xmlEncode(e.value) + '</p>\n';
	    }
	    xml = xml + '</r>\n';
	    return xml;
	}

	extractErrorMessages = function(node) {
        var errorMessageXml = node.getElementsByTagName("errorMessage");
        var ary = new Array();
        for (var i = 0; i < errorMessageXml.length; i++) {

			// why oh why do firefox and IE have different ways to access text content of xml nodes 
        	if (errorMessageXml[i].text) {
	        	ary[i] = errorMessageXml[i].text;
	        }
	        else {
	        	ary[i] = errorMessageXml[i].textContent;
	        }
        }
        return ary;
	}


	
	this.validate = function(input, nameSpace, actionName) {
		var vc = this;
		dojo.io.bind({
			mimetype: "text/xml",
			url: this.servletUrl,
			method: "post",
			postContent: makeValidateXml(input.form, nameSpace, actionName),
			load: function(type, data, evt) { 

		        var root = data.getElementsByTagName("errors")[0];

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
		        vc.onErrors(input, errors);
        	},
        	error : function(a,b) {
	        }
		});
    }
    

	// @param formObject - the form object that triggered the validate call
	// @param errors - a javascript object representing the action errors and field errors
	// client should overwrite this handler to display the new error messages
	this.onErrors = function(inputObject, errors) {
	}
	
	return this;

}
