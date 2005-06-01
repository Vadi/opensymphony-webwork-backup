dojo.hostenv.startPackage("webwork.widgets.RemoteSubmitButton");
dojo.hostenv.startPackage("webwork.widgets.HTMLRemoteSubmitButton");

dojo.hostenv.loadModule("dojo.io.*");

dojo.hostenv.loadModule("dojo.event.*");

dojo.hostenv.loadModule("dojo.xml.Parse");
dojo.hostenv.loadModule("dojo.webui.widgets.Parse");
dojo.hostenv.loadModule("dojo.webui.Widget");
dojo.hostenv.loadModule("dojo.webui.DomWidget");
dojo.hostenv.loadModule("dojo.webui.WidgetManager");

dojo.hostenv.loadModule("webwork.Util");

/*
 * Component to do remote updating of a DOM tree.
 */

webwork.widgets.HTMLRemoteSubmitButton = function() {
	
	// is this needed as well as the dj_inherits calls below
	// this coppied from slideshow
	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);

	// closure trickery
	var _this = this;

	this.templatePath = "webwork/widgets/RemoteSubmitButton.html";

	this.isContainer = false;
	this.widgetType = "RemoteSubmitButton";

	this.formId = "";
	this.onLoad = "";
	this.type = "submit";

	this.button = null;
	
	this.fillInTemplate = function() {
		webwork.Util.passThroughArgs(_this, _this.button);
		_this.button.type = _this.type;
    }
    
    this.submitForm = function() {
		dojo.io.bind({
		    load: _this.load,
	    	formNode: document.getElementById(_this.formId)
		});
    }
    
    // allow this to be overriden by subclasses - say one that renders the response into a div
    this.load = function(type, data) {
    
    	// by default, eval the onLoad handler
		if (_this.onLoad != "") {
		 	eval(_this.onLoad);
		}
    }

}

// is this needed as well as dojo.webui.Widget.call(this);
dj_inherits(webwork.widgets.HTMLRemoteSubmitButton, dojo.webui.DomWidget);

// allow the markup parser to construct these widgets
dojo.webui.widgets.tags.addParseTreeHandler("dojo:RemoteSubmitButton");

// register this widget constructor with the widget manager
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
