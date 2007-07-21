dojo.provide("webwork.widget.Bind");

dojo.require("dojo.io.*");
dojo.require("dojo.event.*");
dojo.require("dojo.widget.*");
dojo.require("dojo.xml.Parse");

dojo.require("webwork.Util");


dojo.widget.defineWidget(
	"webwork.widget.Bind",
	dojo.widget.HtmlWidget,
	{
		initializer: function() {
			// constructor
		},
		statics: function() {
			// statics
		},
		templatePath: dojo.uri.dojoUri('../webwork/widget/Bind.html'),
		templateCssPath: dojo.uri.dojoUri('../webwork/widget/Bind.css'),
	
		// the name of the global javascript variable to associate with this widget instance
    	id: "",

    	// the id of the form object to bind to
    	formId: "",

    	// the url to bind to
    	href: "",

    	// javascript code to provide the href - will be evaluated each time before the data is loaded
    	getHref: "",

    	// topics that will be notified with a "notify" message when the bind operation has completed successfully
    	notifyTopics: "",

    	// html to display when there is an error loading content
    	errorHtml: "Failed to load remote content",

   	 	// do we show transport errors
    	showTransportError: false,
		
		// topics that this widget will listen to. Any message received on these topics will trigger a bind operation
    	listenTopics: "",

    	// the dom id of a target div to fill with the response
    	targetDiv: "",

    	// javascript code to be executed when data arrives - arguments are (eventType, data)
    	onLoad: "",

    	// if true, we set the bind mimetype to text/javascript to cause dojo to eval the result.
    	// and the result will be eval'ed by bind (internally the content type will be set to text/javascript
     	// otherwise targetDiv and onLoad may both be specified, targetDiv will be filled first
    	evalResult: false,

    	// does the bind call use the client side cache - NOTE : doesn't seem to make IE not use the cache :(
    	useCache: false,
		
		
		postMixInProperties: function() {
		},
		
		fillInTemplate: function() {
			// associate the global instance for this widget
        	if (this.id == "") {
        		this.id = webwork.Util.nextId(null);
        	}
        	window[this.id] = this;
        	
		
			// subscribe to out listenTopics
			var _listenTopics = this.listenTopics.split(',');
			for (var a=0; a< _listenTopics.length; a++) {
				var _listenTopic = webwork.Util.trim(_listenTopics[a]);
				dojo.event.topic.subscribe(_listenTopic, this, "bind");
			}
		},
		
		postCreate: function() {
		},
		
		bind: function() {
			var self = this;
			var args = { 
            	load: function(type, data, evt) {
            				self.load(type, data, evt);
            			},
            	error: function(type, error) {
            				self.error(type, error);
            			},
            	preventCache: this.useCache? false : true
        	};

			if (this.evalResult) {
            	args.mimetype = "text/javascript";
        	}
        	
        	// the formId can either be a id or a form refrence
        	var doBind = false;
        	if (this.formId != "") { 
        		if (typeof formId == "object") {
                	args.formNode = this.formId;
                	doBind = true;
            	}else{
                	args.formNode = dojo.byId(this.formId);
                	doBind = true;
            	}
       		}
        	if (this.href != "") {
            	args.url = this.href;
            	doBind = true;
        	}
        	if (this.getHref != "") {
            	args.url = eval(this.getHref);
            	doBind = true;
        	}
        			
        	if (doBind) {		
       	 		try {
       				dojo.io.bind(args);
        		} catch (e) {
            		dojo.debug("EXCEPTION: " + e);
				}
			}
		},
		
		load: function(type, data, evt) {
			 if (this.targetDiv != "") {
            	var div = dojo.byId(this.targetDiv);
            	if (div) {
                	var d = webwork.Util.nextId();

                	// IE seems to have major issues with setting div.innerHTML in this thread !!
                	window.setTimeout(function() {
                    	div.innerHTML = data;

                   	 	// create widget components from the received html
                    	try{
                        	var xmlParser = new dojo.xml.Parse();
                        	var frag  = xmlParser.parseElement(div, null, true);
                        	dojo.widget.getParser().createComponents(frag);
                        	// eval any scripts being returned
                        	var scripts = div.getElementsByTagName('script');
                        	for (var i=0; i<scripts.length; i++) {
                            	eval(scripts[i].innerHTML);
                        	}
                    	}catch(e){
                        	dojo.debug("auto-build-widgets error: "+e);
                    	}
                    	//moved here to support WW-1193
                    	if (this.onLoad != "") {
                        	eval(this.onLoad);
                    	}
                	}, 0);

                	dojo.debug("received html <a onclick=\"var e = document.getElementById('" + d + "'); e.style.display = (e.style.display=='none')?'block':'none';return false;\" href='#'>showHide</a><textarea style='display:none; width:98%;height:200px' id='" + d + "'>" + data + "</textarea>");
            	}
        	} else {
           	 	//moved here to support WW-1193
            	if (this.onLoad != "") {
                	eval(this.onLoad);
            	}
        	}

        	// notify our listeners
        	if (this.notifyTopics != "") {
            	var nt = this.notifyTopics.split(",");
            	for (var i=0; i < nt.length; i++) {
                	var topic = webwork.Util.trim(nt[i]);
               	 	dojo.event.topic.publish( topic, "notify" );
               	 	dojo.debug('notifying [' + topic + ']');
            	}
        	}
		},
		
		error: function(type, error) {
			if (this.showTransportError) {
            	alert(error.message);
	        }else{
            	alert(this.errorHtml);
        	}
		}
	}
);
