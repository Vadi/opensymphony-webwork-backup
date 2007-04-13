dojo.provide("webwork.widget.BindAnchor");

dojo.require("webwork.widget.Bind");
dojo.require("dojo.event.*");

dojo.widget.defineWidget(
	"webwork.widget.BindAnchor",
	webwork.widget.Bind, 
	{
		initializer: function() {},
		statics: function() {},
		
		templatePath: dojo.uri.dojoUri('../webwork/widget/BindAnchor.html'),
		templateCssPath: dojo.uri.dojoUri('../webwork/widget/BindAnchor.css'),
		isContainer: true,
		
		// the template anchor instance
		anchor: null,

    	//a snippet of js to invode before binding
    	preInvokeJS: "",
		
		
		postMixInProperties: function() {
			webwork.widget.BindAnchor.superclass.postMixInProperties.call(this, null);
		},
		fillInTemplate: function(args, frag) {
			webwork.widget.BindAnchor.superclass.fillInTemplate.call(this, null);

			if (this.id) {
				this.anchor.id = this.id;
			}
			else {
				this.anchor.id = webwork.Util.nextId();
			}
			this.anchor.href = "javascript:{}";
		
			this.extraArgs.id = this.anchor.id;
			this.extraArgs.href = this.anchor.href;
			webwork.Util.passThroughArgs(this.extraArgs, this.anchor);
			
			var self = this;
        	dojo.event.kwConnect({
        		adviceType: 'before',
            	srcObj: self.anchor,
            	srcFunc: "onclick",
            	adviceObj: self,
            	adviceFunc: "execute"
	        });

        	webwork.Util.passThroughWidgetTagContent(this, frag, this.anchor);
		},
		
		postCreate: function() {
			webwork.widget.BindAnchor.superclass.postCreate.call(this, null);
		},
		
		execute: function() {
        	var executeConnect = true;
        	//If the user provided some preInvokeJS invoke it and store the results into the
        	//executeConnect var to determine if the connect should occur
			if (this.preInvokeJS != "") {
            	dojo.debug('Evaluating js: ' + this.preInvokeJS);
            	executeConnect = eval(this.preInvokeJS);
			}
        	if (executeConnect) {
        		try {
            		this.bind();
            	}
            	catch(e) {
            		dojo.debug("EXCEPTION: " + e);
            		alert(e);
            	}		
        	}
	    }
	}
);
