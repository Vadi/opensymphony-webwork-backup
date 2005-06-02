dojo.hostenv.startPackage("webwork.widgets.BindDiv");
dojo.hostenv.startPackage("webwork.widgets.HTMLBindDiv");

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
dojo.hostenv.loadModule("webwork.widgets.Bind");

/*
 * Component to do remote updating of a DOM tree.
 */

webwork.widgets.HTMLBindDiv = function() {

	dojo.webui.DomWidget.call(this);
	dojo.webui.HTMLWidget.call(this);
	webwork.widgets.HTMLBind.call(this);

	this.callback = webwork.Util.makeGlobalCallback(this);

	var self = this;

	this.templatePath = "webwork/widgets/BindDiv.html";

	this.isContainer = false;
	this.widgetType = "BindDiv";
	
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
		if (!millis) millis = self.refreshPeriod;
		webwork.Util.setTimeout(self.callback, "doDelayedBind", millis);
	}

	var super_fillInTemplate = this.fillInTemplate;
	this.fillInTemplate = function(args, frag) {
		
		self.contentDiv.id = webwork.Util.nextId();
		self.targetDiv = self.contentDiv.id;

		super_fillInTemplate();
		
		webwork.Util.passThroughArgs(self.extraArgs, self.contentDiv);
		webwork.Util.passThroughWidgetTagContent(self, frag, self.contentDiv);

		// hook into before the bind operation to display the loading message
		// do this always - to allow for on the fuy changes to the loadingHtml
		dojo.event.kwConnect({
			srcObj: self,
			srcFunc: "bind",
			adviceObj: self,
			adviceFunc: "loading"
		});

		self.start();

	}

    this.loading = function() {
        if( self.loadingHtml != "" ) self.contentDiv.innerHTML = self.loadingHtml;
	}

	var connected = false;
	this.refreshPeriod = 0;
	
	this.setRefresh = function(refresh) {
		self.refreshPeriod = refresh;
	}
	
	this.doDelayedBind = function() {
		if (self.refreshPeriod > 0)
			self.delayedBind();
		self.bind();
	}
	
	
	var running = false;
	var lastRefresh = 0;
	this.stop = function() {
		if (!running) return;
		running = false;
		
		lastRefresh = self.refreshPeriod;
		self.setRefresh(0);

		webwork.Util.clearTimeout(self.callback);
		
	}

	this.start = function() {
		if (running) return;
		running = true;
		
		var refresh = lastRefresh;
		if (refresh == 0) refresh = self.refresh;
		self.setRefresh(refresh);
		
		self.delayedBind(self.delay);

	}

}
dj_inherits(webwork.widgets.HTMLBindDiv, webwork.widgets.HTMLBind);
dojo.webui.widgets.tags.addParseTreeHandler("dojo:BindDiv");

// TODO move this into a package include
dojo.webui.widgetManager.registerWidgetPackage('webwork.widgets');
