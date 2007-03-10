/*
 * <!-- START SNIPPET: ajaxValidation -->
 *
 * An "Ajax" theme implementation of ValidationClient, it provides function 
 * hook that client (eg. other theme like xhtml or css_html should implement
 * to customize handling of validation error to suit their needs, eg in an 
 * xhtml theme, it would maybe change the &lt;tr&gt; or &lt;td&gt; to show
 * or hide the validation messages etc.
 * 
 * The hooks are :-
 * <pre>
 * function  clearErrorMessage(form) {
 *    .....
 * }
 *
 * function clearErrorLabels(form) {
 *    ....
 * }
 *
 * function addError(fieldElement, fieldError) {
 *   ....
 * }
 * </pre>
 */
var webworkValidator = new ValidationClient("$!base/validation");
webworkValidator.onErrors = function(input, errors) {
	var form = input.form;

	clearErrorMessages(form);
	clearErrorLabels(form);

    if (errors.fieldErrors) {
        for (var fieldName in errors.fieldErrors) {
        	if (form.elements[fieldName]) {
            	if (form.elements[fieldName].touched) {
                	for (var i = 0; i < errors.fieldErrors[fieldName].length; i++) {
                    	addError(form.elements[fieldName], errors.fieldErrors[fieldName][i]);
                	}
            	}
            }
        }
    }
}

/*
 * Function that gets called when there's change on WebWork UI Components, 
 * eg. js onchange. It delegate the functionality of validating the changed 
 * components to an instance of ValidationClient (in this implementation 
 * webworkValidator).
 */
function validate(element) {
    // mark the element as touch
    element.touched = true;
    var namespace = element.form.attributes['namespace'].nodeValue;
    var actionName = element.form.attributes['name'].nodeValue;
	webworkValidator.validate(element, namespace, actionName);
}

/*
 *<!-- END SNIPPET: ajaxValidation -->
 */