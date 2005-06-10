dojo.hostenv.startPackage("webwork.widgets.BindButton");

dojo.hostenv.loadModule("dojo.io.*");

dojo.hostenv.loadModule("dojo.event.*");

dojo.hostenv.loadModule("dojo.xml.Parse");
dojo.hostenv.loadModule("dojo.webui.widgets.Parse");
dojo.hostenv.loadModule("dojo.webui.Widget");
dojo.hostenv.loadModule("dojo.webui.DomWidget");
dojo.hostenv.loadModule("dojo.webui.WidgetManager");

dojo.hostenv.loadModule("webwork.Util");
dojo.hostenv.loadModule("webwork.widgets.HTMLBind");

/*
 * Component to do remote updating of a DOM tree.
 */

webwork.widgets.HTMLBindButton = function() {
	
	// is this needed as well as the dj_inherits calls below
	// this coppied from slideshow
	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);
	webwork.widgets.Bind.call(this);

	var self = this;

	this.templatePath = "webwork/widgets/BindButton.html";

	this.isContainer = false;
	this.widgetType = "BindButton";
	
	// the type of the input button - can be image
	this.type = "submit";

	// the template button instance
	this.button = null;

	var super_fillInTemplate = this.fillInTemplate;
	this.fillInTemplate = function() {
		super_fillInTemplate();
		webwork.Util.passThroughArgs(self.extraArgs, self.button);
		self.button.type = self.type;
    }

}

// is this needed as well as dojo.webui.Widget.call(this);
dj_inherits(webwork.widgets.HTMLBindButton, webwork.widgets.HTMLBind);
dojo.webui.widgets.tags.addParseTreeHandler("dojo:BindButton");

// TODO needs to be placed into a package include
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
