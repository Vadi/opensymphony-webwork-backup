dojo.hostenv.startPackage("webwork.widgets.RemoteDiv");
dojo.hostenv.startPackage("webwork.widgets.HTMLRemoteDiv");

dojo.hostenv.loadModule("dojo.io.*");

dojo.hostenv.loadModule("dojo.event.*");

dojo.hostenv.loadModule("dojo.xml.Parse");
dojo.hostenv.loadModule("dojo.webui.widgets.Parse");
dojo.hostenv.loadModule("dojo.webui.Widget");
dojo.hostenv.loadModule("dojo.webui.DomWidget");
dojo.hostenv.loadModule("dojo.webui.WidgetManager");

dojo.hostenv.loadModule("dojo.animation.*");
dojo.hostenv.loadModule("dojo.math.*");

dojo.hostenv.loadModule("webwork.Util");
dojo.hostenv.loadModule("webwork.widgets.BindWidget");

/*
 * Component to do remote updating of a DOM tree.
 */

webwork.widgets.HTMLRemoteDiv = function() {

	// is this needed as well as the dj_inherits calls below
	// this coppied from slideshow
	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);
	webwork.widgets.BindWidget.call(this);

	this.callback = webwork.Util.makeGlobalCallback(this);


	// closure trickery
	var _this = this;

	this.templatePath = "webwork/widgets/RemoteDiv.html";

	this.isContainer = false;
	this.widgetType = "RemoteDiv";
	
	// default properties

	// html to display while loading remote content
    this.loadingHtml = "";
    
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
	
	this.delayedBind = function(millis) {
		if (!millis) millis = this.refreshPeriod;
		webwork.Util.setTimeout(this.callback, "doDelayedBind", millis);
	}

	this.fillInTemplate = function(args, frag) {

		_this.contentDiv.id = webwork.Util.nextId();
		this.targetDiv = _this.contentDiv.id;

		_this.init();
		
		webwork.Util.passThroughArgs(_this.extraArgs, _this.contentDiv);

		// fill in the contentDiv with the contents of the widget tag
		var widgetTag = frag["dojo:remotediv"].nodeRef;
		if(widgetTag) _this.contentDiv.innerHTML = widgetTag.innerHTML;

		// hook into before the bind operation to display the loading message
		// do this always - to allow for on the fuy changes to the loadingHtml
		dojo.event.kwConnect({
			srcObj: this,
			srcFunc: "bind",
			adviceObj: this,
			adviceFunc: "loading"
		});

		this.start();

	}

    this.loading = function() {
        if( _this.loadingHtml != "" ) _this.contentDiv.innerHTML = _this.loadingHtml;
	}

	var connected = false;
	this.refreshPeriod = 0;
	
	this.setRefresh = function(refresh) {
		_this.refreshPeriod = refresh;
	}
	
	this.doDelayedBind = function() {
		if (_this.refreshPeriod > 0)
			this.delayedBind();
		this.bind();
	}
	
	
	var running = false;
	var lastRefresh = 0;
	this.stop = function() {
		if (!running) return;
		running = false;
		
		lastRefresh = this.refreshPeriod;
		this.setRefresh(0);

		webwork.Util.clearTimeout(this.callback);
		
	}

	this.start = function() {
		if (running) return;
		running = true;
		
		var refresh = lastRefresh;
		if (refresh == 0) refresh = this.refresh;
		this.setRefresh(refresh);
		
		_this.delayedBind(_this.delay);

	}

}
dj_inherits(webwork.widgets.HTMLRemoteDiv, webwork.widgets.BindWidget);
dojo.webui.widgets.tags.addParseTreeHandler("dojo:remotediv");

// TODO move this into a package include
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
