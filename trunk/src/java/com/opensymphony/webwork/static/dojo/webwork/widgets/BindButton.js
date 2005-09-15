dojo.provide("webwork.widgets.BindButton");
dojo.provide("webwork.widgets.HTMLBindButton");

dojo.require("dojo.io.*");
dojo.require("dojo.event.*");
dojo.require("dojo.widget.*");
dojo.require("dojo.xml.Parse");

dojo.require("webwork.Util");
dojo.require("webwork.widgets.HTMLBind");

/*
 * Component to do a remote submit of a HTML form.
 */

webwork.widgets.HTMLBindButton = function() {

	// inheritance
    // see: http://www.cs.rit.edu/~atk/JavaScript/manuals/jsobj/
	webwork.widgets.HTMLBind.call(this);
	var self = this;

	this.widgetType = "BindButton";
	this.templatePath = dojo.uri.dojoUri("webwork/widgets/BindButton.html");

	// dom node in the template that will contain the remote content
	this.attachBtn = null;

    var super_fillInTemplate = this.fillInTemplate;
	this.fillInTemplate = function(args, frag) {
		super_fillInTemplate(args, frag);

       if (self.id) {
			self.attachBtn.id = self.id;
		}

		webwork.Util.passThroughArgs(self.extraArgs, self.attachBtn);
	}

}
dj_inherits(webwork.widgets.HTMLBindButton, webwork.widgets.HTMLBind);

// make it a tag
dojo.widget.tags.addParseTreeHandler("dojo:BindButton");

// HACK - register this module as a widget package - to be replaced when dojo implements a propper widget namspace manager
dojo.widget.manager.registerWidgetPackage('webwork.widgets');
