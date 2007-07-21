dojo.provide("webwork.widget.BindDiv");

dojo.require("webwork.widget.Bind");
dojo.require("webwork.Util");
dojo.require("dojo.event.*");

dojo.widget.defineWidget(
	"webwork.widget.BindDiv",
	webwork.widget.Bind,
	{
		
		// determine if BindDiv is running or not
		running: false,
		
		initializer: function() {
			// register a global object to use for window.setTimeout callbacks
			// this.callback = webwork.Util.makeGlobalCallback(this),
		},
		statics: function() {},
		
		templatePath: dojo.uri.dojoUri('../webwork/widget/BindDiv.html'),
		templateCssPath: dojo.uri.dojoUri('../webwork/widget/BindDiv.css'),
		
		
		//
		// default properties that can be provided by the widget user
		//

		// html to display while loading remote content
    	loadingHtml: "",

		// initial dealy before fetching content
		delay: 0,
	
		// how often to update the content from the server, after the initial delay
		refresh: 0,

		// does the timeout loop start automatically ?
		autoStart: true,

		// dom node in the template that will contain the remote content
		contentDiv: null,

		// support a toggelable div - each listenEvent will trigger a change in the display state
		// the bind call will only happen when the remote div is displayed
		toggle: false,
		
		
		
		postMixInProperties: function() {
			// call superclass method
			webwork.widget.BindDiv.superclass.postMixInProperties.call(this, null);
		},
		
		fillInTemplate: function(args, frag) {
		
			webwork.widget.BindDiv.superclass.fillInTemplate.call(this, null);
			
			var self = this;
			
			if (this.id == "") { 
				this.contentDiv.id = webwork.Util.nextId();		
			}else {
				this.contentDiv.id = this.id;
			}
			this.targetDiv = this.contentDiv.id;
			

			this.extraArgs.id = this.targetDiv;
			webwork.Util.passThroughArgs(this.extraArgs, this.contentDiv);
			webwork.Util.passThroughWidgetTagContent(this, frag, this.contentDiv);
			

			// hook into before the bind operation to display the loading message
			// do this always - to allow for on the fuy changes to the loadingHtml
			
			
			dojo.event.kwConnect({
				adviceType: 'before',
				srcObj: self,
				srcFunc: "bind",
				adviceObj: self,
				adviceFunc: "loading"
			});
			
			if (this.autoStart) {
				this.start();
			}

	   		if (this.toggle) {
				dojo.event.kwConnect({
					type: 'around',
					srcObj: self,
					srcFunc: "bind",
					adviceObj: self,
					adviceFunc: "__toggleInterceptor"
				});
    		}
		},
		
		postCreate: function() {
			webwork.widget.BindDiv.superclass.postCreate.call(this, null);
		},
		
		
		
		__toggleInterceptor:  function(invocation) {
			var hidden = this.contentDiv.style.display == 'none';
			this.contentDiv.style.display = (hidden)?'':'none';
			if (hidden) {
				invocation.proceed();
			}
		},
		
		loading: function() {
        	if( this.loadingHtml != "" ) {
        		this.contentDiv.innerHTML = this.loadingHtml;
        	}
		},
		
		afterTimeout: function() {
			if (this.running) {
				// do the bind
				this.bind();
			
				// setup the next timeout
				if (this.refresh > 0) {
					this._nextTimeout(this.refresh);
				}
			}
		},
		
		_nextTimeout: function(millis) {
			var self = this;
			var timer = dojo.lang.setTimeout(function() {
				self.afterTimeout();
			}, millis);
			// webwork.Util.setTimeout(this.callback, "afterTimeout", millis);
		},
		
		stop: function() {
			if (! this.running) 
				return;
			this.running = false;
			//webwork.Util.clearTimeout(this.callback);
			//dojo.lang.clearTimer();
		},

		start: function() {
			if (this.running) 
				return;
			this.running = true;
		
			if (this.delay > 0) {
				this._nextTimeout(this.delay);
			}
		}
	}
);
