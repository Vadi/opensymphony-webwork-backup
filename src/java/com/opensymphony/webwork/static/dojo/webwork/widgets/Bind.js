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
	
	// topics that will be notified with a "notify" message when the bind operation has completed successfully
	this.notifyTopics = "";

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

	// topics that this widget will listen to. Any message received on these topics will trigger a bind operation
	this.listenTopics = "";
	
	// the dom id of a target div to fill with the response
	this.targetDiv = "";	

	// javascript code to be executed when data arrives - arguments are (eventType, data)
	this.onLoad = "";
	
	// if true, we set the bind mimetype to text/javascript to cause dojo to eval the result
	this.evalResult = false;
	
	// does the bind call use the client side cache
	this.useCache = false;
	
	var trim = function(a) {
		a = a.replace( /^\s+/g, "" );// strip leading
		return a.replace( /\s+$/g, "" );// strip trailing
	}
	
	this.fillInTemplate = function() {
		// subscribe to out listenTopics
		
		var lt = self.listenTopics.split(",");
    	for (var i=0; i < lt.length; i++) {
    		var e = trim(lt[i]);
			dojo.event.topic.subscribe( e, self, "bind" );
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
			useCache: self.useCache
		};

		// the formId can either be a id or a form refrence
		if (self.formId != "")
			if (typeof formId == "object") {
				args.formNode = self.formId;
			}else{
				args.formNode = document.getElementById(self.formId);
			}
			
		
		if (self.href != "")
			args.url = this.href;

		// havn't tested this yet
		if (self.getUrl != "")
			args.url = eval(this.getUrl);

		if (self.evalResult) {
			args.mimetype = "text/javascript";
		}

		dojo.io.bind(args);
    }
    
    // allow this to be overriden by subclasses - say one that renders the response into a div
    this.load = function(type, data) {
    
		// notify our listeners
		if (self.notifyTopics != "") {
			var nt = self.notifyTopics.split(",");
			for (var i=0; i < nt.length; i++) {
				var topic = trim(nt[i]);
				dj_debug('notifying [' + topic + ']');
				dojo.event.topic.publish( topic, "notify" );
			}
		}
		    
    	if (self.targetDiv != "") {
			var div = document.getElementById(self.targetDiv);
			if (div) {
				var d = webwork.Util.nextId();
				dj_debug("received html <a onclick=\"var e = document.getElementById('" + d + "'); e.style.display = (e.style.display=='none')?'block':'none';return false;\" href='#'>showHide</a><textarea style='display:none; width:300px;height:200px' id='" + d + "'>" + data + "</textarea>");
				div.innerHTML = data;
				// create widget components from the received html
				try{
					var parser = new dojo.xml.Parse();
					var frag  = parser.parseElement(div, null, true);
					var fragParser = new dojo.webui.widgets.Parse(frag);
					fragParser.createComponents(frag);
				}catch(e){
					dj_debug("auto-build-widgets error: "+e);
				}
			}
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

// TODO needs to be placed into a package include
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
