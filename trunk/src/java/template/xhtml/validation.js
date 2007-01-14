/*
 * <!-- START SNIPPET: ajaxValidation -->
 */

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
        //table.removeChild(rowsToDelete[i]); 
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
        else { // when labelposition='top'
        	if (e.parentNode.parentNode) {
				if (dojo.dom.prevElement(e.parentNode.parentNode)) {       		
        			label = dojo.dom.prevElement(e.parentNode.parentNode).getElementsByTagName("label")[0];
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
        	if (dojo.dom.prevElement(row)) { // when labelposition='top'
        		label = dojo.dom.prevElement(row).getElementsByTagName("label")[0];
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