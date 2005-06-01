dojo.hostenv.startPackage("webwork.widgets.RemoteDiv");
dojo.hostenv.startPackage("webwork.widgets.HTMLRemoteDiv");

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

webwork.widgets.HTMLRemoteDiv = function() {

	this.callback = webwork.Util.makeGlobalCallback(this);
	
	// is this needed as well as the dj_inherits calls below
	// this coppied from slideshow
	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);

	// closure trickery
	var _this = this;

	this.templatePath = "webwork/widgets/RemoteDiv.html";

	this.isContainer = false;
	this.widgetType = "RemoteDiv";
	
	// default properties

	 // the location to obtain the remote content from
    this.href = "";

	// html to display while loading remote content
    this.loadingHtml = "";
    
    this.initialContent = "";
    
    // html to display when there is an error loading content
    this.errorHtml = "<i>Failed to load remote content</i>";

	// do we show transport errors
    this.showTransportError = true;

	// initial dealy before fetching content
	this.delay = 0;
	
	// how often to update the content from the server, after the initial delay
	this.refresh = 0;
		
	// dom node in the template that will contain the remote content
	this.contentDiv = null;
	
	// the name of the global javascript variable to associate with this widget instance
	this.id = "";
	
	this.fillInTemplate = function(args, frag) {
		
		webwork.Util.passThroughArgs(_this.extraArgs, _this.contentDiv);

		// fill in the contentDiv with the contents of the widget tag
		var widgetTag = frag["dojo:"+this.widgetType.toLowerCase()]["nodeRef"];
		if(widgetTag) {
			_this.contentDiv.innerHTML = widgetTag.innerHTML;

		}
		
		if (_this.id != "") {
			window[_this.id] = this;
		}

		if (_this.delay > 0) {
			_this.setTimeoutCallback(_this.delay);
		}
		else {
			_this.refreshContentFromServer();
		}
	}
	
    /*
     * Replaces the contents of the node with new data retrieved from the url.
     */

    this.refreshContentFromServer = function() {

        if( _this.loadingHtml != "" ) _this.contentDiv.innerHTML = _this.loadingHtml;

        var displayMsg = _this.errorHtml;

		if (_this.href == null || _this.href == "") {
			if( _this.showTransportError ) {
				displayMsg += ": no href specified";
	            _this.contentDiv.innerHTML = displayMsg;
	        }
		}
		else {
		
	        dojo.io.bind({
	            url: _this.href,
	            load: function(type, data, event) {
	                _this.contentDiv.innerHTML = data;
	            },
	            error: function(type, error){
	                if( _this.showTransportError )
	                    displayMsg += ": " + error.message;
	                _this.contentDiv.innerHTML = displayMsg;
	            },
	            mimetype: "text/plain",
	            useCache: false // TODO make this configurable
	        });

			if (_this.refresh > 0) {
				_this.setTimeoutCallback(_this.refresh);
			}
			
		}
    }

	this.setTimeoutCallback = function(millis) {
		webwork.Util.setTimeout(this.callback, "refreshContentFromServer", millis);
	}

}

// is this needed as well as dojo.webui.Widget.call(this);
dj_inherits(webwork.widgets.HTMLRemoteDiv, dojo.webui.DomWidget);

// allow the markup parser to construct these widgets
dojo.webui.widgets.tags.addParseTreeHandler("dojo:remotediv");

// register this widget constructor with the widget manager
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
