
/**
 *      Methods for the tabbed component
 */
var unselectedClass = "tab_default tab_unselected";
var unselectedContentsClass = "tab_contents_hidden";
var unselectedOverClass = "tab_default tab_unselected tab_unselected_over";
var selectedClass = "tab_default tab_selected";
var selectedContentsClass = "tab_contents_header";

 function mouseIn(tab) {
    var className = tab.className;
    if (className.indexOf('unselected') > -1) {
        className = unselectedOverClass;
        tab.className = className;
    }
 }

 function mouseOut(tab) {
    var className = tab.className;
    if (className.indexOf('unselected') > -1) {
        className = unselectedClass;
        tab.className = className;
    }
 }

/*
 * An object that represents a tabbed page.
 *
 * @param htmlId the id of the element that represents the tab page
 */
function TabContent( htmlId ) {

    this.elementId = htmlId;
    var selected = false;
    var self = this;

    /*
     * Shows or hides this page depending on whether the visible
     * tab id matches this objects id.
     *
     * @param visibleTabId the id of the tab that was selected
     */
    this.updateVisibility = function( visibleTabId ) {
        var thElement = document.getElementById( 'tab_header_'+self.elementId );
        var tcElement = document.getElementById( 'tab_contents_'+self.elementId );
        if (!selected && visibleTabId==self.elementId) {
            thElement.className = selectedClass;
            tcElement.className = selectedContentsClass;
            self.selected = true;

        } else {
            thElement.className = unselectedClass;
            tcElement.className = unselectedContentsClass;
            self.selected = false;
        }
    }

}

/**  end tabbed component functions ******************************************************************/
