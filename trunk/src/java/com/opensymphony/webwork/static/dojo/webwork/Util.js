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
