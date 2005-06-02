dojo.hostenv.startPackage("webwork.widgets.RemoteButton");

dojo.hostenv.loadModule("dojo.io.*");

dojo.hostenv.loadModule("dojo.event.*");

dojo.hostenv.loadModule("dojo.xml.Parse");
dojo.hostenv.loadModule("dojo.webui.widgets.Parse");
dojo.hostenv.loadModule("dojo.webui.Widget");
dojo.hostenv.loadModule("dojo.webui.DomWidget");
dojo.hostenv.loadModule("dojo.webui.WidgetManager");

dojo.hostenv.loadModule("webwork.Util");
dojo.hostenv.loadModule("webwork.widgets.BindWidget");

/*
 * Component to do remote updating of a DOM tree.
 */

webwork.widgets.HTMLRemoteButton = function() {
	
	// is this needed as well as the dj_inherits calls below
	// this coppied from slideshow
	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);
	webwork.widgets.BindWidget.call(this);

	// closure trickery
	var _this = this;

	this.templatePath = "webwork/widgets/RemoteButton.html";

	this.isContainer = false;
	this.widgetType = "RemoteButton";
	
	// the type of the input button - can be image
	this.type = "submit";

	// the template button instance
	this.button = null;
	
	this.fillInTemplate = function() {
		_this.init();
		webwork.Util.passThroughArgs(_this.extraArgs, _this.button);
		_this.button.type = _this.type;
    }

}

// is this needed as well as dojo.webui.Widget.call(this);
dj_inherits(webwork.widgets.HTMLRemoteButton, webwork.widgets.BindWidget);
dojo.webui.widgets.tags.addParseTreeHandler("dojo:Remotebutton");

// TODO move this into a package include
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
