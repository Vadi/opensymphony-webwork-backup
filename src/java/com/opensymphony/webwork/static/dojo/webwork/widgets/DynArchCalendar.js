dojo.hostenv.startPackage("webwork.widgets.DynArchCalendar");
dojo.hostenv.startPackage("webwork.widgets.HTMLDynArchCalendar");

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

webwork.widgets.HTMLDynArchCalendar = function() {

	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);

	var self = this;

	this.templatePath = "webwork/widgets/DynArchCalendar.html";

	this.isContainer = false;
	this.widgetType = "DynArchCalendar";
	
	// default properties
	
	// the name of the global javascript variable to associate with this widget instance
	this.id = "";
		
	// the text input box
	this.inputField = null;
	this.inputFieldStyle = "";
	
	this.controlsDiv = null;
	
	// the trigger button
	this.button = null;
	
	// display the calendar as a flat control, or a popup control
	this.flat = false;	
	
	var argNames = [
		'inputField',
		'displayArea',
		'button',
		'eventName',
		'ifFormat',
		'daFormat',
		'singleClick',
		'firstDay',
		'align',
		'range',
		'weekNumbers',
		'flat',
		'date',
		'showsTime',
		'timeFormat',
		'electric',
		'step',
		'position',
		'cache',
		'showOthers'
	];
	var functionArgs = [
		'flatCallback',
		'disableFunc',
		'onSelect',
		'onClose',
		'onUpdate',
	]
	
	this.fillInTemplate = function(args, frag) {

		if (!Calendar) {
			dj_debug("DynArch Calendar Script not included");
			return;
		}

		// expost this widget instance globally
		if (self.id != "") window[self.id] = self;
	
		self.controlsDiv.id = webwork.Util.nextId();
	
		var params = {};

		if (self.flat) {
			params.flat = self.controlsDiv;
		}else{
			self.inputField = document.createElement("input");
			self.inputField.type = 'text';
			self.inputField.id = webwork.Util.nextId();
			
			self.button = document.createElement("input");
			self.button.id = webwork.Util.nextId();
			self.button.type = 'button';
			self.button.value = ' ... ';

			self.controlsDiv.appendChild(self.inputField);
			self.controlsDiv.appendChild(self.button);

			if (self.inputFieldStyle != "")
				self.inputField.style.cssText = self.inputFieldStyle;
	
			if (self.inputFieldClass != "")
				self.inputField.className = self.inputFieldClass;
		}
		

		webwork.Util.copyProperties(self.extraArgs, params);
	
		// fix the case of args - since they are all made lowercase by the fragment parser
		for (var i=0; i<argNames.length; i++) {
			var n = argNames[i];
			if (params[n.toLowerCase()])
				params[n] = params[n.toLowerCase()];
		}
		
		// build functions for the function args
		for (var i=0; i<functionArgs.length; i++) {
			var name = functionArgs[i];
			var txt = self.extraArgs[name.toLowerCase()];
			if (txt) {
				params[name] = new Function(txt);
			}
		}

		params.inputField = self.inputField;
		params.button = self.button;
		
		Calendar.setup(params);

	}
	
	
	this.show = function() {
		self.button.onclick();
	}
	
}

// is this needed as well as dojo.webui.Widget.call(this);
dj_inherits(webwork.widgets.HTMLDynArchCalendar, dojo.webui.DomWidget);

// allow the markup parser to construct these widgets
dojo.webui.widgets.tags.addParseTreeHandler("dojo:dynarchcalendar");

// register this widget constructor with the widget manager
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
