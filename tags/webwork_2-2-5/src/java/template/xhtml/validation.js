/*
 * <!-- START SNIPPET: ajaxValidation -->
 */
 
/*
 * This function is copied over from Dojo, such that 'xhtml' theme doesn't depends
 * on Dojo.
 */
function previousElement(/* Node */ node, /*string? */ tagName) { 
	//	summary:
	//		returns the previous sibling element matching tagName
	if(!node) { return null; }
	if(tagName) { tagName = tagName.toLowerCase(); }
	do {
		node = node.previousSibling;
	} while(node && node.nodeType != 1 /* ELEMENT_NODE */);

	if(node && tagName && tagName.toLowerCase() != node.tagName.toLowerCase()) {
		return previousElement(node, tagName);
	}
	return node;	//	Element
} 
 
function clearErrorMessages(form) {
    var table = form.childNodes[1];
    if( typeof table == "undefined" ) {
        table = form.childNodes[0];
    }

    // clear out any rows with an "errorFor" attribute
    var rows = table.rows;
    var rowsToDelete = new Array();
    if (rows == null){
        return;
    }

    for(var i = 0; i < rows.length; i++) {
        var r = rows[i];
        if (r.getAttribute("errorFor")) {
            rowsToDelete.push(r);
        }
    }

    // now delete the rows
    for (var i = 0; i < rowsToDelete.length; i++) {
        var r = rowsToDelete[i];
        table.deleteRow(r.rowIndex);
    }
}

function clearErrorLabels(form) {
    // set all labels back to the normal class
    var elements = form.elements;
    for (var i = 0; i < elements.length; i++) {
        var e = elements[i];
        
        var label;
        var cells = e.parentNode.parentNode.cells;
        if (cells && cells.length >= 2) {  // when labelposition='left'
        	label = cells[0].getElementsByTagName("label")[0];
        }
        else if (cells && cells.length >=1) { // when labelposition='top'
        	if (e.parentNode.parentNode) {
        		if (previousElement(e.parentNode.parentNode)) {      
        			label = previousElement(e.parentNode.parentNode).getElementsByTagName("label")[0];
        		}
        	}
        }	
        
        if (label) {
        	label.setAttribute("class", "label");
        	label.setAttribute("className", "label"); //ie hack cause ie does not support setAttribute
        }
    }
}

function addError(e, errorText) {
    try {
        // clear out any rows with an "errorFor" of e.id
        
        var row = e.parentNode.parentNode;
        var table = row.parentNode;
        var error = document.createTextNode(errorText);
        var tr = document.createElement("tr");
        var td = document.createElement("td");
        var span = document.createElement("span");
        td.align = "center";
        td.valign = "top";
        span.setAttribute("class", "errorMessage");
        span.setAttribute("className", "errorMessage"); //ie hack cause ie does not support setAttribute
        span.appendChild(error);
        td.appendChild(span);
        tr.appendChild(td);
        tr.setAttribute("errorFor", e.id);;

        // updat the label too
        var label;
        var cells = e.parentNode.parentNode.cells;
        if (cells && cells.length >= 2) { // when labelposition='left'
        	label = cells[0].getElementsByTagName("label")[0];
        	 td.colSpan = 2;
        }
        else {
        	if (previousElement(row)) { // when labelposition='top'
        		label = previousElement(row).getElementsByTagName("label")[0];
        	}
        }	
        
        if (label) {
        	label.setAttribute("class", "errorLabel");
        	label.setAttribute("className", "errorLabel"); //ie hack cause ie does not support setAttribute
        }
        
        table.insertBefore(tr, row);
    } catch (e) {
        alert(e);
    }
}

/*
 * <!-- END SNIPPET: ajaxValidation -->
 */