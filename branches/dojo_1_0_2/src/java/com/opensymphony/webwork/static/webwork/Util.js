dojo.provide("webwork.Util");

/*
 * This function will copy over all <code>args</code> to <code>target</code>
 * except for dojo related arguments eg. "dojoType" or "dojotype".
 * 
 * @param taget - obj where the arguments are copied over to
 * @param args - array of arguments
 */
webwork.Util.passThroughArgs = function(args, target){
	// pass through the extra args, catering for special cases of style and class for html elements
	var str = '';
	for (n in args) {
		var v = args[n];
		if (n == "style") {
			target.style.cssText = v;
			str = str + n+"="+v+"\n";
		}else if (n == "class") {
			target.className = v;
		}else if (n == "dojoType") {
		}else if (n == "dojotype") {
		}else{
			target[n] = v;
			str = str + n+"="+v+"\n";
		}
	}
	// alert(str);
}

/*
 * This function copied over the innerHTML from <code>widget</code> to 
 * <code>target</code> through the <code>frag</code> (dojo parsed 
 * fragment).
 *
 * @param widget
 * @param frag
 * @param target
 */
webwork.Util.passThroughWidgetTagContent = function(widget, frag, target) {
	// fill in the target with the contents of the widget tag
	var _tmp = frag["dojo:" + widget.widgetType.toLowerCase()];
	var widgetTag = null;
	if(_tmp) {
		widgetTag = _tmp.nodeRef;
	}
	else {
		_tmp = frag["webwork:" + widget.widgetType.toLowerCase()];
		if (_tmp) {
			widgetTag = _tmp.nodeRef;
		}
	}
	if(widgetTag) {
		target.innerHTML = widgetTag.innerHTML;
	}
}

/*
 * This function copy over all the properties in <code>source</code>
 * to <code>target</code>
 *
 * @param source
 * @param target
 */
webwork.Util.copyProperties = function(source, target){
	// pass through the extra args, catering for special cases of style and class for html elements
	for (key in source) target[key] = source[key];
}


webwork.Util.globalCallbackCount = 0;

/*
 * This function register <code>target</code> with a unique name under
 * the global scope (js window object) and return the unique name such that
 * it could be referred latter on using window[the_unique_name_returned]. 
 * The unique name is 'callback_hack_' followed by a global incrementing 
 * number starting from 0.
 *
 * @param target
 * @return a unique name to identified <code>target</code>
 */
webwork.Util.makeGlobalCallback = function(target) {
	var name = 'callback_hack_' + webwork.Util.globalCallbackCount++;
	window[name] = target;
	return name;
}

/*
 * This function allow a <code>callback</code>'s <code>method</code>
 * to be called when a timeout identified <code>millis</code>miliseconds 
 * is reached, and return an id (which could be used if one need to stop this 
 * timeout execution using webwork.Util.clearTimeout(id))
 *
 * @param callback
 * @param method
 * @param milis
 * @return - the id
 */
webwork.Util.setTimeout = function(callback, method, millis) {
	window.setTimeout(callback + "." + method + "()", millis);
}

/*
 * This function clear the timeout of <code>callback<code>.
 * This <code>callback</code> will have to be the id returned from 
 * webwork.Util.setTimeout(....)
 *
 * @param callback
 */
webwork.Util.clearTimeout = function(callback) {
	window.clearTimeout(callback);
}


webwork.Util.nextIdValue = 0;

/*
 * This function generate a unique id based on the <code>scope</code>
 * supplied which could be 'null'.
 * 
 * @param scope
 * @return unique id
 */
webwork.Util.nextId = function(scope) {
	return (scope==null?"id":scope) + webwork.Util.nextIdValue++;
}

/*
 * This function trims leading and trailing spaces from<code>a</code>
 * and return the result
 *
 * @param a
 * @return trimed result
 */
webwork.Util.trim = function(a) {
        a = a.replace( /^\s+/g, "" );// strip leading
        return a.replace( /\s+$/g, "" );// strip trailing
}


