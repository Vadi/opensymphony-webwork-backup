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

	
	// is this needed as well as the dj_inherits calls below
	// this coppied from slideshow
	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);

	// closure trickery
	var _this = this;

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
		if (_this.id != "") window[_this.id] = _this;
	
		_this.controlsDiv.id = webwork.Util.nextId();
	
		var params = {};

		if (_this.flat) {
			params.flat = _this.controlsDiv;
		}else{
			_this.inputField = document.createElement("input");
			_this.inputField.type = 'text';
			_this.inputField.id = webwork.Util.nextId();
			
			_this.button = document.createElement("input");
			_this.button.id = webwork.Util.nextId();
			_this.button.type = 'button';
			_this.button.value = ' ... ';

			_this.controlsDiv.appendChild(_this.inputField);
			_this.controlsDiv.appendChild(_this.button);

			if (_this.inputFieldStyle != "")
				_this.inputField.style.cssText = _this.inputFieldStyle;
	
			if (_this.inputFieldClass != "")
				_this.inputField.className = _this.inputFieldClass;
		}
		

		webwork.Util.copyProperties(_this.extraArgs, params);
	
		// fix the case of args - since they are all made lowercase by the fragment parser
		for (var i=0; i<argNames.length; i++) {
			var n = argNames[i];
			if (params[n.toLowerCase()])
				params[n] = params[n.toLowerCase()];
		}
		
		// build functions for the function args
		for (var i=0; i<functionArgs.length; i++) {
			var name = functionArgs[i];
			var txt = _this.extraArgs[name.toLowerCase()];
			if (txt) {
				params[name] = new Function(txt);
			}
		}

		params.inputField = _this.inputField;
		params.button = _this.button;
		
		Calendar.setup(params);

	}
	
	
	this.show = function() {
		this.button.onclick();
	}
	
}

// is this needed as well as dojo.webui.Widget.call(this);
dj_inherits(webwork.widgets.HTMLDynArchCalendar, dojo.webui.DomWidget);

// allow the markup parser to construct these widgets
dojo.webui.widgets.tags.addParseTreeHandler("dojo:dynarchcalendar");

// register this widget constructor with the widget manager
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
