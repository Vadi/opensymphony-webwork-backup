dojo.hostenv.startPackage("webwork.widgets.RemoteSubmitButton");

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

	// the id of the form object to bind to
	this.formId = "";

	// the name of the global javascript variable to associate with this widget instance
	this.id = "";
	
	// javascript code to be evaled when data arrives - arguments are (eventType, data)
	this.onLoad = "";
	
	// the type of the input button - can be image
	this.type = "submit";

	// the template button instance
	this.button = null;
	
	this.fillInTemplate = function() {
		webwork.Util.passThroughArgs(_this.extraArgs, _this.button);
		_this.button.type = _this.type;
		if (_this.id != "") {
			window[_this.id] = this;
		}
    }
    
    this.submitForm = function() {
		dojo.io.bind({
		    load: _this.load,
	    	formNode: document.getElementById(_this.formId),
			useCache: false
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
webwork.widgets.HTMLRemoteSubmitButtonAndDiv = function() {

	webwork.widgets.HTMLRemoteSubmitButton.call(this);

	this.widgetType = 'RemoteSubmitButtonAndDiv';

	this.divId = "";
	
	var _this = this;
	
    // allow this to be overriden by subclasses - say one that renders the response into a div
    this.load = function(type, data) {
	
    	// by default, eval the onLoad handler
		if (_this.divId == "") {
			dj_debug('no divId specified');
		}else{
			var d = document.getElementById(_this.divId);
			if (d) {
				d.innerHTML = data;
			}else{
				dj_debug('div ' + this._divId + ' not found');
			}
		}

    }

}

// is this needed as well as dojo.webui.Widget.call(this);
dj_inherits(webwork.widgets.HTMLRemoteSubmitButton, dojo.webui.DomWidget);
dj_inherits(webwork.widgets.HTMLRemoteSubmitButtonAndDiv, webwork.widgets.HTMLRemoteSubmitButton);

// allow the markup parser to construct these widgets
dojo.webui.widgets.tags.addParseTreeHandler("dojo:RemoteSubmitButton");
dojo.webui.widgets.tags.addParseTreeHandler("dojo:RemoteSubmitButtonAndDiv");

// register this widget constructor with the widget manager
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
