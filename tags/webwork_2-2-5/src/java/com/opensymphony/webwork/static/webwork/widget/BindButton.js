dojo.provide("webwork.widget.BindButton");

dojo.require("webwork.widget.Bind");
dojo.require("dojo.event.*");

dojo.widget.defineWidget(
	"webwork.widget.BindButton",
	webwork.widget.Bind,
	{
		templatePath: dojo.uri.dojoUri("../webwork/widget/BindButton.html"),
		templateCssPath: dojo.uri.dojoUri("../webwork/widget/BindButton.css"),
		
		// dom node in the template that will contain the remote content
		attachBtn: null,

    	//a snippet of js to invode before binding
    	preInvokeJS: "",
		
		
		postMixInProperties: function(){
			webwork.widget.BindButton.superclass.postMixInProperties.call(this, null);
		},
		fillInTemplate: function(args, frag) {
			webwork.widget.BindButton.superclass.fillInTemplate.call(this, null);
			
			if (this.id) {
				this.attachBtn.id = this.id;
			}
			else {
				this.attachBtn.id = webwork.Util.nextId();
			}
			
			this.extraArgs.id = this.attachBtn.id;
			//args.id = this.attachBtn.id;
			
			webwork.Util.passThroughArgs(this.extraArgs, this.attachBtn);
			//webwork.Util.passThroughArgs(args, this.attachBtn);
			webwork.Util.passThroughWidgetTagContent(this, frag, this.attachBtn);
		},
		postCreate: function() {
			webwork.widget.BindButton.superclass.postCreate.call(this, null);
		},
		
		// a hook / callback called before a button make an xhr call (bind).
		beforeBind: function() {},
		
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
            		// give people a chance to hook things in before we actually bind()
            	    this.beforeBind();
               		this.bind();
            	} catch (e) {
                	dojo.debug("EXCEPTION: " + e);
                	alert(e);
            	}
        	}
    	}
	}
);

