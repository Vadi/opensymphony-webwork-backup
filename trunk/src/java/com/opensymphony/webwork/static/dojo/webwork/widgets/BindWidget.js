dojo.hostenv.startPackage("webwork.widgets.BindWidget");

dojo.hostenv.loadModule("dojo.io.*");

dojo.hostenv.loadModule("dojo.event.*");

dojo.hostenv.loadModule("dojo.xml.Parse");
dojo.hostenv.loadModule("dojo.webui.widgets.Parse");
dojo.hostenv.loadModule("dojo.webui.Widget");
dojo.hostenv.loadModule("dojo.webui.DomWidget");
dojo.hostenv.loadModule("dojo.webui.WidgetManager");

dojo.hostenv.loadModule("webwork.Util");

/*
 * 
 */

webwork.widgets.BindWidget = function() {

	var _this = this;

	// is this needed as well as the dj_inherits calls below
	// this coppied from slideshow
	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);

	// the name of the global javascript variable to associate with this widget instance
	this.id = "";


	/**
	 * Bind Operation Inputs
	 *
	 * formId or href or urlScript must be supplied
	 *	 
	 */

	// the id of the form object to bind to
	this.formId = "";

	// the url to bind to
	this.href = "";
	
	// javascript code to provide the url
	this.getUrl = ""
	
	// topics that will be notified with a "notify" message when the bind operation is complete
	this.notifyTopics = [];

	/**
	 * Bind Operation Outputs
	 *
	 * if eval = true the result will be eval'ed by bind (internally the content type will be set to etxt/javascript
	 * otherwise targetDiv and onLoad may both be specified, targetDiv will be filled first
	 */

	// topics that will trigger a bind operation when any message is sent to it
	this.triggerTopics = [];
	
	// the dom id of a target div to fill with the response
	this.targetDiv = "";	

	// javascript code to be evaled when data arrives - arguments are (eventType, data)
	this.onLoad = "";
	
	// if true, we set the bind content type to text/javascript to cause bind to eval the result
	this.eval = "";

	this.test = function() {
		alert('test');
	}
	
	this.init = function() {

		// subscribe to out triggerTopics
	
    	for (var i=0; i < _this.triggerTopics.length; i++) {
			dojo.event.topic.subscribe( _this.triggerTopics[i], _this, "bind" );
		}
	       
		// associate the global instance for this widget
		if (_this.id != "") {
			window[_this.id] = _this;
		}


    }
    
    this.onEvent = function() {
    	alert('on event');
    }
    
    this.bind = function() {
		var args = {
			load:_this.load,
			useCache: false
		};

		if (_this.formId != "")
			args.formNode = document.getElementById(_this.formId);
		
		if (_this.href != "")
			args.url = this.href;

		if (_this.getUrl != "")
			args.url = eval(this.getUrl);

		// todo replace with isTrue helper
		if (_this.eval == "true" || _this.eval == "1" || _this.eval == "yes" || _this.eval == "on") 
			args.contentType = "text/javascript";

		dojo.io.bind(args);
    }
    
    // allow this to be overriden by subclasses - say one that renders the response into a div
    this.load = function(type, data) {
    
		// notify our listeners
		for (var i=0; i < _this.notifyTopics.length; i++)
			dojo.event.topic.publish( _this.notifyTopics[i], "notify" );
    
    	if (_this.targetDiv != "") {
			var div = document.getElementById(_this.targetDiv);
			if (div) div.innerHTML = data;
    	}
    	
    	if (_this.onLoad != "") {
    		eval(_this.onLoad);
    	}
    }

}
dj_inherits(webwork.widgets.BindWidget, dojo.webui.DomWidget);
