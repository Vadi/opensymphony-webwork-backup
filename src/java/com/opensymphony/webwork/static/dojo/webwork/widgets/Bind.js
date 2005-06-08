dojo.hostenv.startPackage("webwork.widgets.Bind");
dojo.hostenv.startPackage("webwork.widgets.HTMLBind");

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

webwork.widgets.Bind = function() {

	var self = this;

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

    // html to display when there is an error loading content
    this.errorHtml = "Failed to load remote content";

	// do we show transport errors
    this.showTransportError = false;

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
	
	// if true, we set the bind mimetype to text/javascript to cause dojo to eval the result
	this.evalOnLoad = "";
	
	this.fillInTemplate = function() {
		// subscribe to out triggerTopics
	
    	for (var i=0; i < self.triggerTopics.length; i++) {
			dojo.event.topic.subscribe( self.triggerTopics[i], self, "bind" );
		}
	       
		// associate the global instance for this widget
		if (self.id != "") {
			window[self.id] = self;
		}


    }
    
    this.bind = function() {
		var args = {
			load: self.load,
			error: self.error,
			useCache: false
		};

		if (self.formId != "")
			args.formNode = document.getElementById(self.formId);
		
		if (self.href != "")
			args.url = this.href;

		// havn't tested this yet
		if (self.getUrl != "")
			args.url = eval(this.getUrl);

		// todo replace with isTrue helper
		if (self.evalOnLoad == "true") {
			args.mimetype = "text/javascript";
		}

		dojo.io.bind(args);
    }
    
    // allow this to be overriden by subclasses - say one that renders the response into a div
    this.load = function(type, data) {
    
		// notify our listeners
		for (var i=0; i < self.notifyTopics.length; i++)
			dojo.event.topic.publish( self.notifyTopics[i], "notify" );
    
    	if (self.targetDiv != "") {
			var div = document.getElementById(self.targetDiv);
			if (div) div.innerHTML = data;
    	}
    	
    	if (self.onLoad != "") {
    		eval(self.onLoad);
    	}

    }
    
    this.error = function(type, error) {
		if (self.showTransportError) {
			alert(error.message);
		}else{
			alert(self.errorHtml);
		}
    }

}

webwork.widgets.HTMLBind = function() {
	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);
	webwork.widgets.Bind.call(this);

	var self = this;
	this.isContainer = false;
	this.widgetType = "Bind";
	this.templatePath = "webwork/widgets/Bind.html";

}

dj_inherits(webwork.widgets.Bind, dojo.webui.DomWidget);
dj_inherits(webwork.widgets.HTMLBind, webwork.widgets.Bind);
dojo.webui.widgets.tags.addParseTreeHandler("dojo:bind");
