
/*
 * Component to do remote updating of a DOM tree.
 *
 * @param url the location that component will get updated data from
 * @param nodeId the HTML/DOM done "id" that is to be updated
 * @param reloadingText if not null or empty the text to display while an update is being made
 * @param errorText the text to display if there is an error (usually a connection error with the url)
 * @param showTransportErrorText boolean on whether to display the error message from dojo
 */
function RemoteUpdateComponent( url, nodeId, reloadingText, errorText, showTransportErrorText ) {

    this.updateUrl = url;
    this.replaceNodeId = nodeId;
    this.reloadingText = reloadingText;
    this.errorText = errorText;
    this.showTransportErrorText = showTransportErrorText;

    var self = this;

    /*
     * Replaces the contents of the node with new data retrieved from the url.
     */
    this.refresh = function() {

        var replaceNode = document.getElementById(self.replaceNodeId);
        if( null!=self.reloadingText || ""!=self.reloadingText )
            replaceNode.innerHTML = self.reloadingText;
        var displayMsg = self.errorText;

        dojo.io.bind({
            url: self.updateUrl,
            load: function(type, data, event) {
                replaceNode.innerHTML = data;
            },
            error: function(type, error){
                if( "true"==self.showTransportErrorText )
                    displayMsg += ": " + error.message;
                replaceNode.innerHTML = displayMsg;
            },
            mimetype: "text/plain"
        });

    }

    /*
     * Replaces the contents of the node on a specified frequent interval.
     *
     * @freq the frequency in seconds
     */
    this.intervalRefresh = function( freq ) {

        self.refresh();
        if( freq <= 0 )
            return;

        var uid = self.replaceNodeId;
        var sec = freq * 1000;
        var method = uid + ".intervalRefresh("+freq+")";

        window[uid] = self;
        window.setTimeout( method, sec );

    }

    /*
     * Replaces the contents of the node if the parameter is the same as the name of this element.
     *
     * @elementId the elemeent id to match
     */
    this.tabbedElementIdRefresh = function( elementId ) {
        if( self.replaceNodeId=="tab_contents_update_"+elementId )
            self.refresh();
    }

}


/*
 * Function to make a remote call and then evaluate the result
 *
 * @param url the location that component will get updated data from
 * @param errorText the text to display if there is an error (usually a connection error with the url)
 * @param showTransportErrorText boolean on whether to display the error message from dojo
 */
function evalAfterRemoteCall( url, errorText, showTransportErrorText, topic ) {

    displayMsg = errorText;

    dojo.io.bind({
        url: url,
        load: function(type, data, event) {
            if( ""!=data ) {
                eval( data );
            }
        },
        error: function(type, error){
            if( "true"==showTransportErrorText )
                displayMsg += ": " + error.message;
            alert( displayMsg );
        },
        mimetype: "text/javascript"
    });

    // publish to the topic
    if( ""!=topic )
        dojo.event.topic.publish( topic, "clicked" );

}