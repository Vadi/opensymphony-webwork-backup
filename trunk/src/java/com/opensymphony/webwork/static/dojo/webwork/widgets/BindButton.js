dojo.provide("webwork.widgets.BindButton");

dojo.require("dojo.io.*");
dojo.require("dojo.event.*");
dojo.require("dojo.widget.*");
dojo.require("dojo.xml.Parse");

dojo.require("webwork.Util");
dojo.require("webwork.widgets.HTMLBind");

/*
 * Component to do remote updating of a DOM tree.
 */

webwork.widgets.HTMLBindButton = function() {
	
	// inheritance
    // see: http://www.cs.rit.edu/~atk/JavaScript/manuals/jsobj/
	webwork.widgets.HTMLBind.call(this);
	var self = this;

	this.widgetType = "BindButton";
	this.templatePath = dojo.uri.dojoUri("webwork/widgets/BindButton.html");
	
	// the type of the input button - can be image
	this.type = "submit";

	// the template button instance
	this.button = null;

	var super_fillInTemplate = this.fillInTemplate;
	this.fillInTemplate = function(args, frag) {
		super_fillInTemplate(args, frag);
		
		webwork.Util.passThroughArgs(self.extraArgs, self.button);
		self.button.type = self.type;
    }

}

// is this needed as well as dojo.widget.Widget.call(this);
dj_inherits(webwork.widgets.HTMLBindButton, webwork.widgets.HTMLBind);

// make it a tag
dojo.widget.tags.addParseTreeHandler("dojo:BindButton");

// HACK - register this module as a widget package - to be replaced when dojo implements a propper widget namspace manager
dojo.widget.manager.registerWidgetPackage('webwork.widgets');
