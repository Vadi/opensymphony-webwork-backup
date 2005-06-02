dojo.hostenv.startPackage("webwork.widgets.BindAnchor");
dojo.hostenv.startPackage("webwork.widgets.HTMLBindAnchor");

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

webwork.widgets.HTMLBindAnchor = function() {
	
	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);
	webwork.widgets.Bind.call(this);

	var self = this;

	this.templatePath = "webwork/widgets/BindAnchor.html";

	this.isContainer = false;
	this.widgetType = "BindAnchor";

	// the template anchor instance
	this.anthor = null;

	var super_fillInTemplate = this.fillInTemplate;
	this.fillInTemplate = function(args, frag) {
		webwork.Util.passThroughArgs(self.extraArgs, self.anchor);
		self.anchor.href = "javascript:";

		dojo.event.kwConnect({
			srcObj: self.anchor,
			srcFunc: "onclick",
			adviceObj: self,
			adviceFunc: "bind"
		});
		
		webwork.Util.passThroughWidgetTagContent(self, frag, self.anchor);
		
    }

}

// is this needed as well as dojo.webui.Widget.call(this);
dj_inherits(webwork.widgets.HTMLBindAnchor, webwork.widgets.HTMLBind);
dojo.webui.widgets.tags.addParseTreeHandler("dojo:BindAnchor");

// TODO move this into a package include
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
