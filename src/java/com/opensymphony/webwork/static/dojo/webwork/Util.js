dojo.hostenv.startPackage("webwork.Util");

webwork.Util.passThroughArgs = function(args, target){
	// pass through the extra args, catering for special cases of style and class for html elements
	for (n in args) {
		var v = args[n];
		if (n == "style") {
			target.style.cssText = v;
		}else if (n == "class") {
			target.className = v;
		}else{
			target[n] = v;
		}
	}
}

webwork.Util.copyProperties = function(source, target){
	// pass through the extra args, catering for special cases of style and class for html elements
	for (key in source) target[key] = source[key];
}


webwork.Util.globalCallbackCount = 0;

webwork.Util.makeGlobalCallback = function(target) {
	var name = 'callback_hack_' + webwork.Util.globalCallbackCount++;
	window[name] = target;
	return name;
}

webwork.Util.setTimeout = function(callback, method, millis) {
	window.setTimeout(callback + "." + method + "()", millis);
}
webwork.Util.clearTimeout = function(callback) {
	window.clearTimeout(callback);
}


webwork.Util.nextIdValue = 0;

webwork.Util.nextId = function(scope) {
	return (scope==null?"id":scope) + webwork.Util.nextIdValue++;
}
