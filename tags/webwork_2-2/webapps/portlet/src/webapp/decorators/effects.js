// Provide hover and click effect to entire table rows.
// (removed click effect - it was annoying - mike 1/10/03)
// Usage:
// <table class="grid">
//   <tr href="somelink.jsp" onmouseover="rowHover(this)">
//   ...
function rowHover(row) {
	if (!row.href && row.getAttribute) row.href = row.getAttribute("href");
	if (!row.href) return;
	row.oldClassName = row.className;
	row.className = 'gridHover';
	row.onmouseout = function() {
		this.className = this.oldClassName;
	}
//	row.onclick = function() {
//		document.location.href = this.href;
//  }
}

function placeFocus() {
	var stopNow=false;
	for (var i=0; i < document.forms.length; i++) {
		var currSet=document.forms[i].elements;
		if (document.forms[i].name!='searchForm' && document.forms[i].name!='inlinecommentform') {
			for (var j = 0; j < currSet.length; j++) {
				if (currSet[j].type=='text' || currSet[j].type=='password' || currSet[j].type=='textarea'){
					currSet[j].focus();
					stopNow=true;
					break;
				}
			}
		}
		if (stopNow)
			break;
	}
}

function checkAllCheckBoxes(field)
{
    for (i = 0; i < field.length; i++)
        field[i].checked = true ;
}

function clearAllCheckBoxes(field)
{
    for (i = 0; i < field.length; i++)
        field[i].checked = false ;
}

function openUserPickerWindow(formName, element)
{
    var vWinUsers = window.open('openuserpicker.action?key=$key&formName=' + formName + '&elementName=' + element + '&multiSelect=true&startIndex=0&usersPerPage=10', 'UserPicker2', 'status=yes,resizable=yes,top=100,left=200,width=580,height=550,scrollbars=yes');
    vWinUsers.opener = self;
    vWinUsers.focus();
}
