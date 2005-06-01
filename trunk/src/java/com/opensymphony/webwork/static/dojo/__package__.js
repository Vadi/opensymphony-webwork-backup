/* Copyright (c) 2004-2005 The Dojo Foundation, Licensed under the Academic Free License version 2.1 or above *//**
* @file bootstrap1.js
*
* bootstrap file that runs before hostenv_*.js file.
*
* @author Copyright 2004 Mark D. Anderson (mda@discerning.com)
* @author Licensed under the Academic Free License 2.1 http://www.opensource.org/licenses/afl-2.1.php
*
* $Id$
*/

/**
 * The global djConfig can be set prior to loading the library, to override certain settings.
 * It does not exist under dojo.* so that it can be set before the dojo variable exists.
 * Setting any of these variables *after* the library has loaded does nothing at all.
 * The variables that can be set are as follows:
 *
 * <dl>
 * <dt>baseScriptUri
 *  <dd>The value that getBaseScriptUri() will return. It is the base URI for loading modules.
 *  If not set in config, we find it using libraryScriptUri (stripping out the name part).
 *
 * <dt>baseRelativePath
 *  <dd>How to get from the parent URI of the URI defining the bootstrap code (libraryScriptUri)
 *  to the base URI. Defaults to '' (meaning that the bootstrap code sits in the top).
 *  If it is non-empty, it has to have a trailing '/'.
 *
 * <dt>libraryScriptUri
 *  <dd>Unless set in config, in a browser environment, this is the full
 *  value of the 'src' attribute of our script element.
 *  In a command line, this is the argument specifying the library file.
 *  If you set baseScriptUri, this is ignored.
 *  Setting it saves us the effort of trying to figure it out, but you
 *  might as well just set baseScriptUri instead.
 *
 * <dt>isDebug
 *  <dd>whether debug output is enabled.
 * </dl>
 */
//:GLVAR Object djConfig
if(typeof djConfig == 'undefined'){ 
	var djConfig = {};
}

/**
 * dojo is the root variable of (almost all) our public symbols.
 */
//=java public class dojo {
var dojo;
if(typeof dojo == 'undefined'){ dojo = {}; }

/**
 * dj_global is an alias for the top-level global object in the host environment (the "window" object in a browser).
 */
//:GLVAR Object dj_global
var dj_global = this; //typeof window == 'undefined' ? this : window;

// ****************************************************************
// global public utils
// ****************************************************************

/**
 * Produce a line of debug output. 
 * Does nothing unless dojo.hostenv.is_debug_ is true.
 * varargs, joined with ''.
 * Caller should not supply a trailing "\n".
 *
 * TODO: dj_debug() is a convenience for dojo.hostenv.debug()?
 */
// We have a workaround here for the "arguments" object not being legal when using "jsc -fast+".
/*@cc_on @*/
/*@if (@_jscript_version >= 7)
function dj_debug(... args : Object[]) {
@else @*/
function dj_debug(){
	var args = arguments;
/*@end @*/
	if(typeof dojo.hostenv.println != 'function'){
		dj_throw("attempt to call dj_debug when there is no dojo.hostenv println implementation (yet?)");
	}
	if(!dojo.hostenv.is_debug_){ return; }
	var isJUM = dj_global["jum"];
	var s = isJUM ? "": "DEBUG: ";
	for(var i=0;i<args.length;++i){ s += args[i]; }
	if(isJUM){ // this seems to be the only way to get JUM to "play nice"
		jum.debug(s);
	}else{
		dojo.hostenv.println(s);
	}
}

/**
* Throws an Error object given the string err. For now, will also do a println to the user first.
*/
function dj_throw(message){
	if((typeof dojo.hostenv != 'undefined')&&(typeof dojo.hostenv.println != 'undefined')){ 
		dojo.hostenv.println("fatal error: " + message);
	}
	throw Error(message);
}

/*
 * utility to print an Error. 
 * TODO: overriding Error.prototype.toString won't accomplish this?
 * ... since natively generated Error objects do not always reflect such things?
 */
function dj_error_to_string(excep){
	return (typeof excep.message !== 'undefined' ? excep.message : (typeof excep.description !== 'undefined' ? excep.description : excep));
}


/**
 * Rethrows the provided Error object excep, with the additional message given by message.
 */
function dj_rethrow(message, excep){
	var emess = dj_error_to_string(excep);
	dj_throw(message + ": " + emess);
}

/**
 * We put eval() in this separate function to keep down the size of the trapped
 * evaluation context.
 *
 * Note that:
 * - JSC eval() takes an optional second argument which can be 'unsafe'.
 * - Mozilla/SpiderMonkey eval() takes an optional second argument which is the scope object for new symbols.
*/
function dj_eval(s){ return dj_global.eval ? dj_global.eval(s) : eval(s); }


/**
 * Convenience for throwing an exception because some function is not implemented.
 */
function dj_unimplemented(funcname, extra){
	var mess = "No implementation of function '" + funcname + "'";
	if((typeof extra != 'undefined')&&(extra)){ mess += " " + extra; }
	mess += " (host environment '" + dojo.hostenv.getName() + "')";
	dj_throw(mess);
}

/**
 * Does inheritance
 */
function dj_inherits(subclass, superclass){
	if(typeof superclass != 'function'){ 
		dj_throw("eek: superclass not a function: " + superclass + "\nsubclass is: " + subclass);
	}
	subclass.prototype = new superclass();
	subclass.prototype.constructor = subclass;
	// TODO: subclass.super = superclass gives JScript error?
	subclass['super'] = superclass;
}

// an object that authors use determine what host we are running under
dojo.render = {
	name: "",
	ver: 0.0,
	os: { win: false, linux: false, osx: false },
	html: {
		capable: false,
		support: {
			builtin: false,
			plugin: false
		},
		ie: false,
		opera: false,
		khtml: false,
		safari: false,
		moz: false
	},
	svg: {
		capable: false,
		support: {
			builtin: false,
			plugin: false
		},
		corel: false,
		adobe: false,
		batik: false
	},
	swf: {
		capable: false,
		support: {
			builtin: false,
			plugin: false
		},
		mm: false
	},
	swt: {
		capable: false,
		support: {
			builtin: false,
			plugin: false
		},
		ibm: false
	}
};


// ****************************************************************
// dojo.hostenv methods that must be defined in hostenv_*.js
// ****************************************************************
//=java public static HostEnv hostenv;
dojo.hostenv = {
	is_debug_ : ((typeof djConfig['isDebug'] == 'undefined') ? false : djConfig['isDebug']),
	base_script_uri_ : ((typeof djConfig['baseScriptUri'] == 'undefined') ? undefined : djConfig['baseScriptUri']),
	base_relative_path_ : ((typeof djConfig['baseRelativePath'] == 'undefined') ? '' : djConfig['baseRelativePath']),
	library_script_uri_ : ((typeof djConfig['libraryScriptUri'] == 'undefined') ? '' : djConfig['libraryScriptUri']),
	auto_build_widgets_ : ((typeof djConfig['parseWidgets'] == 'undefined') ? true : djConfig['parseWidgets']),

	// for recursion protection
	loading_modules_ : {},
	addedToLoadingCount: [],
	removedFromLoadingCount: [],
	inFlightCount: 0,

	// lookup cache for modules.
	// NOTE: this is partially redundant a private variable in the jsdown implementation, but we don't want to couple the two.
	modules_ : {}
};

//=java } /* dojo */


/**
 * The interface definining the interaction with the EcmaScript host environment.
*/
//=java public abstract class HostEnv {

/*
 * None of these methods should ever be called directly by library users.
 * Instead public methods such as loadModule should be called instead.
 */
//=java protected String name_ = '(unset)';
dojo.hostenv.name_ = '(unset)';
dojo.hostenv.version_ = '(unset)';
dojo.hostenv.pkgFileName = "__package__";

/**
 * Return the name of the hostenv.
 */
//=java public abstract String getName() {}
dojo.hostenv.getName = function(){ return this.name_; }

/**
* Return the version of the hostenv.
*/
//=java public abstract String getVersion() {}
dojo.hostenv.getVersion = function(){ return this.version_; }

/**
 * Display a line of text to the user.
 * The line argument should not contain a trailing "\n"; that is added by the implementation.
 */
//=java protected abstract void println();
//dojo.hostenv.println = function(line) {}

/**
 * Read the plain/text contents at the specified uri.
 * If getText() is not implemented, then it is necessary to override loadUri()
 * with an implementation that doesn't rely on it.
 */
//=java protected abstract String getText(String uri);
dojo.hostenv.getText = function(uri){
	dj_unimplemented('dojo.hostenv.getText', "uri=" + uri);
}

/**
 * return the uri of the script that defined this function
 * private method that must be implemented by the hostenv.
 */
//=java protected abstract String getLibraryScriptUri();
dojo.hostenv.getLibraryScriptUri = function(){
	// FIXME: need to implement!!!
	dj_unimplemented('dojo.hostenv.getLibraryScriptUri','');
}

// ****************************************************************
// dojo.hostenv methods not defined in hostenv_*.js
// ****************************************************************

/**
 * Return the base script uri that other scripts are found relative to.
 * It is either the empty string, or a non-empty string ending in '/'.
 */
//=java public String getBaseScriptUri();
dojo.hostenv.getBaseScriptUri = function(){
	if(typeof this.base_script_uri_ != 'undefined'){ return this.base_script_uri_; }
	var uri = this.library_script_uri_;
	if(!uri){
		uri = this.library_script_uri_ = this.getLibraryScriptUri();
		if(!uri){
			dj_throw("Nothing returned by getLibraryScriptUri(): " + uri);
		}
	}

	var lastslash = uri.lastIndexOf('/');
	// inclusive of slash
	// this.base_script_uri_ = this.normPath((lastslash == -1 ? '' : uri.substring(0,lastslash + 1)) + this.base_relative_path_);
	this.base_script_uri_ = this.base_relative_path_;
	return this.base_script_uri_;
}

// FIXME: we should move this into a different namespace
/*
dojo.hostenv.normPath = function(path){
	// FIXME: need to convert or handle windows-style path separators

	// posix says we can have one and two slashes next to each other, but 3 or
	// more should be compressed to a single slash
	path = path.replace(/(\/\/)(\/)+/, "\/");
	// if we've got a "..." sequence, we can should attempt to normalize it
	path = path.replace(/(\.\.)(\.)+/, "..");
	// likewise, we need to clobber "../" sequences at the beginning of our
	// string since they don't mean anything in this context
	path = path.replace(/^(\.)+(\/)/, "");
	// return path;

	// FIXME: we need to fix this for non-rhino clients (say, IE)
	// we need to strip out ".." sequences since rhino can't handle 'em
	if(path.indexOf("..") >= 0){
		var oparts = path.split("/");
		var nparts = [];
		for(var x=0; x<oparts.length; x++){
			if(oparts[x]==".."){
				// FIXME: what about if this is at the front? do we care?
				if(nparts.length){
					nparts.pop();
				}else{
					nparts.push("..");
				}
			}else{
				nparts.push(oparts[x]);
			}
		}
		return nparts.join("/");
	}
}
*/

/**
* Set the base script uri.
*/
//=java public void setBaseScriptUri(String uri);
// In JScript .NET, see interface System._AppDomain implemented by System.AppDomain.CurrentDomain. Members include AppendPrivatePath, RelativeSearchPath, BaseDirectory.
dojo.hostenv.setBaseScriptUri = function(uri){ this.base_script_uri_ = uri }

/**
 * Loads and interprets the script located at relpath, which is relative to the script root directory.
 * If the script is found but its interpretation causes a runtime exception, that exception is not caught
 * by us, so the caller will see it.
 * We return a true value if and only if the script is found.
 *
 * For now, we do not have an implementation of a true search path.
 * We consider only the single base script uri, as returned by getBaseScriptUri().
 *
 * @param relpath A relative path to a script (no leading '/', and typically ending in '.js').
 * @param module A module whose existance to check for after loading a path. Can be used to determine success or failure of the load.
 */
//=java public Boolean loadPath(String relpath);
dojo.hostenv.loadPath = function(relpath, module /*optional*/, cb /*optional*/){
	if(!relpath){
		dj_throw("Missing relpath argument");
	}
	if((relpath.charAt(0) == '/')||(relpath.match(/^\w+:/))){
		dj_throw("Illegal argument '" + relpath + "'; must be relative path");
	}
	var base = this.getBaseScriptUri();
	var uri = base + relpath;
	//this.println("base=" + base + " relpath=" + relpath);
	try{
		var ok;
		if(!module){
			ok = this.loadUri(uri);
		}else{
			ok = this.loadUriAndCheck(uri, module);
		}
		return ok;
	}catch(e){
		if(dojo.hostenv.is_debug_){
			dj_debug(e);
		}
		return false;
	}
}

/**
 * Reads the contents of the URI, and evaluates the contents.
 * Returns true if it succeeded. Returns false if the URI reading failed. Throws if the evaluation throws.
 * The result of the eval is not available to the caller.
 */
dojo.hostenv.loadUri = function(uri, cb){
	if(dojo.hostenv.loadedUris[uri]){
		return;
	}
	var contents = this.getText(uri, null, true);
	if(contents == null){ return 0; }
	var value = dj_eval(contents);
	return 1;
}

dojo.hostenv.getDepsForEval = function(contents){
	// FIXME: should probably memoize this!
	if(!contents){ contents = ""; }
	// check to see if we need to load anything else first. Ugg.
	var deps = [];
	var tmp = contents.match( /dojo.hostenv.loadModule\(.*?\)/mg );
	if(tmp){
		for(var x=0; x<tmp.length; x++){ deps.push(tmp[x]); }
	}
	tmp = contents.match( /dojo.hostenv.require\(.*?\)/mg );
	if(tmp){
		for(var x=0; x<tmp.length; x++){ deps.push(tmp[x]); }
	}
	// FIXME: this seems to be borken on Rhino in some situations. ???
	tmp = contents.match( /dojo.hostenv.conditionalLoadModule\([\w\W]*?\)/gm );
	if(tmp){
		for(var x=0; x<tmp.length; x++){ deps.push(tmp[x]); }
	}

	return deps;
}

dojo.hostenv.getTextStack = [];
dojo.hostenv.loadUriStack = [];
dojo.hostenv.loadedUris = [];

// FIXME: probably need to add logging to this method
dojo.hostenv.loadUriAndCheck = function(uri, module, cb){
	// dj_debug("loadUriAndCheck: "+uri+", "+module);
	var ok = true;
	try{
		ok = this.loadUri(uri, cb);
	}catch(e){
		dj_debug("failed loading ", uri, " with error: ", e);
	}
	return ((ok)&&(this.findModule(module, false))) ? true : false;
}

dojo.hostenv.modulesLoadedFired = false;
dojo.hostenv.modulesLoadedListeners = [];
dojo.hostenv.loaded = function(){
	this.modulesLoadedFired = true;
	var mll = this.modulesLoadedListeners;
	for(var x=0; x<mll.length; x++){
		mll[x]();
	}
}

dojo.hostenv.modulesLoaded = function(){
	if(this.modulesLoadedFired){ return; }
	if((this.loadUriStack.length==0)&&(this.getTextStack.length==0)){
		if(this.inFlightCount > 0){ 
			dj_debug("couldn't initialize, there are files still in flight");
			return;
		}
		this.loaded();
	}
}

/**
* loadModule("A.B") first checks to see if symbol A.B is defined. 
* If it is, it is simply returned (nothing to do).
* If it is not defined, it will look for "A/B.js" in the script root directory, followed
* by "A.js".
* It throws if it cannot find a file to load, or if the symbol A.B is not defined after loading.
* It returns the object A.B.
*
* This does nothing about importing symbols into the current package.
* It is presumed that the caller will take care of that. For example, to import
* all symbols:
*
*    with (dojo.hostenv.loadModule("A.B")) {
*       ...
*    }
*
* And to import just the leaf symbol:
*
*    var B = dojo.hostenv.loadModule("A.B");
*    ...
*
* dj_load is an alias for dojo.hostenv.loadModule
*/
dojo.hostenv.loadModule = function(modulename, exact_only, omit_module_check){
	// alert("dojo.hostenv.loadModule('"+modulename+"');");
	var module = this.findModule(modulename, false);
	if(module){
		return module;
	}

	// dj_debug("dojo.hostenv.loadModule('"+modulename+"');");

	// protect against infinite recursion from mutual dependencies
	if (typeof this.loading_modules_[modulename] !== 'undefined'){
		// NOTE: this should never throw an exception!! "recursive" includes
		// are normal in the course of app and module building, so blow out of
		// it gracefully, but log it in debug mode

		// dj_throw("recursive attempt to load module '" + modulename + "'");
		dj_debug("recursive attempt to load module '" + modulename + "'");
	}else{
		this.addedToLoadingCount.push(modulename);
	}
	this.loading_modules_[modulename] = 1;

	// convert periods to slashes
	var relpath = modulename.replace(/\./g, '/') + '.js';

	var syms = modulename.split(".");
	var nsyms = modulename.split(".");
	if(syms[0]=="dojo"){ // FIXME: need a smarter way to do this!
		syms[0] = "src"; 
	}
	var last = syms.pop();
	syms.push(last);
	// figure out if we're looking for a full package, if so, we want to do
	// things slightly diffrently
	if(last=="*"){
		modulename = (nsyms.slice(0, -1)).join('.');

		var module = this.findModule(modulename, 0);
		if(module){
			return module;
		}

		while(syms.length){
			syms.pop();
			syms.push("__package__");
			relpath = syms.join("/") + '.js';
			if(relpath.charAt(0)=="/"){
				relpath = relpath.slice(1);
			}
			ok = this.loadPath(relpath, ((!omit_module_check) ? modulename : null));
			if(ok){ break; }
			syms.pop();
		}
	}else{
		relpath = syms.join("/") + '.js';
		modulename = nsyms.join('.');
		var ok = this.loadPath(relpath, ((!omit_module_check) ? modulename : null));
		if((!ok)&&(!exact_only)){
			// var syms = modulename.split(/\./);
			syms.pop();
			while(syms.length){
				relpath = syms.join('/') + '.js';
				ok = this.loadPath(relpath, ((!omit_module_check) ? modulename : null));
				if(ok){ break; }
				syms.pop();
				relpath = syms.join('/') + '/__package__.js';
				if(relpath.charAt(0)=="/"){
					relpath = relpath.slice(1);
				}
				ok = this.loadPath(relpath, ((!omit_module_check) ? modulename : null));
				if(ok){ break; }
			}
		}

		if((!ok)&&(!omit_module_check)){
			dj_throw("Could not find module '" + modulename + "'; last tried path '" + relpath + "'");
		}
	}

	// check that the symbol was defined
	if(!omit_module_check){
		module = this.findModule(modulename, false); // pass in false so we can give better error
		if(!module){
			dj_throw("Module symbol '" + modulename + "' is not defined after loading '" + relpath + "'"); 
		}
	}

	return module;
}


function dj_load(modulename, exact_only){
	return dojo.hostenv.loadModule(modulename, exact_only); 
}


/*
 * private utility to  evaluate a string like "A.B" without using eval.
 */
function dj_eval_object_path(objpath){
	// fast path for no periods
	if(typeof objpath != "string"){ return dj_global; }
	if(objpath.indexOf('.') == -1){
		dj_debug("typeof this[",objpath,"]=",typeof(this[objpath]), " and typeof dj_global[]=", typeof(dj_global[objpath])); 
		// dojo.hostenv.println(typeof dj_global[objpath]);
		return (typeof dj_global[objpath] == 'undefined') ? undefined : dj_global[objpath];
	}

	var syms = objpath.split(/\./);
	var obj = dj_global;
	for(var i=0;i<syms.length;++i){
		obj = obj[syms[i]];
		if((typeof obj == 'undefined')||(!obj)){
			return obj;
		}
	}
	return obj;
}

/**
* startPackage("A.B") follows the path, and at each level creates a new empty object
* or uses what already exists. It returns the result.
*/
dojo.hostenv.startPackage = function(packname){
	var syms = packname.split(/\./);
	if(syms[syms.length-1]=="*"){
		syms.pop();
		dj_debug("startPackage: popped a *, new packagename is : ", sysm.join("."));
	}
	var obj = dj_global;
	var objName = "dj_global";
	for(var i=0;i<syms.length;++i){
		var childobj = obj[syms[i]];
		objName += "."+syms[i];
		// if((typeof childobj == 'undefined')||(!childobj)){
		if((eval("typeof "+objName+" == 'undefined'"))||(eval("!"+objName))){
			dj_debug("startPackage: defining: ", syms.slice(0, i+1).join("."));
			obj = dj_global;
			// we'll start with this and move to the commented out eval if that turns out to be faster in testing
			for(var x=0; x<i; x++){
				obj = obj[syms[x]];
			}
			// obj = eval(syms.slice(0, i).join("."));

			obj[syms[i]] = {};
			// eval(objName+" = {};");

			//dj_debug("startPackage: ", objName, " now defined as: ", new String(eval(objName)));
		}
	}
	return obj;
}



/**
 * findModule("A.B") returns the object A.B if it exists, otherwise null.
 * @param modulename A string like 'A.B'.
 * @param must_exist Optional, defualt false. throw instead of returning null if the module does not currently exist.
 */
//=java public Object findModule(String modulename, boolean must_exist);
dojo.hostenv.findModule = function(modulename, must_exist) {
	// check cache
	if(typeof this.modules_[modulename] != 'undefined'){
		return this.modules_[modulename];
	}

	// see if symbol is defined anyway
	var module = dj_eval_object_path(modulename);
	// dj_debug(modulename+": "+module);
	if((typeof module !== 'undefined')&&(module)){
		return this.modules_[modulename] = module;
	}

	if(must_exist){
		dj_throw("no loaded module named '" + modulename + "'");
	}
	return null;
}

//=java } /* class HostEnv */

/**
* @file hostenv_browser.js
*
* Implements the hostenv interface for a browser environment. 
*
* Perhaps it could be called a "dom" or "useragent" environment.
*
* @author Copyright 2004 Mark D. Anderson (mda@discerning.com)
* @author Licensed under the Academic Free License 2.1 http://www.opensource.org/licenses/afl-2.1.php
*/

// make jsc shut up (so we can use jsc to sanity check the code even if it will never run it).
/*@cc_on
@if (@_jscript_version >= 7)
var window; var XMLHttpRequest;
@end
@*/

if(typeof window == 'undefined'){
	dj_throw("no window object");
}

with(dojo.render){
	html.UA = navigator.userAgent;
	html.AV = navigator.appVersion;
	html.capable = true;
	html.support.builtin = true;

	ver = parseFloat(html.AV);
	os.mac = html.AV.indexOf("Macintosh") == -1 ? false : true; 
	os.win = html.AV.indexOf("Windows") == -1 ? false : true; 

	html.opera = html.UA.indexOf("Opera") == -1 ? false : true; 
	html.khtml = ((html.AV.indexOf("Konqueror") >= 0)||(html.AV.indexOf("Safari") >= 0)) ? true : false; 
	html.safari = (html.AV.indexOf("Safari") >= 0) ? true : false; 
	html.moz = ((html.UA.indexOf("Gecko") >= 0)&&(!html.khtml)) ? true : false; 
	html.ie = ((document.all)&&(!html.opera)) ? true : false;
	html.ie50 = html.ie && html.AV.indexOf("MSIE 5.0")>=0;
	html.ie55 = html.ie && html.AV.indexOf("MSIE 5.5")>=0;
	html.ie60 = html.ie && html.AV.indexOf("MSIE 6.0")>=0;

	/*
	// FIXME: need to check for the various SVG plugins and builtin
	// capabilities (as w/ Moz+SVG)
	svg.capable = false;
	// svg.support.plugin = true;
	// svg.support.builtin = false;
	// svg.adobe = true;
	*/
};

// alert(dojo.render.html.safari);

dojo.hostenv.startPackage("dojo.hostenv");

dojo.hostenv.name_ = 'browser';

// These are in order of decreasing likelihood; this will change in time.
var DJ_XMLHTTP_PROGIDS = ['Msxml2.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.4.0'];

dojo.hostenv.getXmlhttpObject = function(){
    var http = null;
	var last_e = null;
	try{ http = new XMLHttpRequest(); }catch(e){}
    if(!http){
		// http = new XMLHttpRequest();
	// }else if(typeof ActiveXObject !== 'undefined') {

		for(var i=0; i<3; ++i){
			var progid = DJ_XMLHTTP_PROGIDS[i];
			try{
				http = new ActiveXObject(progid);
			}catch(e){
				last_e = e;
			}

			if(http){
				DJ_XMLHTTP_PROGIDS = [progid];  // so faster next time
				// dj_debug("successfully made an ActiveXObject using progid ", progid);
				break;
			}else{
				// window.alert("failed new ActiveXObject(" + progid + "): " + e);
			}
		}
	}

	if((last_e)&&(!http)){
		dj_rethrow("Could not create a new ActiveXObject using any of the progids " + DJ_XMLHTTP_PROGIDS.join(', '), last_e);
	}else if(!http){
		return dj_throw("No XMLHTTP implementation available, for uri " + uri);
	}

	return http;
}

/**
 * Read the contents of the specified uri and return those contents.
 *
 * @param uri A relative or absolute uri. If absolute, it still must be in the same "domain" as we are.
 * @param async_cb If not specified, load synchronously. If specified, load asynchronously, and use async_cb as the progress handler which takes the xmlhttp object as its argument. If async_cb, this function returns null.
 * @param fail_ok Default false. If fail_ok and !async_cb and loading fails, return null instead of throwing.
 */ 
dojo.hostenv.getText = function(uri, async_cb, fail_ok){

	var http = this.getXmlhttpObject();

	if(async_cb){
		http.onreadystatechange = function(){ 
			if((4==http.readyState)&&(http["status"])){
				// dojo.hostenv.inFlightCount--;
				// async_cb(((200 == http.status)) ? http.responseText : null);
				if(http.status==200){
					dj_debug("LOADED URI: "+uri);
					async_cb(http.responseText);
				// }else{
				//	dj_debug(http.status+": "+http.statusText+" "+uri);
					// async_cb();
				}
			}
		}
	}

	// dojo.hostenv.inFlightCount++;
	http.open('GET', uri, async_cb ? true : false);
	http.send(null);
	if(async_cb){
		return null;
	}

	/*
	if(http.status != 200){
		if(!fail_ok){
			dj_throw("Request for uri '" + uri + "' resulted in " + http.status + " (" + http.statusText + ")");
			return null;
		}

		if(!http.responseText) {
			if (!fail_ok) dj_throw("Request for uri '" + uri + "' resulted in no responseText");
			return null;
		}
	}
	*/
	return http.responseText;
}

/*
 * It turns out that if we check *right now*, as this script file is being loaded,
 * then the last script element in the window DOM is ourselves.
 * That is because any subsequent script elements haven't shown up in the document
 * object yet.
 */
function dj_last_script_src() {
    var scripts = window.document.getElementsByTagName('script');
    if(scripts.length < 1){ 
		dj_throw("No script elements in window.document, so can't figure out my script src"); 
	}
    var script = scripts[scripts.length - 1];
    var src = script.src;
    if(!src){
		dj_throw("Last script element (out of " + scripts.length + ") has no src");
	}
    return src;
}

if(!dojo.hostenv["library_script_uri_"]){
	dojo.hostenv.library_script_uri_ = dj_last_script_src();
}

// dojo.hostenv.loadUri = function(uri){
	/* FIXME: adding a script element doesn't seem to be synchronous, and so
	 * checking for namespace or object existance after loadUri using this
	 * method will error out. Need to figure out some other way of handling
	 * this!
	 */
	/*
	var se = document.createElement("script");
	se.src = uri;
	var head = document.getElementsByTagName("head")[0];
	head.appendChild(se);
	// document.write("<script type='text/javascript' src='"+uri+"' />");
	return 1;
}
*/

dojo.hostenv.println = function(s){
	try{
		var ti = document.createElement("div");
		document.body.appendChild(ti);
		ti.innerHTML = s;
	}catch(e){
		/*
		try{
			document.write(s);
			// safari needs the output wrapped in an element for some reason
			// document.write("<div>"+s+"</div>");
		}catch(e2){
			window.alert(s);
		}
		*/
	}
}

/*
// NOTE: IE seems to have some really fucked up ideas of what "onload" means
// WRT to pulling things out of the cache and executing them. In this case, we
// hit "onload" and THEN execute the JS that exists below the bootstrap2.js
// include in the page. This might be related to the iframe loading, but there
// isn't any way to tell that from the event that's thrown. Grr.
if(dojo.render.html.ie){
	dojo.hostenv._old_modulesLoaded = dojo.hostenv.modulesLoaded;
	dojo.hostenv.modulesLoaded = function(){
		this.modulesLoadedFired = false;
		this._old_modulesLoaded();
		if(this.modulesLoadedFired){
			this.modulesLoadedListeners = [];
		}
	}
}
*/

// FIXME: this and other Dojo-specific events need a more NW-like attachment mechanism
window.onload = function(evt){
	dojo.hostenv.modulesLoaded();
}

dojo.hostenv.modulesLoadedListeners.push(function(){
	if(dojo.hostenv.auto_build_widgets_){
		if(dj_eval_object_path("dojo.webui.widgets.Parse")){
			try{
				var parser = new dojo.xml.Parse();
				var frag  = parser.parseElement(document.body, null, true);
				var fragParser = new dojo.webui.widgets.Parse(frag);
				fragParser.createComponents(frag);
			}catch(e){
				dj_debug("auto-build-widgets error: "+e);
			}
		}
	}
});

// we assume that we haven't hit onload yet. Lord help us.
// document.write("<iframe style='border: 0px; width: 1px; height: 1px; position: absolute; bottom: 0px; right: 0px; visibility: visible;' name='djhistory' id='djhistory' src='"+((dojo.render.html.moz) ? 'about:blank' : (dojo.hostenv.base_relative_path_+'/blank.html'))+"'></iframe>");
if((!window["djConfig"])&&(!window["djConfig"]["preventBackButtonFix"])){
	document.write("<iframe style='border: 0px; width: 1px; height: 1px; position: absolute; bottom: 0px; right: 0px; visibility: visible;' name='djhistory' id='djhistory' src='"+(dojo.hostenv.getBaseScriptUri()+'/blank.html')+"'></iframe>");
}

/*
 * bootstrap2.js - runs after the hostenv_*.js file.
 */

/*
 * This method taks a "map" of arrays which one can use to optionally load dojo
 * modules. The map is indexed by the possible dojo.hostenv.name_ values, with
 * two additional values: "default" and "common". The items in the "default"
 * array will be loaded if none of the other items have been choosen based on
 * the hostenv.name_ item. The items in the "common" array will _always_ be
 * loaded, regardless of which list is chosen.  Here's how it's normally
 * called:
 *
 *	dojo.hostenv.conditionalLoadModule({
 *		browser: [
 *			["foo.bar.baz", true, true], // an example that passes multiple args to loadModule()
 *			"foo.sample.*",
 *			"foo.test,
 *		],
 *		default: [ "foo.sample.*" ],
 *		common: [ "really.important.module.*" ]
 *	});
 */
dojo.hostenv.conditionalLoadModule = function(modMap){
	var common = modMap["common"]||[];
	var result = (modMap[dojo.hostenv.name_]) ? common.concat(modMap[dojo.hostenv.name_]||[]) : common.concat(modMap["default"]||[]);

	for(var x=0; x<result.length; x++){
		var curr = result[x];
		if(curr.constructor == Array){
			dojo.hostenv.loadModule.apply(dojo.hostenv, curr);
		}else{
			dojo.hostenv.loadModule(curr);
		}
	}
}

dojo.hostenv.require = dojo.hostenv.loadModule;
dojo.hostenv.provide = dojo.hostenv.startPackage;

dj_debug("Using host environment: ", dojo.hostenv.name_);
// print("Using host environment: ", dojo.hostenv.name_);
dj_debug("getBaseScriptUri()=", dojo.hostenv.getBaseScriptUri());
// print("getBaseScriptUri()=", dojo.hostenv.getBaseScriptUri());

dojo.hostenv.startPackage("dojo.io.IO");

/******************************************************************************
 *	Notes about dojo.io design:
 *	
 *	The dojo.io.* package has the unenviable task of making a lot of different
 *	types of I/O feel natural, despite a universal lack of good (or even
 *	reasonable!) I/O capability in the host environment. So lets pin this down
 *	a little bit further.
 *
 *	Rhino:
 *		perhaps the best situation anywhere. Access to Java classes allows you
 *		to do anything one might want in terms of I/O, both synchronously and
 *		async. Can open TCP sockets and perform low-latency client/server
 *		interactions. HTTP transport is available through Java HTTP client and
 *		server classes. Wish it were always this easy.
 *
 *	xpcshell:
 *		XPCOM for I/O. A cluster-fuck to be sure.
 *
 *	spidermonkey:
 *		S.O.L.
 *
 *	Browsers:
 *		Browsers generally do not provide any useable filesystem access. We are
 *		therefore limited to HTTP for moving information to and from Dojo
 *		instances living in a browser.
 *
 *		XMLHTTP:
 *			Sync or async, allows reading of arbitrary text files (including
 *			JS, which can then be eval()'d), writing requires server
 *			cooperation and is limited to HTTP mechanisms (POST and GET).
 *
 *		<iframe> hacks:
 *			iframe document hacks allow browsers to communicate asynchronously
 *			with a server via HTTP POST and GET operations. With significant
 *			effort and server cooperation, low-latency data transit between
 *			client and server can be acheived via iframe mechanisms (repubsub).
 *
 *		SVG:
 *			Adobe's SVG viewer implements helpful primitives for XML-based
 *			requests, but receipt of arbitrary text data seems unlikely w/o
 *			<![CDATA[]]> sections.
 *
 *
 *	A discussion between Dylan, Mark, Tom, and Alex helped to lay down a lot
 *	the IO API interface. A transcript of it can be found at:
 *		http://dojotoolkit.org/viewcvs/viewcvs.py/documents/irc/irc_io_api_log.txt?rev=307&view=auto
 *	
 *	Also referenced in the design of the API was the DOM 3 L&S spec:
 *		http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407/load-save.html
 ******************************************************************************/

// a map of the available transport options. Transports should add themselves
// by calling add(name)
dojo.io.transports = [];
dojo.io.hdlrFuncNames = [ "load", "error" ]; // we're omitting a progress() event for now

dojo.io.Request = function(url, mt, trans, curl){
	this.url = url;
	this.mimetype = mt;
	this.transport = trans;
	this.changeUrl = curl;
	this.formNode = null;
	
	// events stuff
	this.events_ = {};
	
	var Request = this;
	
	this.error = function (type, error) {
		
		switch (type) {
			case "io":
				var errorCode = dojo.io.IOEvent.IO_ERROR;
				var errorMessage = "IOError: error during IO";
				break;
			case "parse":
				var errorCode = dojo.io.IOEvent.PARSE_ERROR;
				var errorMessage = "IOError: error during parsing";
			default:
				var errorCode = dojo.io.IOEvent.UNKOWN_ERROR;
				var errorMessage = "IOError: cause unkown";
		}
		
		var event = new dojo.io.IOEvent("error", null, Request, errorMessage, this.url, errorCode);
		Request.dispatchEvent(event);
		if (Request.onerror) { Request.onerror(errorMessage, Request.url, event); }
	}
	
	this.load = function (type, data, evt) {
		var event = new dojo.io.IOEvent("load", data, Request, null, null, null);
		Request.dispatchEvent(event);
		if (Request.onload) { Request.onload(event); }
	}
	
	this.backButton = function () {
		var event = new dojo.io.IOEvent("backbutton", null, Request, null, null, null);
		Request.dispatchEvent(event);
		if (Request.onbackbutton) { Request.onbackbutton(event); }
	}
	
	this.forwardButton = function () {
		var event = new dojo.io.IOEvent("forwardbutton", null, Request, null, null, null);
		Request.dispatchEvent(event);
		if (Request.onforwardbutton) { Request.onforwardbutton(event); }
	}
	
}

// EventTarget interface
dojo.io.Request.prototype.addEventListener = function (type, func) {
	if (!this.events_[type]) { this.events_[type] = []; }
	
	for (var i = 0; i < this.events_[type].length; i++) {
		if (this.events_[type][i] == func) { return; }
	}
	this.events_[type].push(func);
}

dojo.io.Request.prototype.removeEventListener = function (type, func) {
	if (!this.events_[type]) { return; }
	
	for (var i = 0; i < this.events_[type].length; i++) {
		if (this.events_[type][i] == func) { this.events_[type].splice(i,1); }
	}
}

dojo.io.Request.prototype.dispatchEvent = function (evt) {
	if (!this.events_[evt.type]) { return; }
	for (var i = 0; i < this.events_[evt.type].length; i++) {
		this.events_[evt.type][i](evt);
	}
	return false; // FIXME: implement return value
}

dojo.io.IOEvent = function(type, data, request, errorMessage, errorUrl, errorCode) {	
	// properties
	this.type =  type;
	this.data = data;
	this.request = request;
	this.errorMessage = errorMessage;
	this.errorUrl = errorUrl;
	this.errorCode = errorCode;
}

// constants
dojo.io.IOEvent.UNKOWN_ERROR = 0;
dojo.io.IOEvent.IO_ERROR = 1;
dojo.io.IOEvent.PARSE_ERROR = 2;


dojo.io.Error = function(msg, type, num){
	this.message = msg;
	this.type =  type || "unknown"; // must be one of "io", "parse", "unknown"
	this.number = num || 0; // per-substrate error number, not normalized
}

dojo.io.transports.addTransport = function(name){
	this.push(name);
	// FIXME: do we need to handle things that aren't direct children of the
	// dojo.io namespace? (say, dojo.io.foo.fooTransport?)
	this[name] = dojo.io[name];
}

// binding interface, the various implementations register their capabilities
// and the bind() method dispatches
dojo.io.bind = function(kwArgs){
	// if the request asks for a particular implementation, use it

	// normalize args
	if(!kwArgs["mimetype"]){ kwArgs.mimetype = "text/plain"; }
	if(!kwArgs["method"] && !kwArgs["formNode"]){
		kwArgs.method = "get";
	} else if(kwArgs["formNode"]) {
		kwArgs.method = kwArgs["formNode"].method || "get";
	}
	if(kwArgs["handler"]){ kwArgs.handle = kwArgs.handler; }
	if(!kwArgs["handle"]){ kwArgs.handle = function(){}; }
	if(kwArgs["loaded"]){ kwArgs.load = kwArgs.loaded; }
	if(kwArgs["changeUrl"]) { kwArgs.changeURL = kwArgs.changeUrl; }
	for(var x=0; x<this.hdlrFuncNames.length; x++){
		var fn = this.hdlrFuncNames[x];
		if(typeof kwArgs[fn] == "function"){ continue; }
		if(typeof kwArgs.handler == "object"){
			if(typeof kwArgs.handler[fn] == "function"){
				kwArgs[fn] = kwArgs.handler[fn]||kwArgs.handler["handle"]||function(){};
			}
		}else if(typeof kwArgs["handler"] == "function"){
			kwArgs[fn] = kwArgs.handler;
		}else if(typeof kwArgs["handle"] == "function"){
			kwArgs[fn] = kwArgs.handle;
		}
	}

	var tsName = "";
	if(kwArgs["transport"]){
		tsName = kwArgs["transport"];
		if(!this[tsName]){ return false; /* throw exception? */ }
	}else{
		// otherwise we do our best to auto-detect what available transports
		// will handle 

		// FIXME: should we normalize or set defaults for the kwArgs here?
		for(var x=0; x<dojo.io.transports.length; x++){
			var tmp = dojo.io.transports[x];
			if((this[tmp])&&(this[tmp].canHandle(kwArgs))){
				tsName = tmp;
			}
		}
		if(tsName == ""){ return false; /* throw exception? */ }
	}
	this[tsName].bind(kwArgs);
	return true;
}

dojo.io.argsFromMap = function(map){
	var control = new Object();
	var mapStr = "";
	for(var x in map){
		if(!control[x]){
			mapStr+= encodeURIComponent(x)+"="+encodeURIComponent(map[x])+"&";
		}
	}

	return mapStr;
}

/*
dojo.io.sampleTranport = new function(){
	this.canHandle = function(kwArgs){
		// canHandle just tells dojo.io.bind() if this is a good transport to
		// use for the particular type of request.
		if(	
			(
				(kwArgs["mimetype"] == "text/plain") ||
				(kwArgs["mimetype"] == "text/html") ||
				(kwArgs["mimetype"] == "text/javascript")
			)&&(
				(kwArgs["method"] == "get") ||
				( (kwArgs["method"] == "post") && (!kwArgs["formNode"]) )
			)
		){
			return true;
		}

		return false;
	}

	this.bind = function(kwArgs){
		var hdlrObj = {};

		// set up a handler object
		for(var x=0; x<dojo.io.hdlrFuncNames.length; x++){
			var fn = dojo.io.hdlrFuncNames[x];
			if(typeof kwArgs.handler == "object"){
				if(typeof kwArgs.handler[fn] == "function"){
					hdlrObj[fn] = kwArgs.handler[fn]||kwArgs.handler["handle"];
				}
			}else if(typeof kwArgs[fn] == "function"){
				hdlrObj[fn] = kwArgs[fn];
			}else{
				hdlrObj[fn] = kwArgs["handle"]||function(){};
			}
		}

		// build a handler function that calls back to the handler obj
		var hdlrFunc = function(evt){
			if(evt.type == "onload"){
				hdlrObj.load("load", evt.data, evt);
			}else if(evt.type == "onerr"){
				var errObj = new dojo.io.Error("sampleTransport Error: "+evt.msg);
				hdlrObj.error("error", errObj);
			}
		}

		// the sample transport would attach the hdlrFunc() when sending the
		// request down the pipe at this point
		var tgtURL = kwArgs.url+"?"+dojo.io.argsFromMap(kwArgs.content);
		// sampleTransport.sendRequest(tgtURL, hdlrFunc);
	}

	dojo.io.transports.addTransport("sampleTranport");
}
*/

dojo.hostenv.startPackage("dojo.alg.Alg");

dojo.alg.find = function(arr, val){
	for(var i=0;i<arr.length;++i){
		if(arr[i] == val){ return i; }
	}
	return -1;
}

dojo.alg.inArray = function(arr, val){
	// support both (arr, val) and (val, arr)
	if( (!arr || arr.constructor != Array) && (val && val.constructor == Array) ) {
		var a = arr;
		arr = val;
		val = a;
	}
	return dojo.alg.find(arr, val) > -1;
}
dojo.alg.inArr = dojo.alg.inArray; // for backwards compatibility

dojo.alg.getNameInObj = function(ns, item){
	if(!ns){ ns = dj_global; }

	for(var x in ns){
		if(ns[x] === item){
			return new String(x);
		}
	}
	return null;
}

// is this the right place for this?
dojo.alg.has = function(obj, name){
	return (typeof obj[name] !== 'undefined');
}

dojo.alg.forEach = function(arr, unary_func){
	for(var i=0; i<arr.length; i++){
		unary_func(arr[i]);
	}
}

dojo.alg.for_each = dojo.alg.forEach; // burst compat

dojo.alg.map = function(arr, obj, unary_func){
	for(var i=0;i<arr.length;++i){
		unary_func.call(obj, arr[i]);
	}
}

dojo.alg.for_each_call = dojo.alg.map; // burst compat

dojo.hostenv.loadModule("dojo.alg.Alg", false, true);

dojo.hostenv.loadModule("dojo.alg.*");

dojo.hostenv.startPackage("dojo.event.Event");

dojo.event = new function(){

	var anonCtr = 0;
	this.anon = {};

	this.nameAnonFunc = function(anonFuncPtr, namespaceObj){
		var ret = "_"+anonCtr++;
		var nso = (namespaceObj || this.anon);
		while(typeof nso[ret] != "undefined"){
			ret = "_"+anonCtr++;
		}
		nso[ret] = anonFuncPtr;
		return ret;
	}

	this.createFunctionPair = function(obj, cb) {
		var ret = [];
		if(typeof obj == "function"){
			ret[1] = dojo.event.nameAnonFunc(obj, dj_global);
			ret[0] = dj_global;
			return ret;
		}else if((typeof obj == "object")&&(typeof cb == "string")){
			return [obj, cb];
		}else if((typeof obj == "object")&&(typeof cb == "function")){
			ret[1] = dojo.event.nameAnonFunc(cb, obj);
			ret[0] = obj;
			return ret;
		}
		return null;
	}

	// FIXME: where should we put this method (not here!)?
	this.matchSignature = function(args, signatureArr){

		var end = Math.min(args.length, signatureArr.length);

		for(var x=0; x<end; x++){
			// FIXME: this is naive comparison, can we do better?
			if(compareTypes){
				if((typeof args[x]).toLowerCase() != (typeof signatureArr[x])){
					return false;
				}
			}else{
				if((typeof args[x]).toLowerCase() != signatureArr[x].toLowerCase()){
					return false;
				}
			}
		}

		return true;
	}

	// FIXME: where should we put this method (not here!)?
	this.matchSignatureSets = function(args){
		for(var x=1; x<arguments.length; x++){
			if(this.matchSignature(args, arguments[x])){
				return true;
			}
		}
		return false;
	}

	function interpolateArgs(args){
		var ao = {
			srcObj: dj_global,
			srcFunc: null,
			adviceObj: dj_global,
			adviceFunc: null,
			aroundObj: null,
			aroundFunc: null,
			adviceType: (args.length>2) ? args[0] : "after",
			precedence: "last",
			once: false,
			delay: null
		};

		switch(args.length){
			case 0: return;
			case 1: return;
			case 2:
				ao.srcObj = ao.adviceObj = dj_global;
				ao.srcFunc = args[0];
				ao.adviceFunc = args[1];
				break;
			case 3:
				if((typeof args[1] == "string")&&(typeof args[2] == "string")){
					ao.srcFunc = args[1];
					ao.adviceFunc = args[2];
				}else if((typeof args[0] == "object")&&(typeof args[1] == "string")&&(typeof args[2] == "function")){
					ao.adviceType = "after";
					ao.srcObj = args[0];
					ao.srcFunc = args[1];
					var tmpName  = dojo.event.nameAnonFunc(args[2], ao.adviceObj);
					ao.adviceObj[tmpName] = args[2];
					ao.adviceFunc = tmpName;
				}else if((typeof args[0] == "function")&&(typeof args[1] == "object")&&(typeof args[2] == "string")){
					ao.adviceType = "after";
					ao.srcObj = dj_global;
					var tmpName  = dojo.event.nameAnonFunc(args[0], ao.srcObj);
					ao.srcObj[tmpName] = args[0];
					ao.srcFunc = tmpName;
					ao.adviceObj = args[1];
					ao.adviceFunc = args[2];
				}
				break;
			case 4:
				if((typeof args[0] == "object")&&(typeof args[2] == "object")){
					// we can assume that we've got an old-style "connect" from
					// the sigslot school of event attachment. We therefore
					// assume after-advice.
					ao.adviceType = "after";
					ao.srcObj = args[0];
					ao.srcFunc = args[1];
					ao.adviceObj = args[2];
					ao.adviceFunc = args[3];
				}else if((typeof args[1]).toLowerCase() == "object"){
					ao.srcObj = args[1];
					ao.srcFunc = args[2];
					ao.adviceObj = dj_global;
					ao.adviceFunc = args[3];
				}else if((typeof args[2]).toLowerCase() == "object"){
					ao.srcObj = dj_global;
					ao.srcFunc = args[1];
					ao.adviceObj = args[2];
					ao.adviceFunc = args[3];
				}else{
					ao.srcObj = ao.adviceObj = ao.aroundObj = dj_global;
					ao.srcFunc = args[1];
					ao.adviceFunc = args[2];
					ao.aroundFunc = args[3];
				}
				break;
			case 6:
				ao.srcObj = args[1];
				ao.srcFunc = args[2];
				ao.adviceObj = args[3]
				ao.adviceFunc = args[4];
				ao.aroundFunc = args[5];
				ao.aroundObj = dj_global;
				break;
			default:
				ao.srcObj = args[1];
				ao.srcFunc = args[2];
				ao.adviceObj = args[3]
				ao.adviceFunc = args[4];
				ao.aroundObj = args[5];
				ao.aroundFunc = args[6];
				ao.once = args[7];
				ao.delay = args[8];
				break;
		}

		if((typeof ao.srcFunc).toLowerCase() != "string"){
			ao.srcFunc = dojo.alg.getNameInObj(ao.srcObj, ao.srcFunc);
		}

		if((typeof ao.adviceFunc).toLowerCase() != "string"){
			ao.adviceFunc = dojo.alg.getNameInObj(ao.adviceObj, ao.adviceFunc);
		}

		if((ao.aroundObj)&&((typeof ao.aroundFunc).toLowerCase() != "string")){
			ao.aroundFunc = dojo.alg.getNameInObj(ao.aroundObj, ao.aroundFunc);
		}

		return ao;
	}

	this.connect = function(){
		var ao = interpolateArgs(arguments);

		// FIXME: just doing a "getForMethod()" seems to be enough to put this into infinite recursion!!
		var mjp = dojo.event.MethodJoinPoint.getForMethod(ao.srcObj, ao.srcFunc);
		if(ao.adviceFunc){
			var mjp2 = dojo.event.MethodJoinPoint.getForMethod(ao.adviceObj, ao.adviceFunc);
		}

		mjp.kwAddAdvice(ao);

		return mjp;	// advanced users might want to fsck w/ the join point
					// manually
	}

	this.kwConnectImpl_ = function(kwArgs, disconnect){
		var fn = (disconnect) ? "disconnect" : "connect";
		return dojo.event[fn](	(kwArgs["type"]||kwArgs["adviceType"]||"after"),
									kwArgs["srcObj"],
									kwArgs["srcFunc"],
									kwArgs["adviceObj"],
									kwArgs["adviceFunc"],
									kwArgs["aroundObj"],
									kwArgs["aroundFunc"],
									kwArgs["once"],
									kwArgs["delay"]);
	}

	this.kwConnect = function(kwArgs){
		return this.kwConnectImpl_(kwArgs, false);

	}

	this.disconnect = function(){
		var ao = interpolateArgs(arguments);
		if(!ao.adviceFunc){ return; } // nothing to disconnect
		var mjp = dojo.event.MethodJoinPoint.getForMethod(ao.srcObj, ao.srcFunc);
		return mjp.removeAdvice(ao.adviceObj, ao.adviceFunc, ao.adviceType, ao.once);
	}

	this.kwDisconnect = function(kwArgs){
		return this.kwConnectImpl_(kwArgs, true);
	}

}

// exactly one of these is created whenever a method with a joint point is run,
// if there is at least one 'around' advice.
dojo.event.MethodInvocation = function(join_point, obj, args) {
	this.jp_ = join_point;
	this.object = obj;
	this.args = [];
	for(var x=0; x<args.length; x++){
		this.args[x] = args[x];
	}
	// the index of the 'around' that is currently being executed.
	this.around_index = -1;
}

dojo.event.MethodInvocation.prototype.proceed = function() {
	// dojo.hostenv.println("in MethodInvocation.proceed()");
	this.around_index++;
	if(this.around_index >= this.jp_.around.length){
		return this.jp_.object[this.jp_.methodname].apply(this.jp_.object, this.args);
		// return this.jp_.run_before_after(this.object, this.args);
	}else{
		var ti = this.jp_.around[this.around_index];
		var mobj = ti[0]||dj_global;
		var meth = ti[1];
		return mobj[meth].call(mobj, this);
	}
} 


dojo.event.MethodJoinPoint = function(obj, methname){
	this.object = obj||dj_global;
	this.methodname = methname;
	this.methodfunc = this.object[methname];
	this.before = [];
	this.after = [];
	this.around = [];
}

dojo.event.MethodJoinPoint.getForMethod = function(obj, methname) {
	// if(!(methname in obj)){
	if(!obj){ obj = dj_global; }
	if(!obj[methname]){
		// supply a do-nothing method implementation
		obj[methname] = function(){};
	}else if(typeof obj[methname] != "function"){
		return null; // FIXME: should we throw an exception here instead?
	}
	// we hide our joinpoint instance in obj[methname + '$joinpoint']
	var jpname = methname + "$joinpoint";
	var jpfuncname = methname + "$joinpoint$method";
	var joinpoint = obj[jpname];
	if(!joinpoint){
		var isNode = false;
		if(dojo.event["browser"]){
			if((obj["attachEvent"])||(obj["nodeType"])||(obj["addEventListener"])){
				isNode = true;
				dojo.event.browser.addClobberAttrs(jpname, jpfuncname, methname);
			}
		}
		obj[jpfuncname] = obj[methname];
		// joinpoint = obj[jpname] = new dojo.event.MethodJoinPoint(obj, methname);
		joinpoint = obj[jpname] = new dojo.event.MethodJoinPoint(obj, jpfuncname);
		obj[methname] = function(){ 
			var args = [];

			if((isNode)&&(!arguments.length)&&(window.event)){
				args.push(dojo.event.browser.fixEvent(window.event));
			}else{
				for(var x=0; x<arguments.length; x++){
					if((x==0)&&(isNode)){
						args.push(dojo.event.browser.fixEvent(arguments[x]));
					}else{
						args.push(arguments[x]);
					}
				}
			}
			// return joinpoint.run.apply(joinpoint, arguments); 
			return joinpoint.run.apply(joinpoint, args); 
		}
	}
	// dojo.hostenv.println("returning joinpoint");
	return joinpoint;
}

dojo.event.MethodJoinPoint.prototype.unintercept = function() {
	this.object[this.methodname] = this.methodfunc;
}

dojo.event.MethodJoinPoint.prototype.run = function() {
	// dojo.hostenv.println("in run()");
	var obj = this.object||dj_global;
	var args = arguments;

	// optimization. We only compute once the array version of the arguments
	// pseudo-arr in order to prevent building it each time advice is unrolled.
	var aargs = [];
	// NOTE: alex: I think this is safe since we apply() these args anyway, so
	// primitive types will be copied at the call boundary anyway, and
	// references to objects would be mutable anyway.
	for(var x=0; x<args.length; x++){
		aargs[x] = args[x];
	}

	var unrollAdvice  = function(marr){ 
		// dojo.hostenv.println("in unrollAdvice()");
		var callObj = marr[0]||dj_global;
		var callFunc = marr[1];
		var aroundObj = marr[2]||dj_global;
		var aroundFunc = marr[3];
		var undef;
		var delay = parseInt(marr[4]);
		var hasDelay = ((!isNaN(delay))&&(marr[4]!==null)&&(typeof marr[4] != "undefined"));
		var to = {
			args: [],
			jp_: this,
			object: obj,
			proceed: function(){
				return callObj[callFunc].apply(callObj, to.args);
			}
		};

		/*
		// FIXME: how slow is this? Is there a better/faster way to get this
		// done?
		// FIXME: is it necessaray to make this copy every time that
		// unrollAdvice gets called? Would it be better/possible to handle it
		// in run() where we make args in the first place?
		for(var x=0; x<args.length; x++){
			to.args[x] = args[x];
		}
		*/
		to.args = aargs;

		if(aroundFunc){
			// NOTE: around advice can't delay since we might otherwise depend
			// on execution order!
			aroundObj[aroundFunc].call(aroundObj, to);
		}else{
			// var tmjp = dojo.event.MethodJoinPoint.getForMethod(obj, methname);
			if((hasDelay)&&((dojo.render.html)||(dojo.render.svg))){  // FIXME: the render checks are grotty!
				dj_global["setTimeout"](function(){
					callObj[callFunc].apply(callObj, args); 
				}, delay);
			}else{ // many environments can't support delay!
				callObj[callFunc].apply(callObj, args); 
			}
		}
	}

	if(this.before.length>0){
		dojo.alg.forEach(this.before, unrollAdvice);
	}

	var result;
	if(this.around.length>0){
		var mi = new dojo.event.MethodInvocation(this, obj, args);
		result = mi.proceed();
	}else if(this.methodfunc){
		// dojo.hostenv.println("calling: "+this.methodname)
		result = this.object[this.methodname].apply(this.object, args);
	}

	if(this.after.length>0){
		dojo.alg.forEach(this.after, unrollAdvice);
	}

	return (this.methodfunc) ? result : null;
}

dojo.event.MethodJoinPoint.prototype.getArr = function(kind){
	var arr = this.after;
	// FIXME: we should be able to do this through props or Array.in()
	if((typeof kind == "string")&&(kind.indexOf("before")!=-1)){
		arr = this.before;
	}else if(kind=="around"){
		arr = this.around;
	}
	return arr;
}

dojo.event.MethodJoinPoint.prototype.kwAddAdvice = function(args){
	this.addAdvice(	args["adviceObj"], args["adviceFunc"], 
					args["aroundObj"], args["aroundFunc"], 
					args["adviceType"], args["precedence"], 
					args["once"], args["delay"]);
}

dojo.event.MethodJoinPoint.prototype.addAdvice = function(	thisAdviceObj, thisAdvice, 
															thisAroundObj, thisAround, 
															advice_kind, precedence, 
															once, delay){
	var arr = this.getArr(advice_kind);
	if(!arr){
		dj_throw("bad this: " + this);
	}

	var ao = [thisAdviceObj, thisAdvice, thisAroundObj, thisAround, delay];
	
	if(once){
		if(this.hasAdvice(thisAdviceObj, thisAdvice, advice_kind, arr) >= 0){
			return;
		}
	}

	if(precedence == "first"){
		arr.unshift(ao);
	}else{
		arr.push(ao);
	}
}

dojo.event.MethodJoinPoint.prototype.hasAdvice = function(thisAdviceObj, thisAdvice, advice_kind, arr){
	if(!arr){ arr = this.getArr(advice_kind); }
	var ind = -1;
	for(var x=0; x<arr.length; x++){
		if((arr[x][0] == thisAdviceObj)&&(arr[x][1] == thisAdvice)){
			ind = x;
		}
	}
	return ind;
}

dojo.event.MethodJoinPoint.prototype.removeAdvice = function(thisAdviceObj, thisAdvice, advice_kind, once){
	var arr = this.getArr(advice_kind);
	var ind = this.hasAdvice(thisAdviceObj, thisAdvice, advice_kind, arr);
	if(ind == -1){
		return false;
	}
	while(ind != -1){
		arr.splice(ind, 1);
		if(once){ break; }
		ind = this.hasAdvice(thisAdviceObj, thisAdvice, advice_kind, arr);
	}
	return true;
}

// needed for package satisfaction
dojo.hostenv.startPackage("dojo.event.Event");

/* Copyright (c) 2004-2005 The Dojo Foundation, Licensed under the Academic Free License version 2.1 or above */
dojo.hostenv.startPackage("dojo.event.Topic");
dojo.hostenv.loadModule("dojo.event.Event");

dojo.event.Topic = {};

dojo.event.topic = new function(){
	this.topics = {};

	this.getTopic = function(topicName){
		if(!this.topics[topicName]){
			this.topics[topicName] = new this.TopicImpl(topicName);
		}
		return this.topics[topicName];
	}

	this.subscribe = function(topic, obj, funcName){
		var topic = this.getTopic(topic);
		topic.subscribe(obj, funcName);
	}

	this.unsubscribe = function(topic, obj, funcName){
		var topic = this.getTopic(topic);
		topic.subscribe(obj, funcName);
	}

	this.publish = function(topic, message){
		var topic = this.getTopic(topic);
		// if message is an array, we treat it as a set of arguments,
		// otherwise, we just pass on the arguments passed in as-is
		var args = [];
		if((arguments.length == 2)&&(message.length)&&(typeof message != "string")){
			args = message;
		}else{
			var args = [];
			for(var x=1; x<arguments.length; x++){
				args.push(arguments[x]);
			}
		}
		topic.sendMessage.apply(topic, args);
	}
}

dojo.event.topic.TopicImpl = function(topicName){
	this.topicName = topicName;
	var self = this;

	self.subscribe = function(listenerObject, listenerMethod){
		dojo.event.connect("before", self, "sendMessage", listenerObject, listenerMethod);
	}

	self.unsubscribe = function(listenerObject, listenerMethod){
		dojo.event.disconnect("before", self, "sendMessage", listenerObject, listenerMethod);
	}

	self.registerPublisher = function(publisherObject, publisherMethod){
		dojo.event.connect(publisherObject, publisherMethod, self, "sendMessage");
	}

	self.sendMessage = function(message){
		// The message has been propagated
	}
}


dojo.hostenv.startPackage("dojo.event.BrowserEvent");
dojo.event.browser = {};

dojo.hostenv.loadModule("dojo.event.Event");

dojo_ie_clobber = new function(){
	this.clobberArr = ['data', 
		'onload', 'onmousedown', 'onmouseup', 
		'onmouseover', 'onmouseout', 'onmousemove', 
		'onclick', 'ondblclick', 'onfocus', 
		'onblur', 'onkeypress', 'onkeydown', 
		'onkeyup', 'onsubmit', 'onreset',
		'onselect', 'onchange', 'onselectstart', 
		'ondragstart', 'oncontextmenu'];

	this.exclusions = [];
	
	this.clobberList = {};

	this.addClobberAttr = function(type){
		if(dojo.render.html.ie){
			if((!this.clobberList[type])||
				(this.clobberList[type]!="set")){
				this.clobberArr.push(type);
				this.clobberList[type] = "set"; 
			}
		}
	}

	this.addExclusionID = function(id){
		this.exclusions.push(id);
	}

	if(dojo.render.html.ie){
		for(var x=0; x<this.clobberArr.length; x++){
			this.clobberList[this.clobberArr[x]] = "set";
		}
	}

	this.clobber = function(){
		// var init = new Date();
		// var stripctr = 0;
		for(var x=0; x< this.exclusions.length; x++){
			try{
				var tn = document.getElementById(this.exclusions[x]);
				tn.parentNode.removeChild(tn);
			}catch(e){
				// this is fired on unload, so squelch
			}
		}

		var na = document.all;
		for(var i = na.length-1; i>=0; i=i-1){
			var el = na[i];
			for(var p = this.clobberArr.length-1; p>=0; p=p-1){
				// stripctr++;
				el[this.clobberArr[p]] = null;
			}
		}
		// alert("clobbering took: "+((new Date())-init)+"ms\nwe removed: "+stripctr+" properties");
	}
}

if(dojo.render.html.ie){
	window.onunload = function(){
		dojo_ie_clobber.clobber();
	}
}

dojo.event.browser = new function(){
	this.addClobberAttr = function(type){
		dojo_ie_clobber.addClobberAttr(type);
	}

	this.addClobberAttrs = function(){
		for(var x=0; x<arguments.length; x++){
			this.addClobberAttr(arguments[x]);
		}
	}

	/*
	this.eventAroundAdvice = function(methodInvocation){
		var evt = this.fixEvent(methodInvocation.args[0]);
		return methodInvocation.proceed();
	}
	*/

	this.addListener = function(node, evtName, fp, capture){
		if(!capture){ var capture = false; }
		evtName = evtName.toLowerCase();
		if(evtName.substr(0,2)=="on"){ evtName = evtName.substr(2); }
		if(!node){ return; } // FIXME: log and/or bail?

		// build yet another closure around fp in order to inject fixEvent
		// around the resulting event
		var newfp = function(evt){
			if(!evt){ evt = window.event; }
			var ret = fp(dojo.event.browser.fixEvent(evt));
			if(capture){
				dojo.event.browser.stopEvent(evt);
			}
			return ret;
		}

		if(node.attachEvent){
			// NW_attachEvent_list.push([node, 'on' + evtName, fp]);
			node.attachEvent("on"+evtName, newfp);
		}else if(node.addEventListener){ // &&(!__is__.konq)){ // Konq 3.1 tries to implement this, but it seems to be broken
			node.addEventListener(evtName, newfp, capture);
			return true;
		}else{
			// NW_expando_list.push([node, 'on' + evtName]);
			// there's probably "better" anti-clobber algs, but this should only ever have
			// to happen once, so it should suffice
			if( typeof node["on"+evtName] == "function" ) {
				var oldEvt = node["on"+evtName];
				node["on"+evtName] = function(e) {
					oldEvt(e);
					newfp(e);
				}
			} else {
				node["on"+evtName]=newfp;
			}
			return true;
		}
	}

	this.fixEvent = function(evt){
		/* From the requirements doc:

		  Given that normalization of event properties is a goal, the
		  properties that should be normalized for all DOM events are:

			+ type
			+ target
			+ currentTarget
			+ relatedTarget

		  Methods that must be globally available on the event object include:

			+ preventDefault()
			+ stopPropagation()
		*/
		if(dojo.render.html.ie){
			return new dojo.event.IEEvent(evt||window["event"]);
		}
		return evt;
	}

	this.stopEvent = function(ev) {
		if(window.event){
			ev.returnValue = false;
			ev.cancelBubble = true;
		}else{
			ev.preventDefault();
			ev.stopPropagation();
		}
	}
}

dojo.event.IEEvent = function(evt){
	for(var prop in evt) {
		if(!this[prop]) {
			this[prop] = evt[prop];
		}
	}
	// this class is mainly taken from Burst's WindowEvent.js
	this.ie_event_ = evt;
	this.target = evt.srcElement;
	this.type = evt.type;

	// keyCode is not standardized in any w3 API yet (Level 3 Events is in draft).
	// some browsers store it in 'which'
	if(dojo.alg.has('keyCode', evt)){
		this.keyCode = ev.keyCode;
	}

	// below are all for MouseEvent.
	var this_obj = this;
	// these are the same in both
	dojo.alg.forEach(['shiftKey', 'altKey', 'ctrlKey', 'metaKey'], 
		function(k){
			if(dojo.alg.has(evt, k)){ 
				this_obj[k] = evt[k];
			} 
		}
	);

	if(typeof evt.button != 'undefined'){
		// these are different in interpretation, but we copy them anyway
		dojo.alg.forEach(['button', 'screenX', 'screenY', 'clientX', 'clientY'], 
			function(k){
				if(dojo.alg.has(evt, k)){
					this_obj[k] = evt[k];
				}
			}
		);
	}

	// mouseover
	if(evt.fromElement){ this.relatedTarget = evt.fromElement; }
	// mouseout
	if(evt.toElement){ this.relatedTarget = evt.toElement; }

	this.callListener = function(listener, curTarget){
		if(typeof listener != 'function'){
			dj_throw("listener not a function: " + listener);
		}
		this.currentTarget = curTarget;
		var ret = listener.call(curTarget, this);
		return ret;
	}

	this.stopPropagation = function(){
		this.ie_event_.cancelBubble = true;
	}

	this.preventDefault = function(){
	  this.ie_event_.returnValue = false;
	}

}

dojo.hostenv.conditionalLoadModule({
	common: ["dojo.event.Event", "dojo.event.Topic"],
	browser: ["dojo.event.BrowserEvent"]
});

dojo.hostenv.startPackage("dojo.io.BrowserIO");

dojo.hostenv.loadModule("dojo.io.IO");
dojo.hostenv.loadModule("dojo.alg.*");

dojo.io.checkChildrenForFile = function(node){
	var hasTrue = false;
	for(var x=0; x<node.childNodes.length; x++){
		if(node.nodeType==1){
			if(node.nodeName.toLowerCase() == "input"){
				if(node.getAttribute("type")=="file"){
					return true;
				}
			}

			if(node.childNodes.length){
				for(var x=0; x<node.childNodes.length; x++){
					if(dojo.io.checkChildrenForFile(node.childNodes.item(x))){
						return true;
					}
				}
			}
		}
	}
	return false;
}

dojo.io.formHasFile = function(formNode){
	return dojo.io.checkChildrenForFile(formNode);
}

dojo.io.buildFormGetString = function(sNode){
	// FIXME: we should probably be building an array and then join()-ing to
	// make this fast for large forms
	var ec = encodeURIComponent;
	//the argument is a DOM Node corresponding to a form element.
	var tvar = "";
	var ctyp = sNode.nodeName ? sNode.nodeName.toLowerCase() : "";
	var etyp = sNode.type ? sNode.type.toLowerCase() : "";
	if( ( (ctyp=="input") && (etyp!="radio") && (etyp!="checkbox") ) || (ctyp=="select") || (ctyp=="textarea")){
		if((ctyp=='input') && (etyp=='submit')){
			// we shouldn't be adding values of submit buttons, so ommit them here
		}else if(!((ctyp=="select")&&(sNode.getAttribute("multiple")))){
			tvar = ec(sNode.getAttribute("name")) + "=" + ec(sNode.value) + "&";
		}else{
			// otherwise we have a multi-select element, so gather all of it's values
			var tn = ec(sNode.getAttribute("name")); 
			var copts = sNode.getElementsByTagName("option");
			for(var x=0; x<copts.length; x++){
				if(copts[x].selected){
					tvar += tn+"="+ec(copts[x].value)+"&";
				}
			}
		}
	}else if(ctyp=="input"){
		if(sNode.checked){
			tvar = ec(sNode.getAttribute("name")) + "=" + ec(sNode.value)  + "&";
		}
	}
	if(sNode.hasChildNodes()){
		for(var temp_count=(sNode.childNodes.length-1); temp_count >= 0; temp_count--){
			tvar += dojo.io.buildFormGetString(sNode.childNodes.item(temp_count));
		}
	}
	return tvar;
}

dojo.io.setIFrameSrc = function(iframe, src, replace){
	try{
		var r = dojo.render.html;
		// dj_debug(iframe);
		if(!replace){
			if(r.safari){
				iframe.location = src;
			}else{
				frames[iframe.name].location = src;
			}
		}else{
			var idoc = (r.moz) ? iframe.contentWindow : iframe;
			idoc.location.replace(src);
			dj_debug(iframe.contentWindow.location);
		}
	}catch(e){ alert(e); }
}

dojo.io.createIFrame = function(fname){
	if(window[fname]){ return window[fname]; }
	if(window.frames[fname]){ return window.frames[fname]; }
	var r = dojo.render.html;
	var cframe = null;
	cframe = document.createElement((((r.ie)&&(r.win)) ? "<iframe name="+fname+">" : "iframe"));
	with(cframe){
		name = fname;
		setAttribute("name", fname);
		id = fname;
	}
	window[fname] = cframe;
	document.body.appendChild(cframe);
	with(cframe.style){
		position = "absolute";
		left = top = "0px";
		height = width = "1px";
		visibility = "hidden";
		if(dojo.hostenv.is_debug_){
			position = "relative";
			height = "100px";
			width = "300px";
			visibility = "visible";
		}
	}

	// FIXME: do we need to (optionally) squelch onload?
	
	dojo.io.setIFrameSrc(cframe, dojo.hostenv.getBaseScriptUri()+"/blank.html", true);
	return cframe;
}


dojo.io.cancelDOMEvent = function(evt){
	if(!evt){ return false; }
	if(evt.preventDefault){
		evt.stopPropagation();
		evt.preventDefault();
	}else{
		if(window.event){
			window.event.cancelBubble = true;
			window.event.returnValue = false;
		}
	}
	return false;
}

dojo.io.XMLHTTPTransport = new function(){
	var _this = this;

	this.initialHref = window.location.href;
	this.initialHash = window.location.hash;

	this.moveForward = false;

	var _cache = {}; // FIXME: make this public? do we even need to?
	this.useCache = false; // if this is true, we'll cache unless kwArgs.useCache = false
	this.historyStack = [];
	this.forwardStack = [];
	this.historyIframe = null;
	this.bookmarkAnchor = null;
	this.locationTimer = null;

	/* NOTES:
	 *	Safari 1.2: 
	 *		back button "works" fine, however it's not possible to actually
	 *		DETECT that you've moved backwards by inspecting window.location.
	 *		Unless there is some other means of locating.
	 *		FIXME: perhaps we can poll on history.length?
	 *	IE 5.5 SP2:
	 *		back button behavior is macro. It does not move back to the
	 *		previous hash value, but to the last full page load. This suggests
	 *		that the iframe is the correct way to capture the back button in
	 *		these cases.
	 *	IE 6.0:
	 *		same behavior as IE 5.5 SP2
	 * Firefox 1.0:
	 *		the back button will return us to the previous hash on the same
	 *		page, thereby not requiring an iframe hack, although we do then
	 *		need to run a timer to detect inter-page movement.
	 */

	// FIXME: Should this even be a function? or do we just hard code it in the next 2 functions?
	function getCacheKey(url, query, method) {
		return url + "|" + query + "|" + method.toLowerCase();
	}

	function addToCache(url, query, method, http) {
		_cache[getCacheKey(url, query, method)] = http;
	}

	function getFromCache(url, query, method) {
		return _cache[getCacheKey(url, query, method)];
	}

	this.clearCache = function() {
		_cache = {};
	}

	// moved successful load stuff here
	function doLoad(kwArgs, http, url, query, useCache) {
		if(http.status==200) {
			var ret;
			if(kwArgs.mimetype == "text/javascript") {
				ret = dj_eval(http.responseText);
			} else if(kwArgs.mimetype == "text/xml") {
				ret = http.responseXML;
				if(!ret || typeof ret == "string") {
					ret = dojo.xml.domUtil.parseXmlString(http.responseText);
				}
			} else {
				ret = http.responseText;
			}

			if( useCache ) { // only cache successful responses
				addToCache(url, query, kwArgs.method, http);
			}
			if( typeof kwArgs.load == "function" ) {
				kwArgs.load("load", ret, http);
			} else if( typeof kwArgs.handle == "function" ) {
				kwArgs.handle("load", ret, http);
			}
		} else {
			var errObj = new dojo.io.Error("XMLHttpTransport Error: "+http.status+" "+http.statusText);
			if( typeof kwArgs.error == "function" ) {
				kwArgs.error("error", errObj);
			} else if( typeof kwArgs.handle == "function" ) {
				kwArgs.handle("error", errObj, errObj);
			}
		}
	}

	this.addToHistory = function(args){
		var callback = args["back"]||args["backButton"]||args["handle"];
		var hash = null;
		if(!this.historyIframe){
			this.historyIframe = window.frames["djhistory"];
		}
		if(!this.bookmarkAnchor){
			this.bookmarkAnchor = document.createElement("a");
			document.body.appendChild(this.bookmarkAnchor);
			this.bookmarkAnchor.style.display = "none";
		}
		if((!args["changeURL"])||(dojo.render.html.ie)){
			var url = dojo.hostenv.getBaseScriptUri()+"blank.html?"+(new Date()).getTime();
			this.moveForward = true;
			dojo.io.setIFrameSrc(this.historyIframe, url, false);
		}
		if(args["changeURL"]){
			hash = "#"+ ((args["changeURL"]!==true) ? args["changeURL"] : (new Date()).getTime());
			setTimeout("window.location.href = '"+hash+"';", 1);
			this.bookmarkAnchor.href = hash;
			if(dojo.render.html.ie){
				// IE requires manual setting of the hash since we are catching
				// events from the iframe
				var oldCB = callback;
				var lh = null;
				var hsl = this.historyStack.length-1;
				if(hsl>=0){
					while(!this.historyStack[hsl]["urlHash"]){
						hsl--;
					}
					lh = this.historyStack[hsl]["urlHash"];
				}
				if(lh){
					callback = function(){
						if(window.location.hash != ""){
							setTimeout("window.location.href = '"+lh+"';", 1);
						}
						oldCB();
					}
				}
				// when we issue a new bind(), we clobber the forward 
				// FIXME: is this always a good idea?
				this.forwardStack = []; 
				var oldFW = args["forward"]||args["forwardbutton"];;
				var tfw = function(){
					if(window.location.hash != ""){
						window.location.href = hash;
					}
					if(oldFW){ // we might not actually have one
						oldFW();
					}
				}
				if(args["forward"]){
					args.forward = tfw;
				}else if(args["forwardButton"]){
					args.forwardButton = tfw;
				}
			}else if(dojo.render.html.moz){
				// start the timer
				if(!this.locationTimer){
					this.locationTimer = setInterval("dojo.io.XMLHTTPTransport.checkLocation();", 200);
				}
			}
		}

		this.historyStack.push({"url": url, "callback": callback, "kwArgs": args, "urlHash": hash});
	}

	this.checkLocation = function(){
		var hsl = this.historyStack.length;

		if((window.location.hash == this.initialHash)||(window.location.href == this.initialHref)&&(hsl == 1)){
			// FIXME: could this ever be a forward button?
			// we can't clear it because we still need to check for forwards. Ugg.
			// clearInterval(this.locationTimer);
			this.handleBackButton();
			return;
		}
		// first check to see if we could have gone forward. We always halt on
		// a no-hash item.
		if(this.forwardStack.length > 0){
			if(this.forwardStack[this.forwardStack.length-1].urlHash == window.location.hash){
				this.handleForwardButton();
				return;
			}
		}
		// ok, that didn't work, try someplace back in the history stack
		if((hsl >= 2)&&(this.historyStack[hsl-2])){
			if(this.historyStack[hsl-2].urlHash==window.location.hash){
				this.handleBackButton();
				return;
			}
		}
	}

	this.iframeLoaded = function(evt, ifrLoc){
		var isp = ifrLoc.href.split("?");
		if(isp.length < 2){ 
			// alert("iframeLoaded");
			// we hit the end of the history, so we should go back
			if(this.historyStack.length == 1){
				this.handleBackButton();
			}
			return;
		}
		var query = isp[1];
		if(this.moveForward){
			// we were expecting it, so it's not either a forward or backward
			// movement
			this.moveForward = false;
			return;
		}

		var last = this.historyStack.pop();
		// we don't have anything in history, so it could be a forward button
		if(!last){ 
			if(this.forwardStack.length > 0){
				var next = this.forwardStack[this.forwardStack.length-1];
				if(query == next.url.split("?")[1]){
					this.handleForwardButton();
				}
			}
			// regardless, we didnt' have any history, so it can't be a back button
			return;
		}
		// put it back on the stack so we can do something useful with it when
		// we call handleBackButton()
		this.historyStack.push(last);
		if(this.historyStack.length >= 2){
			if(isp[1] == this.historyStack[this.historyStack.length-2].url.split("?")[1]){
				// looks like it IS a back button press, so handle it
				this.handleBackButton();
			}
		}else{
			this.handleBackButton();
		}
	}

	this.handleBackButton = function(){
		var last = this.historyStack.pop();
		if(!last){ return; }
		if(last["callback"]){
			last.callback();
		}else if(last.kwArgs["backButton"]){
			last.kwArgs["backButton"]();
		}else if(last.kwArgs["back"]){
			last.kwArgs["back"]();
		}else if(last.kwArgs["handle"]){
			last.kwArgs.handle("back");
		}
		this.forwardStack.push(last);
	}

	this.handleForwardButton = function(){
		// FIXME: should we build in support for re-issuing the bind() call here?
		// alert("alert we found a forward button call");
		var last = this.forwardStack.pop();
		if(!last){ return; }
		if(last.kwArgs["forward"]){
			last.kwArgs.back();
		}else if(last.kwArgs["forwardButton"]){
			last.kwArgs.forwardButton();
		}else if(last.kwArgs["handle"]){
			last.kwArgs.handle("forward");
		}
		this.historyStack.push(last);
	}

	this.canHandle = function(kwArgs){
		// canHandle just tells dojo.io.bind() if this is a good transport to
		// use for the particular type of request.

		// FIXME: we need to determine when form values need to be
		// multi-part mime encoded and avoid using this transport for those
		// requests.
		return dojo.alg.inArray(kwArgs["mimetype"], ["text/plain", "text/html", "text/xml", "text/javascript"])
			&& dojo.alg.inArray(kwArgs["method"], ["post", "get"])
			&& !( kwArgs["formNode"] && dojo.io.formHasFile(kwArgs["formNode"]) );
	}

	this.bind = function(kwArgs){
		if(!kwArgs["url"]){
			// are we performing a history action?
			if( !kwArgs["formNode"]
				&& (kwArgs["backButton"] || kwArgs["back"] || kwArgs["changeURL"] || kwArgs["watchForURL"])
				&& (!window["djConfig"] && !window["djConfig"]["preventBackButtonFix"]) ) {
				this.addToHistory(kwArgs);
				return true;
			}
		}

		// build this first for cache purposes
		var url = kwArgs.url;
		var query = "";
		if(kwArgs["formNode"]){
			var ta = kwArgs.formNode.getAttribute("action");
			if((ta)&&(!kwArgs["url"])){ url = ta; }
			var tp = kwArgs.formNode.getAttribute("method");
			if((tp)&&(!kwArgs["method"])){ kwArgs.method = tp; }
			query += dojo.io.buildFormGetString(kwArgs.formNode);
		}

		if(!kwArgs["method"]) {
			kwArgs.method = "get";
		}

		if(kwArgs["content"]){
			query += dojo.io.argsFromMap(kwArgs.content);
		}

		if(kwArgs["postContent"] && kwArgs.method.toLowerCase() == "post") {
			query = kwArgs.postContent;
		}

		if(kwArgs["backButton"] || kwArgs["back"] || kwArgs["changeURL"]){
			this.addToHistory(kwArgs);
		}

		var async = kwArgs["sync"] ? false : true;

		var useCache = kwArgs["useCache"] == true ||
			(this.useCache == true && kwArgs["useCache"] != false );

		if(useCache){
			var cachedHttp = getFromCache(url, query, kwArgs.method);
			if(cachedHttp){
				doLoad(kwArgs, cachedHttp, url, query, false);
				return;
			}
		}

		// much of this is from getText, but reproduced here because we need
		// more flexibility
		var http = dojo.hostenv.getXmlhttpObject();
		var received = false;

		// build a handler function that calls back to the handler obj
		if(async){
			http.onreadystatechange = function(){
				if((4==http.readyState)&&(http.status)){
					if(received){ return; } // Opera 7.6 is foo-bar'd
					received = true;
					doLoad(kwArgs, http, url, query, useCache);
				}
			}
		}

		if(kwArgs.method.toLowerCase() == "post"){
			// FIXME: need to hack in more flexible Content-Type setting here!
			http.open("POST", url, async);
			http.setRequestHeader("Content-Type", kwArgs["contentType"] || "application/x-www-form-urlencoded");
			http.send(query);
		}else{
			http.open("GET", url+((query!="") ? "?"+query : ""), async);
			http.send(null);
		}

		if( !async ) {
			doLoad(kwArgs, http, url, query, useCache);
		}

		return;
	}
	dojo.io.transports.addTransport("XMLHTTPTransport");
}


dojo.hostenv.startPackage("dojo.xml.domUtil");

// for loading script:
dojo.xml.domUtil = new function(){
	var _this = this;

	this.nodeTypes = {
		ELEMENT_NODE                  : 1,
		ATTRIBUTE_NODE                : 2,
		TEXT_NODE                     : 3,
		CDATA_SECTION_NODE            : 4,
		ENTITY_REFERENCE_NODE         : 5,
		ENTITY_NODE                   : 6,
		PROCESSING_INSTRUCTION_NODE   : 7,
		COMMENT_NODE                  : 8,
		DOCUMENT_NODE                 : 9,
		DOCUMENT_TYPE_NODE            : 10,
		DOCUMENT_FRAGMENT_NODE        : 11,
		NOTATION_NODE                 : 12
	}
	
	this.dojoml = "http://www.dojotoolkit.org/2004/dojoml";
	
	this.getTagName = function(node){
		var tagName = node.tagName;
		if(tagName.substr(0,5).toLowerCase()!="dojo:"){
			
			if(tagName.substr(0,4).toLowerCase()=="dojo"){
				// FIXME: this assuumes tag names are always lower case
				return "dojo:" + tagName.substring(4).toLowerCase();
			}

			var djt = node.getAttribute("dojoType");
			if(djt){
				return "dojo:"+djt.toLowerCase();
			}
			
			if((node.getAttributeNS)&&(node.getAttributeNS(this.dojoml,"type"))){
				return "dojo:" + node.getAttributeNS(this.dojoml,"type").toLowerCase();
			}
			try{
				// FIXME: IE really really doesn't like this, so we squelch
				// errors for it
				djt = node.getAttribute("dojo:type");
			}catch(e){ /* FIXME: log? */ }
			if(djt){
				return "dojo:"+djt.toLowerCase();
			}
		}
		return tagName.toLowerCase();
	}

	this.getStyle = function(node, cssSelector) {
		var value = undefined, camelCased = _this.toCamelCase(cssSelector);
		value = node.style[camelCased]; // dom-ish
		if(!value) {
			if(document.defaultView) { // gecko
				value = document.defaultView.getComputedStyle(node, "").getPropertyValue(cssSelector);
			} else if(node.currentStyle) { // ie
				value = node.currentStyle[camelCased];
			} else if(node.style.getPropertyValue) { // dom spec
				value = node.style.getPropertyValue(cssSelector);
			}
		}
		return value;
	}

	this.toCamelCase = function(selector) {
		var arr = selector.split('-'), cc = arr[0];
		for(var i = 1; i < arr.length; i++) {
			cc += arr[i].charAt(0).toUpperCase() + arr[i].substring(1);
		}
		return cc;		
	}

	this.toSelectorCase = function(selector) {
		return selector.replace(/([A-Z])/g, "-$1" ).toLowerCase() ;
	}

	this.getAncestors = function(node){
		var ancestors = [];
		while(node){
			ancestors.push(node);
			node = node.parentNode;
		}
		return ancestors;
	}

	this.isChildOf = function(node, ancestor) {
		while(node) {
			if(node == ancestor) {
				return true;
			}
			node = node.parentNode;
		}
		return false;
	}

	// FIXME: this won't work in Safari
	this.parseXmlString = function(str, mimetype) {
		if(!mimetype) { mimetype = "text/xml"; }
		if(typeof DOMParser != "undefined") {
			var parser = new DOMParser();
			return parser.parseFromString(str, mimetype);
		} else if(typeof ActiveXObject != "undefined") {
			var domDoc = new ActiveXObject("Microsoft.XMLDOM");
			if(domDoc) {
				domDoc.async = false;
				domDoc.loadXML(str);
				return domDoc;
			} else {
				dj_debug("toXml didn't work?");
			}
		} else if(document.createElement) {
			// FIXME: this may change all tags to uppercase!
			var tmp = document.createElement("xml");
			tmp.innerHTML = str;
			if(document.implementation && document.implementation.createDocument) {
				var xmlDoc = document.implementation.createDocument("foo", "", null);
				for(var i = 0; i < tmp.childNodes.length; i++) {
					xmlDoc.importNode(tmp.childNodes.item(i), true);
				}
				return xmlDoc;
			}
			// FIXME: probably not a good idea to have to return an HTML fragment
			// FIXME: the tmp.doc.firstChild is as tested from IE, so it may not
			// work that way across the board
			return tmp.document && tmp.document.firstChild ?
				tmp.document.firstChild : tmp;
		}
		return null;
	}
	this.parseXMLString = this.parseXmlString; // to avoid confusion

	// get RGB array from css-style color declarations
	this.extractRGB = function(color) {
		var hex = "0123456789abcdef";
		color = color.toLowerCase();
		if( color.indexOf("rgb") == 0 ) {
			var matches = color.match(/rgba*\((\d+), *(\d+), *(\d+)/i);
			var ret = matches.splice(1, 3);
			return ret;
		} else if( color.indexOf("#") == 0 ) {
			var colors = [];
			color = color.substring(1);
			if( color.length == 3 ) {
				colors[0] = color.charAt(0) + color.charAt(0);
				colors[1] = color.charAt(1) + color.charAt(1);
				colors[2] = color.charAt(2) + color.charAt(2);
			} else {
				colors[0] = color.substring(0, 2);
				colors[1] = color.substring(2, 4);
				colors[2] = color.substring(4, 6);
			}

			for(var i = 0; i < colors.length; i++) {
				var c = colors[i];
				colors[i] = hex.indexOf(c.charAt(0))*16 + hex.indexOf(c.charAt(1));
			}
			return colors;
		} else {
			// named color (how many do we support?)
			switch(color) {
				case "white": return [255,255,255];
				case "black": return [0,0,0];
				case "red": return[255,0,0];
				case "green": return [0,255,0];
				case "blue": return [0,0,255];
				case "navy": return [0,0,128];
				case "gray": return [128,128,128];
				case "silver": return [192,192,192];
			}
		}
		return [255,255,255]; // assume white if all else fails
	}

	this.hex2rgb = function(hex) {
		var hexNum = "0123456789ABCDEF";
		var rgb = new Array(3);
		if( hex.indexOf("#") == 0 ) { hex = hex.substring(1); }
		if( hex.length == 3 ) {
			rgb[0] = hex.charAt(0) + hex.charAt(0)
			rgb[1] = hex.charAt(1) + hex.charAt(1)
			rgb[2] = hex.charAt(2) + hex.charAt(2);
		} else {
			rgb[0] = hex.substring(0, 2);
			rgb[1] = hex.substring(2, 4);
			rgb[2] = hex.substring(4);
		}
		for(var i = 0; i < rgb.length; i++) {
			rgb[i] = hexNum.indexOf(rgb[i].charAt(0)) * 16 + hexNum.indexOf(rgb[i].charAt(1));
		}
		return rgb;
	}

	this.rgb2hex = function(r, g, b) {
		if(r.constructor == Array) {
			g = r[1] || 0;
			b = r[2] || 0;
			r = r[0] || 0;
		}
		return "#" + r.toString(16) + g.toString(16) + b.toString(16);
	}

	this.insertBefore = function(node, ref){
		var pn = node.parentNode;
		pn.insertBefore(node, ref);
	}

	this.before = this.insertBefore;

	this.insertAfter = function(node, ref){
		var pn = ref.parentNode;
		if(ref == pn.lastChild){
			pn.appendChild(node);
		}else{
			pn.insertBefore(node, ref.nextSibling);
		}
	}

	this.after = this.insertAfter;
}

dojo.hostenv.startPackage("dojo.xml.Parse");

dojo.hostenv.loadModule("dojo.xml.domUtil");

//TODO: determine dependencies
// currently has dependency on dojo.xml.DomUtil nodeTypes constants...

/* generic method for taking a node and parsing it into an object

For example, the following xml fragment

<foo bar="bar">
	<baz xyzzy="xyzzy"/>
</foo>

can be described as:

dojo.???.foo = {}
dojo.???.foo.bar = {}
dojo.???.foo.bar.value = "xyzzy";
dojo.???.foo.baz = {}
dojo.???.foo.baz.xyzzy = {}
dojo.???.foo.baz.xyzzy.value = "xyzzy"

*/
// using documentFragment nomenclature to generalize in case we don't want to require passing a collection of nodes with a single parent
dojo.xml.Parse = function(){
	this.parseFragment = function(documentFragment) {
		// handle parent element
		var parsedFragment = {};
		// var tagName = dojo.xml.domUtil.getTagName(node);
		var tagName = dojo.xml.domUtil.getTagName(documentFragment);
		// TODO: What if document fragment is just text... need to check for nodeType perhaps?
		parsedFragment[tagName] = new Array(documentFragment.tagName);
		var attributeSet = this.parseAttributes(documentFragment);
		for(var attr in attributeSet){
			if(!parsedFragment[attr]){
				parsedFragment[attr] = [];
			}
			parsedFragment[attr][parsedFragment[attr].length] = attributeSet[attr];
		}
		var nodes = documentFragment.childNodes;
		for(var childNode in nodes){
			switch(nodes[childNode].nodeType){
				case  dojo.xml.domUtil.nodeTypes.ELEMENT_NODE: // element nodes, call this function recursively
					parsedFragment[tagName].push(this.parseElement(nodes[childNode]));
					break;
				case  dojo.xml.domUtil.nodeTypes.TEXT_NODE: // if a single text node is the child, treat it as an attribute
					if(nodes.length == 1){
						if(!parsedFragment[documentFragment.tagName]){
							parsedFragment[tagName] = [];
						}
						parsedFragment[tagName].push({ value: nodes[0].nodeValue });
					}
					break;
			}
		}
		
		return parsedFragment;
	}

	this.parseElement = function(node, hasParentNodeSet, optimizeForDojoML){
		// TODO: make this namespace aware
		var parsedNodeSet = {};
		var tagName = dojo.xml.domUtil.getTagName(node);
		parsedNodeSet[tagName] = [];
		if((!optimizeForDojoML)||(tagName.substr(0,4).toLowerCase()=="dojo")){
			var attributeSet = this.parseAttributes(node);
			for(var attr in attributeSet){
				if(!parsedNodeSet[tagName][attr]){
					parsedNodeSet[tagName][attr] = [];
				}
				parsedNodeSet[tagName][attr].push(attributeSet[attr]);
			}
		}
	
		// FIXME: we might want to make this optional or provide cloning instead of
		// referencing, but for now, we include a node reference to allow
		// instantiated components to figure out their "roots"
		parsedNodeSet[tagName].nodeRef = node;
		parsedNodeSet.tagName = tagName;
	
		var ntypes = dojo.xml.domUtil.nodeTypes;
	
		for(var i=0; i<node.childNodes.length; i++){
			var tcn = node.childNodes.item(i);
			switch(tcn.nodeType){
				case  ntypes.ELEMENT_NODE: // element nodes, call this function recursively
					var ctn = dojo.xml.domUtil.getTagName(tcn);
					if(!parsedNodeSet[ctn]){
						parsedNodeSet[ctn] = [];
					}
					parsedNodeSet[ctn].push(this.parseElement(tcn, true, optimizeForDojoML));
					if(	(tcn.childNodes.length == 1)&&
						(tcn.childNodes.item(0).nodeType == ntypes.TEXT_NODE)){
						parsedNodeSet[ctn][parsedNodeSet[ctn].length-1].value = tcn.childNodes.item(0).nodeValue;
					}
					break;
				case  ntypes.ATTRIBUTE_NODE: // attribute node... not meaningful here
					break;
				case  ntypes.TEXT_NODE: // if a single text node is the child, treat it as an attribute
					if(node.childNodes.length == 1) {
						parsedNodeSet[tagName].push({ value: node.childNodes.item(0).nodeValue });
					}
					break;
				case  ntypes.CDATA_SECTION_NODE: // cdata section... not sure if this would ever be meaningful... might be...
					break;
				case  ntypes.ENTITY_REFERENCE_NODE: // entity reference node... not meaningful here
					break;
				case  ntypes.ENTITY_NODE: // entity node... not sure if this would ever be meaningful
					break;
				case  ntypes.PROCESSING_INSTRUCTION_NODE: // processing instruction node... not meaningful here
					break;
				case  ntypes.COMMENT_NODE: // comment node... not not sure if this would ever be meaningful 
					break;
				case  ntypes.DOCUMENT_NODE: // document node... not sure if this would ever be meaningful
					break;
				case  ntypes.DOCUMENT_TYPE_NODE: // document type node... not meaningful here
					break;
				case  ntypes.DOCUMENT_FRAGMENT_NODE: // document fragment node... not meaningful here
					break;
				case  ntypes.NOTATION_NODE:// notation node... not meaningful here
					break;
			}
		}
		//return (hasParentNodeSet) ? parsedNodeSet[node.tagName] : parsedNodeSet;
		return parsedNodeSet;
	}

	/* parses a set of attributes on a node into an object tree */
	this.parseAttributes = function(node) {
		// TODO: make this namespace aware
		var parsedAttributeSet = {};
		var atts = node.attributes;
		// TODO: should we allow for duplicate attributes at this point...
		// would any of the relevant dom implementations even allow this?
		for(var i=0; i<atts.length; i++) {
			var attnode = atts.item(i);
			if((dojo.render.html.capable)&&(dojo.render.html.ie)){
				if(!attnode){ continue; }
				if(	(typeof attnode == "object")&&
					(typeof attnode.nodeValue == 'undefined')||
					(attnode.nodeValue == null)||
					(attnode.nodeValue == '')){ 
					continue; 
				}
			}
			parsedAttributeSet[attnode.nodeName] = { 
				value: attnode.nodeValue 
			};
		}
		return parsedAttributeSet;
	}
}

dojo.hostenv.startPackage("dojo.webui.WidgetManager");
dojo.hostenv.startPackage("dojo.webui.widgetManager");

dojo.hostenv.loadModule("dojo.alg.*");

dojo.webui.widgetManager = new function(){
	this.widgets = [];
	this.widgetIds = [];
	this.root = null; // the root widget

	var widgetCtr = 0;

	this.getUniqueId = function(){
		return widgetCtr++;
	}

	this.add = function(widget){
		this.widgets.push(widget);
		if(widget.widgetId == ""){
			widget.widgetId = widget.widgetType+" "+this.getUniqueId();
		}else if(this.widgetIds[widget.widgetId]){
			dj_debug("widget ID collision on ID: "+widget.widgetId);
		}
		this.widgetIds[widget.widgetId] = widget;
	}

	// FIXME: we should never allow removal of the root widget until all others
	// are removed!
	this.remove = function(widgetIndex){
		var tw = this.widgets[widgetIndex];
		delete this.widgetIds[tw];
		this.widgets.splice(widgetIndex, 1);
	}

	this.getWidgetById = function(id){
		return this.widgetIds[id];
	}

	this.getWidgetsOfType = function(type){
		var lt = type.toLowerCase();
		var ret = [];
		dojo.alg.forEach(this.widgets, function(x){
			if(x.widgetType.toLowerCase() == lt){
				ret.push(x);
			}
		});
		return ret;
	}

	this.getWidgetsByFilter = function(unaryFunc){
		var ret = [];
		dojo.alg.forEach(this.widgets, function(x){
			if(unaryFunc(x)){
				ret.push(x);
			}
		});
		return ret;
	}

	// map of previousally discovered implementation names to constructors
	var knownWidgetImplementations = [];

	// support manually registered widget packages
	var widgetPackages = ["dojo.webui.widgets"];

	this.registerWidgetPackage = function(pname) {
		widgetPackages.push(pname);
	}

	this.getImplementation = function(widgetName, ctorObject, mixins){

			// try and find a name for the widget
		var impl = this.getImplementationName(widgetName);
				
		if(impl){
			
			var item = new impl(ctorObject);
			//alert(impl+": "+item);
			return item;
		}


	}
	this.getImplementationName = function(widgetName){
		/*
		 * This is the overly-simplistic implemention of getImplementation (har
		 * har). In the future, we are going to want something that allows more
		 * freedom of expression WRT to specifying different specializations of
		 * a widget.
		 *
		 * Additionally, this implementation treats widget names as case
		 * insensitive, which does not necessarialy mesh with the markup which
		 * can construct a widget.
		 */

		// first, search the knownImplementations list for a suitable match
		var impl = knownWidgetImplementations[widgetName.toLowerCase()];
		if(impl){
			return impl;
		}

		// if we didn't get one there, then we need to run through the
		// classname location algorithm

		// step 1: look for a rendering-context specific version of our widget
		// name
		// /alex goes looking for a good way to do this...
		// ...oh fuck it, for now we' hard-code in an "HTML" prefix and see if
		// it dies, at which point we'll drop the prefix and just try to find
		// the base class.
		for (var i = 0; i < widgetPackages.length; i++) {
			var pn = widgetPackages[i];
			var pkg = dj_eval_object_path(pn);

			for(var x in pkg){
				var xlc = (new String(x)).toLowerCase();
				if(dojo.render.html.capable){
					if(("html"+widgetName).toLowerCase() == xlc){
						knownWidgetImplementations[xlc] = pkg[x];
						return pkg[x];
					}
				}else if(dojo.render.svg.capable){
					if(("svg"+widgetName).toLowerCase() == xlc){
						knownWidgetImplementations[xlc] = pkg[x];
						return pkg[x];
					}
				}
			}
		}
	}

	// FIXME: does it even belong in this name space?
	// NOTE: this method is implemented by DomWidget.js since not all
	// hostenv's would have an implementation.
	this.getWidgetFromPrimitive = function(baseRenderType){
		dj_unimplemented("dojo.webui.widgetManager.getWidgetFromPrimitive");
	}

	this.getWidgetFromEvent = function(nativeEvt){
		dj_unimplemented("dojo.webui.widgetManager.getWidgetFromEvent");
	}

	// FIXME: what else?
}

// hostenv.loadModule("dojo.alg.*");
dojo.hostenv.startPackage("dojo.text.Text");

dojo.text = new function(){
	this.trim = function(iString){
		if(!iString){ // allow String.prototyp-ing
			iString = this; 
		}
		if(typeof iString != "string"){ return iString; }
		if(!iString.length){ return iString; }
		return iString.replace(/^\s*/, "").replace(/\s*$/, "");
	}

	// Parameterized string function
	//  str - formatted string with %{values} to be replaces
	//  pairs - object of name: "value" value pairs
	//  killExtra - remove all remaining %{values} after pairs are inserted
	this.paramString = function(str, pairs, killExtra) {
		if(typeof str != "string") { // allow String.prototype-ing
			pairs = str;
			str = this;
		}

		for(var name in pairs) {
			var re = new RegExp("\\%\\{" + name + "\\}", "g");
			str = str.replace(re, pairs[name]);
		}

		if(killExtra) {
			str = str.replace(/%\{([^\}\s]+)\}/g, "");
		}

		return str;
	}
}

dojo.text.Text = {}; // duh, alex.

dojo.hostenv.conditionalLoadModule({
	common: ["dojo.text.Text"]
});

dojo.hostenv.startPackage("dojo.webui.widgets.Parse");

dojo.hostenv.loadModule("dojo.webui.WidgetManager");
dojo.hostenv.loadModule("dojo.text.*");

dojo.webui.widgets.Parse = function(fragment) {
	this.propertySetsList = [];
	this.fragment = fragment;

	/*	createComponents recurses over a raw JavaScript object structure,
			and calls the corresponding handler for its normalized tagName if it exists
	*/
	this.createComponents = function(fragment, parentComp){
		var djTags = dojo.webui.widgets.tags;
		var returnValue = [];
		for(var item in fragment){
			// if we have items to parse/create at this level, do it!
			try{
				if((fragment[item]["tagName"])&&
					(fragment[item] != fragment["nodeRef"])){
					var tn = new String(fragment[item]["tagName"]);
					// we split so that you can declare multiple
					// non-destructive widgets from the same ctor node
					var tna = tn.split(";");
					for(var x=0; x<tna.length; x++){
						var ltn = dojo.text.trim(tna[x]).toLowerCase();
						if(djTags[ltn]){
							fragment[item].tagName = ltn;
							// dj_debug(djTags[tn.toLowerCase()]);
							// dj_debug(parentComp);
							returnValue.push(djTags[ltn](fragment[item], this, parentComp));
						}
					}
				}
			}catch(e){
				if(dojo.hostenv.is_debug_){ dj_debug(e); }
				// throw(e);
				// IE is such a bitch sometimes
			}

			// if there's a sub-frag, build widgets from that too
			if( (typeof fragment[item] == "object")&&
				(fragment[item] != fragment.nodeRef)&&
				(fragment[item] != fragment["tagName"])){
				returnValue.push(this.createComponents(fragment[item], parentComp));
			}
		}
		return returnValue;
	}

	/*  parsePropertySets checks the top level of a raw JavaScript object
			structure for any propertySets.  It stores an array of references to 
			propertySets that it finds.
	*/
	this.parsePropertySets = function(fragment) {
		this.propertySets = [];
		for(var item in fragment){
			if(	(fragment[item]["tagName"] == "dojo:propertyset") ) {
				propertySets.push(fragment[item]);
			}
		}
		// FIXME: should we store these propertySets somewhere for later retrieval
		this.propertySetsList.push(propertySets);
		return propertySets;
	}
	
	/*  parseProperties checks a raw JavaScript object structure for
			properties, and returns an array of properties that it finds.
	*/
	this.parseProperties = function(fragment) {
		var properties = {};
		for(var item in fragment){
			// FIXME: need to check for undefined?
			// case: its a tagName or nodeRef
			if((fragment[item] == fragment["tagName"])||
				(fragment[item] == fragment.nodeRef)){
				// do nothing
			}else{
				if((fragment[item]["tagName"])&&
					(dojo.webui.widgets.tags[fragment[item].tagName.toLowerCase()])){
					// TODO: it isn't a property or property set, it's a fragment, 
					// so do something else
					// FIXME: needs to be a better/stricter check
					// TODO: handle xlink:href for external property sets
				}else if((fragment[item][0])&&(fragment[item][0].value!="")){
					try{
						// FIXME: need to allow more than one provider
						if(item.toLowerCase() == "dataprovider") {
							var _this = this;
							this.getDataProvider(_this, fragment[item][0].value);
							properties.dataProvider = this.dataProvider;
						}
						properties[item] = fragment[item][0].value;
						var nestedProperties = this.parseProperties(fragment[item]);
						// FIXME: this kind of copying is expensive and inefficient!
						for(var property in nestedProperties){
							properties[property] = nestedProperties[property];
						}
					}catch(e){ dj_debug(e); }
				/*
				}else if((fragment[item])&&(fragment[item].value!="")){
					//dj_debug(item+": "+fragment[item]);
					if(typeof fragment[item] == "object"){
						for(var x in fragment[item]){
							dj_debug("- "+x+": "+fragment[item][x]);
						}
					}
				*/
				}
			}
		}
		return properties;
	}

	/* getPropertySetById returns the propertySet that matches the provided id
	*/
	
	this.getDataProvider = function(objRef, dataUrl) {
		// FIXME: this is currently sync.  To make this async, we made need to move 
		//this step into the widget ctor, so that it is loaded when it is needed 
		// to populate the widget
		dojo.io.bind({
			url: dataUrl,
			load: function(type, evaldObj){
				if(type=="load"){
					objRef.dataProvider = evaldObj;
				}
			},
			mimetype: "text/javascript",
			sync: true
		});
	}

	
	this.getPropertySetById = function(propertySetId){
		for(var x = 0; x < this.propertySetsList.length; x++){
			if(propertySetId == this.propertySetsList[x]["id"][0].value){
				return this.propertySetsList[x];
			}
		}
		return "";
	}
	
	/* getPropertySetsByType returns the propertySet(s) that match(es) the
	 * provided componentClass
	 */
	this.getPropertySetsByType = function(componentType){
		var propertySets = [];
		for(var x=0; x < this.propertySetsList.length; x++){
			// dj_debug(x);
			var cpl = this.propertySetsList[x];
			var cpcc = cpl["componentClass"]||cpl["componentType"]||null;
			if((cpcc)&&(propertySetId == cpcc[0].value)){
				propertySets.push(cpl);
			}
		}
		return propertySets;
	}
	
	/* getPropertySets returns the propertySet for a given component fragment
	*/
	this.getPropertySets = function(fragment){
		var ppl = "dojo:propertyproviderlist";
		var propertySets = [];
		var tagname = fragment["tagName"];
		if(fragment[ppl]){ 
			var propertyProviderIds = fragment[ppl].value.split(" ") || fragment[ppl].value;
			// FIXME: should the propertyProviderList attribute contain # syntax for reference to ids or not?
			// FIXME: need a better test to see if this is local or external
			// FIXME: doesn't handle nested propertySets, or propertySets that just contain information about css documents, etc.
			for(propertySetId in propertyProviderIds){
				if((propertySetId.indexOf("..")==-1)&&(propertySetId.indexOf("://")==-1)){
					// get a reference to a propertySet within the current parsed structure
					var propertySet = this.getPropertySetById(propertySetId);
					if(propertySet != ""){
						propertySets.push(propertySet);
					}
				}else{
					// FIXME: add code to parse and return a propertySet from
					// another document
					// alex: is this even necessaray? Do we care? If so, why?
				}
			}
		}
		// we put the typed ones first so that the parsed ones override when
		// iteration happens.
		return (this.getPropertySetsByType(tagname)).concat(propertySets);
	}
	
	/* 
		nodeRef is the node to be replaced... in the future, we might want to add 
		an alternative way to specify an insertion point

		componentName is the expected dojo widget name, i.e. Button of ContextMenu

		properties is an object of name value pairs
	*/
	this.createComponentFromScript = function(nodeRef, componentName, properties) {
		var frag = {};
		var tagName = "dojo:" + componentName.toLowerCase();
		frag[tagName] = {};
		for (prop in properties) {
			if (properties[prop]) {
				frag[tagName][prop.toLowerCase()] = [{value: properties[prop]}];
			}
		}
		frag[tagName]["dojotype"] = [{value: componentName}];
		frag[tagName].nodeRef = nodeRef;
		frag.tagName = tagName;
		var fragContainer = [frag];
		return this.createComponents(fragContainer);
	}
}



dojo.hostenv.startPackage("dojo.lang.Lang");

dojo.lang.mixin = function(obj, props){
	var tobj = {};
	for(var x in props){
		if(typeof tobj[x] == "undefined"){
			obj[x] = props[x];
		}
	}
}

dojo.lang.extend = function(ctor, props){
	this.mixin(ctor.prototype, props);
}

dojo.lang.extendPrototype = function(obj, props){
	this.extend(obj.constructor, props);
}

dojo.hostenv.conditionalLoadModule({
	common: ["dojo.lang.Lang"]
});

dojo.hostenv.startPackage("dojo.webui.DragAndDrop");
dojo.hostenv.startPackage("dojo.webui.selection");
dojo.hostenv.startPackage("dojo.webui.dragAndDrop");
dojo.hostenv.startPackage("dojo.webui.DragSource");
dojo.hostenv.startPackage("dojo.webui.DropTarget");
dojo.hostenv.startPackage("dojo.webui.DragAndDropManager");

dojo.webui.DragSource = function(){
	// The interface that all drag data sources MUST implement
	this.isDragSource = true;

	this.startDrag = function(){
	}

	this.endDrag = function(){
	}

	this.getDragIcon = function(){
	}

	this.getTypes = function(){
		// DragSource objects MUST include a selection property or overload
		// this method
		if(this.selection){
			return this.selection.getTypes();
		}
		return [];
	}
}

dojo.webui.DropTarget = function(){
	// The interface that all components that accept drops MUST implement
	this.acceptedTypes = []; // strings

	this.handleDrag = function(dragSourceObj){ 
	}

	this.dragEnter = function(dragSourceObj){ 
	}

	this.dragLeave = function(dragSourceObj){
	}

	this.acceptDrag = function(dragSourceObj){
		if(!dragSourceObj){ return false; }
		if(!dragSourceObj["getTypes"]){ 
			// dj_debug("won't accept");
			return false; 
		}
		var dtypes = dragSourceObj.getTypes();
		if(dtypes.length == 0){
			// dj_debug("won't accept");
			return false; 
		}
		for(var x=0; x<dtypes.length; x++){
			if(!dojo.alg.inArray(this.acceptedTypes, dtypes[x])){
				return false;
			}
		}
		// dj_debug(this.widgeType+" will accept!");
		return true;
	}

	this.handleDrop = function(dragSourceObj){
		// this is the default action. it's not very smart and so this method
		// should be over-ridden by widgets wanting to handle drops
		var sel = dragSourceObj.selection.selected;
		for(var x=0;x<sel.length; x++){
			var tmp = dragSourceObj.removeChild(sel[x]);
			// dj_debug(tmp);
			this.addChild(tmp);
		}
		return false;
	}
}

dojo.webui.DragAndDropManager = function(){
	
	this.hoverTarget = null;
	this.dragSource = null;
	this.dropTarget = null;
	this.isDragging = false;
	this.targetAccepts = false;

	// FIXME: should these be replaced by some DOM/HTML variant? is there some
	// other method they should call?
	this.mouseOver = function(widget){ return; }
	this.mouseOut = function(widget){ return; }
	this.mouseMove = function(widget){ return; }
	this.mouseDown = function(){ return; }
	this.mouseUp = function(nativeEvt){ this.drop(nativeEvt); }
	this.mouseDrag = function(nativeEvt){ return; }
	this.startDrag = function(nativeEvt){ return; }
	this.checkForResize = function(nativeEvt){ return; }
	this.checkForDrag = function(nativeEvt){ return; }

	this.checkTargetAccepts = function(){
		if((this.dropTarget)&&(this.dropTarget["acceptDrag"])&&(this.dropTarget.acceptDrag(this.dragSource))){
			this.targetAccepts = true;
		}else{
			this.targetAccepts = false;
			// FIXME: visually signal that the drop won't work!
		}
		return this.targetAccepts;
	}

	this.drag = function(nativeEvt){
		// FIXME: when dragging over a potential drop target, we must ask it if
		// it can accept our selected items. Need to preform that check here
		// and provide visual feedback.
		this.checkTargetAccepts();
		if((this.dropTarget)&&(this.dragSource)){
			this.dropTarget.handleDrag(this.dragSource, nativeEvt);
		}
	}

	this.drop = function(nativeEvt){
		// FIXME: we need to pass dojo.webui.selection to the drop target here.
		// If rejected, need to provide visual feedback of rejection. Need to
		// determine how to handle copy vs. move drags and if that can/should
		// be set by the dragged items or the receiver of the drop event.
		if((this.dropTarget)&&(this.dragSource)&&(this.targetAccepts)){
			this.dropTarget.handleDrop(this.dragSource, nativeEvt);
		}
	}
}

/* FIXME:
 *	The base widget classes should support drag-and-drop completely, but
 *	perhaps through a mixin.  Firstly, any widget should implement a "drop"
 *	handler that implements multiple states. The first state is an accepted
 *	drop, in which case the widget is passed to the There needs to be support
 *	for an acception rubric based on type and/or some other set of handler
 *	functions that can act as gatekeepers. These acceptance functions should be
 *	settable through property sets on a type or instance basis.  Likewise, a
 *	"provisional" state which eventually results in the accepted or denied
 *	states must be supported (at least visually). A drop-rejection must also be
 *	made available, in which the widget "move" is never completed, but the
 *	provisional drop is rolled back visually.
 */

// FIXME: need to support types of drops other than movement, such as copying.
//		  Should this be modifiable keystrokes in order to set what should be
//		  done?
// FIXME: need to select collections of selected objects. Is this a clipboard
// 		  concept? Will we want our own clipboard?

dojo.webui.Selection = function(){

	this.selected = [];
	var selectionIndexProp = "_dojo.webui.selection.index";
	var selectionTypeProp = "_dojo.webui.selection.type";

	this.add = function(obj, type){
		if(typeof obj["setSelected"] == "function"){
			obj.setSelected(true);
		}
		obj[selectionIndexProp] = this.selected.length;
		this.selected.push(obj);
		obj[selectionTypeProp] = (!type) ? (new String(typeof obj)) : type;
		// dj_debug(obj[selectionTypeProp]);
	}

	this.getTypes = function(){
		var uniqueTypes = [];
		for(var x=0; x<this.selected.length; x++){
			var st = this.selected[x][selectionTypeProp];
			if((this.selected[x])&&(!uniqueTypes[st])){
				uniqueTypes[st] = true;
				uniqueTypes.push(st);
			}
		}
		return uniqueTypes;
	}

	this.addMultiple = function(){
		// FIXME: how do we pass type info!?
		for(var x=0; x<arguments.length; x++){
			this.add(arguments[x]);
		}
	}

	this.remove = function(obj){
		if(typeof obj["setSelected"] == "function"){
			obj.setSelected(false);
		}
		if(typeof obj[selectionIndexProp] != "undefined"){
		}else{
			for(var x=0; x<this.selected.length; x++){
				if(this.selected[x] === obj){
					delete this.selected[x][selectionIndexProp];
					delete this.selected[x][selectionTypeProp];
					delete this.selected[x];
				}
			}
		}
	}

	this.clear = function(){
		for(var x=0; x<this.selected.length; x++){
			if(this.selected[x]){
				this.remove(this.selected[x]);
			}
		}
		this.selected = [];
	}
}


// ensure that dojo.webui exists
dojo.hostenv.startPackage("dojo.webui.Widget");
dojo.hostenv.startPackage("dojo.webui.widgets.tags");

dojo.hostenv.loadModule("dojo.lang.*");
dojo.hostenv.loadModule("dojo.webui.WidgetManager");
dojo.hostenv.loadModule("dojo.webui.DragAndDrop");
dojo.hostenv.loadModule("dojo.event.*");
dojo.hostenv.loadModule("dojo.text.*");

dojo.webui.Widget = function(){
}
// FIXME: need to be able to disambiguate what our rendering context is
//        here!

// needs to be a string with the end classname. Every subclass MUST
// over-ride.
dojo.lang.extend(dojo.webui.Widget, {
	widgetType: "Widget",
	parent: null,
	children: [],
	// obviously, top-level and modal widgets should set these appropriately
	isTopLevel:  false,
	isModal: false,

	isEnabled: true,
	isHidden: false,
	isContainer: false, // can we contain other widgets?
	// FIXME: need to replace this with context menu stuff
	rightClickItems: [],
	widgetId: "",
	selection: new dojo.webui.Selection(),
	
	enable: function(){
		// should be over-ridden
		this.isEnabled = true;
	},

	disable: function(){
		// should be over-ridden
		this.isEnabled = false;
	},

	hide: function(){
		// should be over-ridden
		this.isHidden = true;
	},

	show: function(){
		// should be over-ridden
		this.isHidden = false;
	},

	create: function(args, fragment, parentComp){
		//dj_debug(parentComp);
		this.satisfyPropertySets(args, fragment, parentComp);
		this.mixInProperties(args, fragment, parentComp);
		this.buildRendering(args, fragment, parentComp);
		this.initialize(args, fragment, parentComp);
		this.postInitialize(args, fragment, parentComp);
		dojo.webui.widgetManager.add(this);
		return this;
	},

	destroy: function(widgetIndex){
		// FIXME: this is woefully incomplete
		this.uninitialize();
		this.destroyRendering();
		dojo.webui.widgetManager.remove(widgetIndex);
	},

	satisfyPropertySets: function(args){
		// get the default propsets for our component type
		var typePropSets = []; // FIXME: need to pull these from somewhere!
		var localPropSets = []; // pull out propsets from the parser's return structure

		// for(var x=0; x<args.length; x++){
		// }

		for(var x=0; x<typePropSets.length; x++){
		}

		for(var x=0; x<localPropSets.length; x++){
		}
		
		return args;
	},

	mixInProperties: function(args){
		/*
		 * the actual mix-in code attempts to do some type-assignment based on
		 * PRE-EXISTING properties of the "this" object. When a named property
		 * of a propset is located, it is first tested to make sure that the
		 * current object already "has one". Properties which are undefined in
		 * the base widget are NOT settable here. The next step is to try to
		 * determine type of the pre-existing property. If it's a string, the
		 * property value is simply assigned. If a function, the property is
		 * replaced with a "new Function()" declaration. If an Array, the
		 * system attempts to split the string value on ";" chars, and no
		 * further processing is attempted (conversion of array elements to a
		 * integers, for instance). If the property value is an Object
		 * (testObj.constructor === Object), the property is split first on ";"
		 * chars, secondly on ":" chars, and the resulting key/value pairs are
		 * assigned to an object in a map style. The onus is on the property
		 * user to ensure that all property values are converted to the
		 * expected type before usage.
		 */

		var undef;

		// NOTE: we cannot assume that the passed properties are case-correct
		// (esp due to some browser bugs). Therefore, we attempt to locate
		// properties for assignment regardless of case. This may cause
		// problematic assignments and bugs in the future and will need to be
		// documented with big bright neon lights.

		// FIXME: fails miserably if a mixin property has a default value of null in 
		// a widget
		
		for(var x in args){
			var tx = this[x];
			var xorig = new String(x);
			if(!tx){
				// FIXME: this is O(n) time for each property, and thereby O(mn), which can easily be O(n^2)!!! Ack!!
				for(var y in this){
					if((new String(y)).toLowerCase()==(new String(x)).toLowerCase()){
						x = y; 
						args[y] = args[xorig];
						break;
					}
				}
			}
			
			if((typeof this[x]) != (typeof undef)){
				if(!typeof args[x] == "string"){
					this[x] = args[x];
				}else{
					if(typeof this[x] == "string"){
						this[x] = args[x];
					}else if(typeof this[x] == "number"){

						this[x] = new Number(args[x]); // FIXME: what if NaN is the result?
					}else if(typeof this[x] == "function"){

						// FIXME: need to determine if always over-writing instead
						// of attaching here is appropriate. I suspect that we
						// might want to only allow attaching w/ action items.
						
						// RAR, 1/19/05: I'm going to attach instead of
						// over-write here. Perhaps function objects could have
						// some sort of flag set on them? Or mixed-into objects
						// could have some list of non-mutable properties
						// (although I'm not sure how that would alleviate this
						// particular problem)? 

						// this[x] = new Function(args[x]);

						// after an IRC discussion last week, it was decided
						// that these event handlers should execute in the
						// context of the widget, so that the "this" pointer
						// takes correctly.
						var tn = dojo.event.nameAnonFunc(new Function(args[x]), this);
						dojo.event.connect(this, x, this, tn);
					}else if(this[x].constructor == Array){ // typeof [] == "object"
						this[x] = args[x].split(";");
					}else if(typeof this[x] == "object"){ 

						// FIXME: should we be allowing extension here to handle
						// other object types intelligently?

						// FIXME: unlike all other types, we do not replace the
						// object with a new one here. Should we change that?
						var pairs = args[x].split(";");
						for(var y=0; y<pairs.length; y++){
							var si = pairs[y].indexOf(":");
							if((si != -1)&&(pairs[y].length>si)){
								this[x][pairs[y].substr(0, si)] = pairs[y].substr(si+1);
							}
						}
					}else{
						// the default is straight-up string assignment. When would
						// we ever hit this?
						this[x] = args[x];
					}
				}
			}
		}
	},

	initialize: function(args, frag){
		// dj_unimplemented("dojo.webui.Widget.initialize");
		return false;
	},

	postInitialize: function(args, frag){
		return false;
	},

	uninitialize: function(){
		// dj_unimplemented("dojo.webui.Widget.uninitialize");
		return false;
	},

	buildRendering: function(){
		// SUBCLASSES MUST IMPLEMENT
		dj_unimplemented("dojo.webui.Widget.buildRendering");
		return false;
	},

	destroyRendering: function(){
		// SUBCLASSES MUST IMPLEMENT
		dj_unimplemented("dojo.webui.Widget.destroyRendering");
		return false;
	},

	cleanUp: function(){
		// SUBCLASSES MUST IMPLEMENT
		dj_unimplemented("dojo.webui.Widget.cleanUp");
		return false;
	},

	addChild: function(child){
		// SUBCLASSES MUST IMPLEMENT
		dj_unimplemented("dojo.webui.Widget.addChild");
		return false;
	},

	addChildAtIndex: function(child, index){
		// SUBCLASSES MUST IMPLEMENT
		dj_unimplemented("dojo.webui.Widget.addChildAtIndex");
		return false;
	},

	removeChild: function(childRef){
		// SUBCLASSES MUST IMPLEMENT
		dj_unimplemented("dojo.webui.Widget.removeChild");
		return false;
	},

	removeChildAtIndex: function(index){
		// SUBCLASSES MUST IMPLEMENT
		dj_unimplemented("dojo.webui.Widget.removeChildAtIndex");
		return false;
	},

	resize: function(width, height){
		// both width and height may be set as percentages. The setWidth and
		// setHeight  functions attempt to determine if the passed param is
		// specified in percentage or native units. Integers without a
		// measurement are assumed to be in the native unit of measure.
		this.setWidth(width);
		this.setHeight(height);
	},

	setWidth: function(width){
		if((typeof width == "string")&&(width.substr(-1) == "%")){
			this.setPercentageWidth(width);
		}else{
			this.setNativeWidth(width);
		}
	},

	setHeight: function(height){
		if((typeof height == "string")&&(height.substr(-1) == "%")){
			this.setPercentageHeight(height);
		}else{
			this.setNativeHeight(height);
		}
	},

	setPercentageHeight: function(height){
		// SUBCLASSES MUST IMPLEMENT
		return false;
	},

	setNativeHeight: function(height){
		// SUBCLASSES MUST IMPLEMENT
		return false;
	},

	setPercentageWidth: function(width){
		// SUBCLASSES MUST IMPLEMENT
		return false;
	},

	setNativeWidth: function(width){
		// SUBCLASSES MUST IMPLEMENT
		return false;
	}
});

// TODO: should have a more general way to add tags or tag libraries?
// TODO: need a default tags class to inherit from for things like getting propertySets
// TODO: parse properties/propertySets into component attributes
// TODO: parse subcomponents
// TODO: copy/clone raw markup fragments/nodes as appropriate
dojo.webui.widgets.tags = {};
dojo.webui.widgets.tags.addParseTreeHandler = function(type){
	var ltype = type.toLowerCase();
	this[ltype] = function(fragment, widgetParser, parentComp){ 
		return dojo.webui.widgets.buildWidgetFromParseTree(ltype, fragment, widgetParser, parentComp);
	}
}

dojo.webui.widgets.tags["dojo:propertyset"] = function(fragment, widgetParser) {
	// FIXME: Is this needed?
	// FIXME: Not sure that this parses into the structure that I want it to parse into...
	// FIXME: add support for nested propertySets
	var properties = widgetParser.parseProperties(fragment["dojo:propertyset"]);
}

// FIXME: need to add the <dojo:connect />
dojo.webui.widgets.tags["dojo:connect"] = function(fragment, widgetParser) {
	var properties = widgetParser.parseProperties(fragment["dojo:connect"]);
}

dojo.webui.widgets.buildWidgetFromParseTree = function(type, frag, parser, parentComp){
	var stype = type.split(":");
	stype = (stype.length == 2) ? stype[1] : type;
	// outputObjectInfo(frag["dojo:"+stype]);
	// FIXME: we don't seem to be doing anything with this!
	var propertySets = parser.getPropertySets(frag);
	var localProperties = parser.parseProperties(frag["dojo:"+stype]);
	var twidget = dojo.webui.widgetManager.getImplementation(stype);
	return twidget.create(localProperties, frag, parentComp);
}

dojo.hostenv.conditionalLoadModule({
	browser: ["dojo.webui.Widget"]
});

dojo.hostenv.startPackage("dojo.xml.htmlUtil");
dojo.hostenv.loadModule("dojo.text.*");
dojo.hostenv.loadModule("dojo.event.*");

// FIXME: we are going to assume that we can throw any and every rendering
// engine into the IE 5.x box model. In Mozilla, we do this w/ CSS. Need to investigate for KHTML and Opera
dojo.xml.htmlUtil = new function(){
	var _this = this;
	var _selectDisabled = false;
	this.styleSheet = null;
	
	// FIXME: need to make sure these get installed at onLoad!!!
	// FIXME: if we're on Moz, we need to FORCE -moz-box-sizing: border-box;
	/*
	document.body.style.boxSizing = "border-box";
	document.body.style.MozBoxSizing = "border-box";
	*/

	this._clobberSelection = function(){
		if(window.getSelection){
			var selObj = window.getSelection();
			selObj.collapseToEnd();
		}else if(document.selection){
			document.selection.clear();
		}
	}

	this.disableSelect = function(){
		if(!_selectDisabled){
			_selectDisabled = true;
			dojo.event.connect(document.body, "onselectstart", dojo.event.browser, "stopEvent");
			dojo.event.connect(document.body, "ondragstart", dojo.event.browser, "stopEvent");
			dojo.event.connect(document.body, "onmousemove", this, "_clobberSelection");
		}
	}

	this.enableSelect = function(){
		if(_selectDisabled){
			_selectDisabled = false;
			dojo.event.disconnect(document.body, "onselectstart", dojo.event.browser, "stopEvent");
			dojo.event.disconnect(document.body, "ondragstart", dojo.event.browser, "stopEvent");
			dojo.event.disconnect(document.body, "onmousemove", this, "_clobberSelection");
		}
	}

	var cm = document["compatMode"];
	var boxSizing = ((cm)&&((cm == "BackCompat")||(cm == "QuirksMode"))) ? true : false;

	this.getInnerWidth = function(node){
		return node.offsetWidth;
	}

	this.getOuterWidth = function(node){
		dj_unimplemented("dojo.xml.htmlUtil.getOuterWidth");
	}

	this.getInnerHeight = function(node){
		return node.offsetHeight; // FIXME: does this work?
	}

	this.getOuterHeight = function(node){
		dj_unimplemented("dojo.xml.htmlUtil.getOuterHeight");
	}

	this.getTotalOffset = function(node, type){
		// cribbed from PPK
		var typeStr = (type=="top") ? "offsetTop" : "offsetLeft";
		var alt = (type=="top") ? "y" : "x";
		var ret = 0;
		if(node["offsetParent"]){
			// FIXME: this is known not to work sometimes on IE 5.x since nodes
			// soemtimes need to be "tickled" before they will display their
			// offset correctly
			while(node.offsetParent){
				ret += node[typeStr];
				node = node.offsetParent;
			}
		}else if(node[alt]){
			ret += node[alt];
		}
		return ret;
	}

	this.totalOffsetLeft = function(node){
		return this.getTotalOffset(node, "left");
	}

	this.getAbsoluteX = this.totalOffsetLeft;

	this.totalOffsetTop = function(node){
		return this.getTotalOffset(node, "top");
	}

	this.getAbsoluteY = this.totalOffsetTop;

	this.getEventTarget = function(evt){
		if((window["event"])&&(event["srcElement"])){
			return event.srcElement;
		}else if((evt)&&(evt.target)){
			return evt.target;
		}
	}

	this.evtTgt = this.getEventTarget;

	this.getParentOfType = function(node, type){
		var parent = node;
		type = type.toLowerCase();
		while(parent.nodeName.toLowerCase()!=type){
			if((!parent)||(parent==(document["body"]||document["documentElement"]))){
				return null;
			}
			parent = parent.parentNode;
		}
		return parent;
	}

	// RAR: this function comes from nwidgets and is more-or-less unmodified.
	// We should probably look ant Burst and f(m)'s equivalents
	this.getAttribute = function(node, attr){
		// FIXME: need to add support for attr-specific accessors
		if((!node)||(!node.getAttribute)){
			// if(attr !== 'nwType'){
			//	alert("getAttr of '" + attr + "' with bad node"); 
			// }
			return null;
		}
		var ta = typeof attr == 'string' ? attr : new String(attr);

		// first try the approach most likely to succeed
		var v = node.getAttribute(ta.toUpperCase());
		if((v)&&(typeof v == 'string')&&(v!="")){ return v; }

		// try returning the attributes value, if we couldn't get it as a string
		if(v && typeof v == 'object' && v.value){ return v.value; }

		// this should work on Opera 7, but it's a little on the crashy side
		if((node.getAttributeNode)&&(node.getAttributeNode(ta))){
			return (node.getAttributeNode(ta)).value;
		}else if(node.getAttribute(ta)){
			return node.getAttribute(ta);
		}else if(node.getAttribute(ta.toLowerCase())){
			return node.getAttribute(ta.toLowerCase());
		}
		return null;
	}
	this.getAttr = this.getAttribute; // for backwards compat (may disappear!!!)
	
	/*
	 *	Determines whether or not the specified node carries a value for the
	 *	attribute in question.
	*/
	this.hasAttribute = function(node, attr){
		var v = this.getAttribute(node, attr);
		return v ? true : false;
	}
	this.hasAttr = this.hasAttribute; // for backwards compat (may disappear!!!)
	
	
	/*
	 * Returns the string value of the list of CSS classes currently assigned
	 * directly to the node in question. Returns an empty string if no class attribute
	 * is found;
	*/
	this.getClass = function(node){
		if(node.className){
			return node.className;
		}else if(this.hasAttribute(node, "class")){
			return this.getAttribute(node, "class");
		}
		return "";
	}

	/*
	 * Returns whether or not the specified classname is a portion of the
	 * class list currently applied to the node. Does not cover cascaded
	 * styles, only classes directly applied to the node.
	*/

	this.hasClass = function(node, classname){
		var classes = this.getClass(node).split(/\s+/g);
		for(var x=0; x<classes.length; x++){
			if(classname == classes[x]){ return true; }
		}
		return false;
	}

	/*
	 * Adds the specified class to the beginning of the class list on the
	 * passed node. This gives the specified class the highest precidence
	 * when style cascading is calculated for the node. Returns true or
	 * false; indicating success or failure of the operation, respectively.
	*/

	this.prependClass = function(node, classStr){
		if(!node){ return null; }
		if(this.hasAttribute(node,"class")||node.className){
			classStr += " " + (node.className||this.getAttribute(node, "class"));
		}
		return this.setClass(node, classStr);
	}

	/*	Adds the specified class to the end of the class list on the
	 *	passed &node;. Returns &true; or &false; indicating success or failure.
	*/

	this.addClass = function(node, classStr){
		if(!node){ return null; }
		if(this.hasAttribute(node,"class")||node.className){
			classStr = (node.className||this.getAttribute(node, "class")) + " " + classStr;
		}
		return this.setClass(node, classStr);
	}

	/*
	 *  Clobbers the existing list of classes for the node, replacing it with
	 *	the list given in the 2nd argument. Returns true or false
	 *	indicating success or failure.
	*/

	this.setClass = function(node, classStr){
		if(!node){ return false; }
		var cs = new String(classStr);
		try{
			if(typeof node.className == "string"){
				node.className = cs;
			}else if(node.setAttribute){
				node.setAttribute("class", classStr);
				node.className = cs;
			}else{
				return false;
			}
		}catch(e){
			dj_debug("__util__.setClass() failed", e);
		}
		return true;
	}

	/*	Removes the className from the node;. Returns
	 *  true or false indicating success or failure.
	*/

	this.removeClass = function(node, classStr){
		if(!node){ return false; }
		var classStr = dojo.text.trim(new String(classStr));

		try{
			var cs = String( node.className ).split(" ");
			var nca  = [];
			for(var i = 0; i<cs.length; i++){
				if(cs[i] != classStr){ 
					nca .push(cs[i]);
				}
			}
			node.className = nca .join(" ");
		}catch(e){
			dj_debug("__util__.removeClass() failed", e);
		}

		return true;
	}

	// Enum type for getElementsByClass classMatchType arg:
	this.classMatchType = {
		ContainsAll : 0, // all of the classes are part of the node's class (default)
		ContainsAny : 1, // any of the classes are part of the node's class
		IsOnly : 2 // only all of the classes are part of the node's class
	}

	/*	Returns an array of nodes for the given classStr, children of a
	 *  parent, and optionally of a certain nodeType
	*/

	this.getElementsByClass = function(classStr, parent, nodeType, classMatchType) {
		if(!parent){ parent = document; }
		var classes = classStr.split(/\s+/g);
		var nodes = [];
		if( classMatchType != 1 && classMatchType != 2 ) classMatchType = 0; // make it enum

		if(document.evaluate) { // supports dom 3 xpath
			var xpath = "//" + (nodeType || "*") + "[contains(";
			if( classMatchType != _this.classMatchType.ContainsAny ) {
				xpath += "concat(' ',@class,' '), ' " +
					classes.join(" ') and contains(concat(' ',@class,' '), ' ") +
					" ')]";
			} else {
				xpath += "concat(' ',@class,' '), ' " +
					classes.join(" ')) or contains(concat(' ',@class,' '), ' ") +
					" ')]";
			}
			//dj_debug("xpath: " + xpath);

			var xpathResult = document.evaluate(xpath, document, null,
				XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);

			outer:
			for(var node = null, i = 0; node = xpathResult.snapshotItem(i); i++) {
				if( classMatchType != _this.classMatchType.IsOnly ) {
					nodes.push(node);
				} else {
					if( !_this.getClass(node) ) { continue outer; }

					var nodeClasses = _this.getClass(node).split(/\s+/g);
					var reClass = new RegExp("(\\s|^)(" + classes.join(")|(") + ")(\\s|$)");
					for(var j = 0; j < nodeClasses.length; j++) {
						if( !nodeClasses[j].match(reClass) ) {
							continue outer;
						}
					}
					nodes.push(node);
				}
			}
		} else {
			if(!nodeType) { nodeType = "*"; }
			var candidateNodes = parent.getElementsByTagName(nodeType);

			outer:
			for(var i = 0; i < candidateNodes.length; i++) {
				var node = candidateNodes[i];
				if( !_this.getClass(node) ) { continue outer; }
				var nodeClasses = _this.getClass(node).split(/\s+/g);
				var reClass = new RegExp("(\\s|^)((" + classes.join(")|(") + "))(\\s|$)");
				var matches = 0;

				for(var j = 0; j < nodeClasses.length; j++) {
					if( reClass.test(nodeClasses[j]) ) {
						if( classMatchType == _this.classMatchType.ContainsAny ) {
							nodes.push(node);
							continue outer;
						} else {
							matches++;
						}
					} else {
						if( classMatchType == _this.classMatchType.IsOnly ) {
							continue outer;
						}
					}
				}

				if( matches == classes.length ) {
					if( classMatchType == _this.classMatchType.IsOnly && matches == nodeClasses.length ) {
						nodes.push(node);
					} else if( classMatchType == _this.classMatchType.ContainsAll ) {
						nodes.push(node);
					}
				}
			}
		}
		return nodes;
	}
	this.getElementsByClassName = this.getElementsByClass;
	
	/* float between 0.0 (transparent) and 1.0 (opaque) */
	this.setOpacity = function(node, opacity, dontFixOpacity) {
		var h = dojo.render.html;
		if( !dontFixOpacity ) {
			if( opacity >= 1.0 ) {
				if( h.ie ) {
					this.clearOpacity(node);
					return;
				} else {
					opacity = 0.999999;
				}
			}
			else if( opacity < 0.0 ) { opacity = 0; }
		}
		if(h.ie){
			if(node.nodeName.toLowerCase() == "tr"){
				// FIXME: is this too naive? will we get more than we want?
				var tds = node.getElementsByTagName("td");
				for(var x=0; x<tds.length; x++){
					tds[x].style.filter = "Alpha(Opacity="+opacity*100+")";
				}
			}
			node.style.filter = "Alpha(Opacity="+opacity*100+")";
		}else if(h.moz){
			node.style.opacity = opacity; // ffox 1.0 directly supports "opacity"
			node.style.MozOpacity = opacity;
		}else if(h.safari){
			node.style.opacity = opacity; // 1.3 directly supports "opacity"
			node.style.KhtmlOpacity = opacity;
		}else{
			node.style.opacity = opacity;
		}
	}
	
	this.getOpacity = function(node) {
		if( dojo.render.html.ie ) {
			var opac = (node.filters && node.filters.alpha && typeof node.filters.alpha.opacity == "number" ? node.filters.alpha.opacity : 100) / 100;
		} else {
			var opac = node.style.opacity || node.style.MozOpacity ||  node.style.KhtmlOpacity || 1;
		}
		return opac >= 0.999999 ? 1.0 : Number(opac);
	}

	this.clearOpacity = function(node) {
		var h = dojo.render.html;
		if(h.ie) {
			if( node.filters && node.filters.alpha ) {
				node.style.filter = ""; // FIXME: may get rid of other filter effects
			}
		} else if(h.moz) {
			node.style.opacity = 1;
			node.style.MozOpacity = 1;
		} else if(h.safari) {
			node.style.opacity = 1;
			node.style.KhtmlOpacity = 1;
		} else {
			node.style.opacity = 1;
		}
	}
	
	// FIXME: this is a really basic stub for adding and removing cssRules, but
	// it assumes that you know the index of the cssRule that you want to add 
	// or remove, making it less than useful.  So we need something that can 
	// search for the selector that you you want to remove.
	this.insertCSSRule = function(selector, declaration, index) {
		if(dojo.render.html.ie) {
			if(!this.styleSheet) {
				// FIXME: create a new style sheet document
			}
			if(!index) {
				index = this.styleSheet.rules.length;
			}
			return this.styleSheet.addRule(selector, declaration, index);
		} else if(document.styleSheets[0] && document.styleSheets[0].insertRule) {
			if(!this.styleSheet) {
				// FIXME: create a new style sheet document here
			}
			if(!index) {
				index = this.styleSheet.cssRules.length;
			}
			var rule = selector + "{" + declaration + "}"
			return this.styleSheet.insertRule(rule, index);
		}
	}
	
	this.removeCSSRule = function(index){
		if(!this.styleSheet){
			dj_debug("no stylesheet defined for removing rules");
			return false;
		}
		if(dojo.render.html.ie){
			if(!index){
				index = this.styleSheet.rules.length;
				this.styleSheet.removeRule(index);
			}
		}else if(document.styleSheets[0]){
			if(!index){
				index = this.styleSheet.cssRules.length;
			}
			this.styleSheet.deleteRule(index);
		}
		return true;
	}

	this.insertCSSFile = function(URI, doc){
		if(!doc){ doc = document; }
		var file = doc.createElement("link");
		file.setAttribute("type", "text/css");
		file.setAttribute("rel", "stylesheet");
		file.setAttribute("href", URI);
		var head = doc.getElementsByTagName("head")[0];
		head.appendChild(file);
	}

	this.getBackgroundColor = function(node) {
		var color;
		do {
			color = dojo.xml.domUtil.getStyle(node, "background-color");
			// Safari doesn't say "transparent"
			if(color.toLowerCase() == "rgba(0, 0, 0, 0)") { color = "transparent"; }
			if(node == document.body) { node = null; break; }
			node = node.parentNode;
		} while(node && color == "transparent");

		if( color == "transparent" ) {
			color = [255, 255, 255, 0];
		} else {
			color = dojo.xml.domUtil.extractRGB(color);
		}
		return color;
	}
}

dojo.hostenv.startPackage("dojo.webui.DomWidget");

dojo.hostenv.loadModule("dojo.event.*");
dojo.hostenv.loadModule("dojo.text.*");
dojo.hostenv.loadModule("dojo.webui.Widget");
dojo.hostenv.loadModule("dojo.webui.DragAndDrop");
dojo.hostenv.loadModule("dojo.xml.domUtil");
dojo.hostenv.loadModule("dojo.xml.htmlUtil");

dojo.webui.DomWidget = function(preventSuperclassMixin){

	// FIXME: this is sort of a hack, but it seems necessaray in the case where
	// a widget might already have another mixin base class and DomWidget is
	// mixed in to provide extra attributes, but not necessarialy an over-write
	// of the defaults (which might have already been changed);
	if(!preventSuperclassMixin){
		// mixin inheritance
		dojo.webui.Widget.call(this);
	}

	this.attachProperty = "dojoAttachPoint";
	this.eventAttachProperty = "dojoAttachEvent";
	this.subTemplateProperty = "dojoSubTemplate";
	this.onBuildProperty = "dojoOnBuild";
	this.subTemplates = {};

	this.domNode = null; // this is our visible representation of the widget!
	this.containerNode = null; // holds child elements

	// FIXME: should we support addition at an index in the children arr and
	// order the display accordingly? Right now we always append.
	this.addChild = function(widget, overrideContainerNode, pos, ref){ 
		if(!this.isContainer){ // we aren't allowed to contain other widgets, it seems
			dj_debug("dojo.webui.DomWidget.addChild() attempted on non-container widget");
			return false;
		}else if(!this.containerNode){
			dj_debug("dojo.webui.DomWidget.addChild() attempted without containerNode");
			return false;
		}else{
			var cn = (overrideContainerNode) ? overrideContainerNode : this.containerNode;
			if(!pos){ pos = "after"; }
			if(!ref){ ref = cn.lastChild; }
			if(!ref){
				cn.appendChild(widget.domNode);
			}else{
				dojo.xml.domUtil[pos](widget.domNode, ref);
			}
			this.children.push(widget);
			widget.parent = this;
		}
	}

	this.removeChild = function(widget){
		for(var x=0; x<this.children.length; x++){
			if(this.children[x] === widget){
				this.children.splice(x, 1);
				break;
			}
		}
		return widget;
	}
	
	this.postInitialize = function(args, frag, parentComp){
		if(parentComp){
			parentComp.addChild(this);
		}else{
			if(!frag){ return; }
			var sourceNodeRef = frag["dojo:"+this.widgetType.toLowerCase()]["nodeRef"];
			if(!sourceNodeRef){ return; } // fail safely if we weren't instantiated from a fragment
			// FIXME: this will probably break later for more complex nesting of widgets
			// FIXME: this will likely break something else, and has performance issues
			// FIXME: it also seems to be breaking mixins
			// FIXME: this breaks when the template for the container widget has child
			// nodes

			this.parent = dojo.webui.widgetManager.root;
			// insert our domNode into the DOM in place of where we started
			var oldNode = sourceNodeRef.parentNode.replaceChild(this.domNode, sourceNodeRef);
		}

		if(this.isContainer){
			var elementNodeType = dojo.xml.domUtil.nodeTypes.ELEMENT_NODE;
			// FIXME: this is borken!!!

			// FIXME: Dylan, why do we keep having to create new frag parsers
			// left and right? It seems horribly inefficient.
			var fragParser = new dojo.webui.widgets.Parse(frag);
			// build any sub-components with us as the parent
			fragParser.createComponents(frag, this);
		}
	}

	this.startResize = function(coords){
		dj_unimplemented("dojo.webui.DomWidget.startResize");
	}

	this.updateResize = function(coords){
		dj_unimplemented("dojo.webui.DomWidget.updateResize");
	}

	this.endResize = function(coords){
		dj_unimplemented("dojo.webui.DomWidget.endResize");
	}
	// method over-ride
	this.buildRendering = function(args, frag){
		// DOM widgets construct themselves from a template
		this.buildFromTemplate(args, frag);
		this.fillInTemplate(args, frag); 	// this is where individual widgets
											// will handle population of data
											// from properties, remote data
											// sets, etc.
	}

	this.buildFromTemplate = function(args, frag){
		// copy template properties if they're already set in the templates object
		var ts = dojo.webui.DomWidget.templates[this.widgetType];
		if(ts){
			if(!this.templateString.length){
				this.templateString = ts["string"];
			}
			if(!this.templateNode){
				this.templateNode = ts["node"];
			}
		}
		var node = null;
		// attempt to clone a template node, if there is one
		if((!this.templateNode)&&(this.templateString)){
			// otherwise, we are required to instantiate a copy of the template
			// string if one is provided.
			
			// FIXME: need to be able to distinguish here what should be done
			// or provide a generic interface across all DOM implementations
			// FIMXE: this breaks if the template has whitespace as its first 
			// characters
			node = this.createNodesFromText(this.templateString, true);
			this.templateNode = node[0].cloneNode(true); // we're optimistic here
			ts.node = this.templateNode;
		}
		if(!this.templateNode){ 
			dj_debug("weren't able to create template!");
			return false;
		}
		var node = this.templateNode.cloneNode(true);
		if(!node){ return false; }

		// recurse through the node, looking for, and attaching to, our
		// attachment points which should be defined on the template node.

		this.domNode = node;
		this.attachTemplateNodes(this.domNode);
	}

	this.attachTemplateNodes = function(baseNode, targetObj){
		if(!targetObj){ targetObj = this; }
		var elementNodeType = dojo.xml.domUtil.nodeTypes.ELEMENT_NODE;

		if(!baseNode){ 
			baseNode = targetObj.domNode;
		}

		if(baseNode.nodeType != elementNodeType){
			return;
		}

		// FIXME: is this going to have capitalization problems?
		var attachPoint = baseNode.getAttribute(this.attachProperty);
		if(attachPoint){
			targetObj[attachPoint]=baseNode;
		}

		/*
		// FIXME: we need to put this into some kind of lookup structure
		// instead of direct assignment
		var tmpltPoint = baseNode.getAttribute(this.templateProperty);
		if(tmpltPoint){
			targetObj[tmpltPoint]=baseNode;
		}
		*/

		// subtemplates are always collected "flatly" by the widget class
		var tmpltPoint = baseNode.getAttribute(this.subTemplateProperty);
		if(tmpltPoint){
			// we assign by removal in this case, mainly because we assume that
			// this will get proccessed later when the sub-template is filled
			// in (usually by this method, and usually repetitively)
			this.subTemplates[tmpltPoint]=baseNode.parentNode.removeChild(baseNode);
			// make sure we don't get stopped here the next time we try to process
			this.subTemplates[tmpltPoint].removeAttribute(this.subTemplateProperty);
			return;
		}

		var attachEvent = baseNode.getAttribute(this.eventAttachProperty);
		if(attachEvent){
			// NOTE: we want to support attributes that have the form
			// "domEvent: nativeEvent; ..."
			var evts = attachEvent.split(";");
			for(var x=0; x<evts.length; x++){
				var tevt = null;
				var thisFunc = null;
				if(!evts[x]){ continue; }
				if(!evts[x].length){ continue; }
				tevt = dojo.text.trim(evts[x]);
				if(tevt.indexOf(":") >= 0){
					// oh, if only JS had tuple assignment
					var funcNameArr = tevt.split(":");
					tevt = dojo.text.trim(funcNameArr[0]);
					thisFunc = dojo.text.trim(funcNameArr[1]);
				}
				if(!thisFunc){
					thisFunc = tevt;
				}
				if(dojo.hostenv.name_ == "browser"){
					var _this = targetObj;
					// dojo.event.browser.addListener(baseNode, tevt.toLowerCase(), function(ea){ _this[thisFunc||tevt](ea); });
					// baseNode[tevt.toLowerCase()] 
					var tf = function(){ 
						var ntf = new String(thisFunc);
						return function(evt){
							if(_this[ntf]){
								_this[ntf](evt);
							}
						}
					}();
					dojo.event.browser.addListener(baseNode, tevt.toLowerCase(), tf);
				}else{
					var en = tevt.toLowerCase().substr(2);
					baseNode.addEventListener(en, targetObj[thisFunc||tevt], false);
				}
			}
		}

		var onBuild = baseNode.getAttribute(this.onBuildProperty);
		if(onBuild){
			eval("var node = baseNode; var widget = targetObj; "+onBuild);
		}

		// FIXME: temporarily commenting this out as it is breaking things
		for(var x=0; x<baseNode.childNodes.length; x++){
			if(baseNode.childNodes.item(x).nodeType == elementNodeType){
				this.attachTemplateNodes(baseNode.childNodes.item(x), targetObj);
			}
		}

		/*
		for(var x=0; x<baseNode.childNodes.length; x++){
			var tn = baseNode.childNodes[x];
			if(tn.nodeType!=1){ continue; }
			var aa = dojo.xml.htmlUtil.getAttr(tn, this.attachProperty);
			if(aa){
				// __log__.debug(aa);
				var thisFunc = null;
				if(aa.indexOf(":") >= 0){
					// oh, if only JS had tuple assignment
					var funcNameArr = aa.split(":");
					aa = funcNameArr[0];
					thisFunc = funcNameArr[1];
				}
				alert(aa);
				if((this[aa])&&((thisFunc)||(typeof this[aa] == "function"))){
					var _this = this;
					baseNode[thisFunc||aa] = function(evt){ 
						_this[aa](evt);
					}
				}else{
					this[aa]=tn;
				}
			}
			if(tn.childNodes.length>0){
				this.attachTemplateNodes(tn);
			}
		}
		*/
	}

	this.fillInTemplate = function(){
		// dj_unimplemented("dojo.webui.DomWidget.fillInTemplate");
	}
	
	// method over-ride
	this.destroyRendering = function(){
		var tempNode = this.domNode.parentNode.removeChild(this.domNode);
		delete tempNode;
	}

	// method over-ride
	this.cleanUp = function(){
		
	}
	
	this.getContainerHeight = function(){
		// FIXME: the generic DOM widget shouldn't be using HTML utils!
		return dojo.xml.htmlUtil.getInnerHeight(this.domNode.parentNode);
	}

	this.getContainerWidth = function(){
		// FIXME: the generic DOM widget shouldn't be using HTML utils!
		return dojo.xml.htmlUtil.getInnerWidth(this.domNode.parentNode);
	}

	this.createNodesFromText = function(){
		dj_unimplemented("dojo.webui.DomWidget.createNodesFromText");
	}

	if((arguments.length>0)&&(typeof arguments[0] == "object")){
		this.create(arguments[0]);
	}

}

dojo.webui.DomWidget.prototype.templateNode = null;
dojo.webui.DomWidget.prototype.templateString = null;
dojo.webui.DomWidget.templates = {};

dj_inherits(dojo.webui.DomWidget, dojo.webui.Widget);

// SVGWidget is a mixin ONLY
dojo.webui.SVGWidget = function(args){
	// alert("dojo.webui.SVGWidget");
	// mixin inheritance
	// dojo.webui.DomWidget.call(this);

	this.getContainerHeight = function(){
		// NOTE: container height must be returned as the INNER height
		dj_unimplemented("dojo.webui.SVGWidget.getContainerHeight");
	}

	this.getContainerWidth = function(){
		// return this.parent.domNode.offsetWidth;
		dj_unimplemented("dojo.webui.SVGWidget.getContainerWidth");
	}

	this.setNativeHeight = function(height){
		// var ch = this.getContainerHeight();
		dj_unimplemented("dojo.webui.SVGWidget.setNativeHeight");
	}

	this.createNodesFromText = function(txt, wrap){
		// from http://wiki.svg.org/index.php/ParseXml
		var docFrag = parseXML(txt, window.document);
		docFrag.normalize();
		if(wrap){ 
			var ret = [docFrag.firstChild.cloneNode(true)];
			return ret;
		}
		var nodes = [];
		for(var x=0; x<docFrag.childNodes.length; x++){
			nodes.push(docFrag.childNodes.item(x).cloneNode(true));
		}
		// tn.style.display = "none";
		return nodes;
	}
}

// HTMLWidget is a mixin ONLY
dojo.webui.HTMLWidget = function(args){
	// mixin inheritance
	// dojo.webui.DomWidget.call(this);
	this.templateCSSPath = null;
	this.templatePath = null;

	this.resizeGhost = null;
	this.initialResizeCoords = null;
	// this.templateString = null;

	this.getContainerHeight = function(){
		// NOTE: container height must be returned as the INNER height
		dj_unimplemented("dojo.webui.HTMLWidget.getContainerHeight");
	}

	this.getContainerWidth = function(){
		return this.parent.domNode.offsetWidth;
	}

	this.setNativeHeight = function(height){
		var ch = this.getContainerHeight();
	}

	this.startResize = function(coords){
		// get the left and top offset of our dom node
		var hu = dojo.xml.htmlUtil;
		
		coords.offsetLeft = hu.totalOffsetLeft(this.domNode);
		coords.offsetTop = hu.totalOffsetTop(this.domNode);
		coords.innerWidth = hu.getInnerWidth(this.domNode);
		coords.innerHeight = hu.getInnerHeight(this.domNode);
		if(!this.resizeGhost){
			this.resizeGhost = document.createElement("div");
			var rg = this.resizeGhost;
			rg.style.position = "absolute";
			rg.style.backgroundColor = "white";
			rg.style.border = "1px solid black";
			dojo.xml.htmlUtil.setOpacity(rg, 0.3);
			document.body.appendChild(rg);
		}
		with(this.resizeGhost.style){
			left = coords.offsetLeft + "px";
			top = coords.offsetTop + "px";
		}
		this.initialResizeCoords = coords;
		this.resizeGhost.style.display = "";
		this.updateResize(coords);
	}

	this.updateResize = function(coords){
		var dx = coords.x-this.initialResizeCoords.x;
		var dy = coords.y-this.initialResizeCoords.y;
		with(this.resizeGhost.style){
			width = this.initialResizeCoords.innerWidth + dx + "px";
			height = this.initialResizeCoords.innerHeight + dy + "px";
		}
	}

	this.endResize = function(coords){
		// FIXME: need to actually change the size of the widget!
		var dx = coords.x-this.initialResizeCoords.x;
		var dy = coords.y-this.initialResizeCoords.y;
		with(this.domNode.style){
			width = this.initialResizeCoords.innerWidth + dx + "px";
			height = this.initialResizeCoords.innerHeight + dy + "px";
		}
		this.resizeGhost.style.display = "none";
	}


	this.createNodesFromText = function(txt, wrap){
		// alert("HTMLWidget.createNodesFromText");
		var tn = document.createElement("span");
		// tn.style.display = "none";
		tn.style.visibility= "hidden";
		document.body.appendChild(tn);
		tn.innerHTML = txt;
		tn.normalize();
		if(wrap){ 
			// start hack
			if(tn.firstChild.nodeValue == " " || tn.firstChild.nodeValue == "\t") {
				var ret = [tn.firstChild.nextSibling.cloneNode(true)];
			} else {
				var ret = [tn.firstChild.cloneNode(true)];
			}
			// end hack
			tn.style.display = "none";
			return ret;
		}
		var nodes = [];
		for(var x=0; x<tn.childNodes.length; x++){
			nodes.push(tn.childNodes[x].cloneNode(true));
		}
		tn.style.display = "none";
		return nodes;
	}

	this._old_buildFromTemplate = this.buildFromTemplate;

	this.buildFromTemplate = function(){
		// copy template properties if they're already set in the templates object
		var tmplts = dojo.webui.DomWidget.templates;
		var ts = tmplts[this.widgetType];
		if(!ts){
			tmplts[this.widgetType] = {};
			ts = tmplts[this.widgetType];
		}
		if(!this.templateString){
			this.templateString = ts["string"];
		}
		if(!this.templateNode){
			this.templateNode = ts["node"];
		}
		if((!this.templateNode)&&(!this.templateString)&&(this.templatePath)){
			// fetch a text fragment and assign it to templateString
			// NOTE: we rely on blocking IO here!
			// FIXME: extra / being inserted in URL?
			var tmplts = dojo.webui.DomWidget.templates;
			var ts = tmplts[this.widgetType];
			if(!ts){
				tmplts[this.widgetType] = {};
				ts = tmplts[this.widgetType];
			}
			var tp = dojo.hostenv.getBaseScriptUri()+""+this.templatePath;
			this.templateString = dojo.hostenv.getText(tp);
			ts.string = this.templateString;
		}

		if(this.templateCSSPath){
			// FIXME: extra / being inserted in URL?
			dojo.xml.htmlUtil.insertCSSFile(dojo.hostenv.getBaseScriptUri()+"/"+this.templateCSSPath);
			this.templateCSSPath = null;
		}
		this._old_buildFromTemplate();
	}
}
// dj_inherits(dojo.webui.HTMLWidget, dojo.webui.DomWidget);
dojo.webui.HTMLWidgetMixin = new dojo.webui.HTMLWidget();

dojo.webui.htmlDragAndDropManager = new function(){
	dojo.webui.DragAndDropManager.call(this);

	this.resizeTarget = null;
	this.hoverNode = null;
	this.dragIcon = null;
	this.isResizing = false;
	this.overResizeHandle = false;
	this.overDragHandle  = false;
	this.init = [];
	this.curr = [];

	this.checkForResize = function(node){
		var rh = false;
		var ca = null;
		var ancestors = dojo.xml.domUtil.getAncestors(node);
		while((ancestors.length)&&(!rh)){
			var ca = ancestors.shift();
			rh = dojo.xml.htmlUtil.getAttribute(ca, "resizeHandle");
		}
		return rh;
	}

	this.checkForDrag = function(node){
		var rh = false;
		var ca = null;
		var ancestors = dojo.xml.domUtil.getAncestors(node);
		while((ancestors.length)&&(!rh)){
			var ca = ancestors.shift();
			rh = dojo.xml.htmlUtil.getAttribute(ca, "dragHandle");
		}
		return rh;
	}

	this.mouseMove = function(evt){
		this.curr = [evt.clientX, evt.clientY];
		this.curr.x = this.curr[0];
		this.curr.y = this.curr[1];
		if((this.isDragging)||(this.isResizing)){
			// FIXME: we should probably implement a distance threshold here!
			this.mouseDrag(evt);
		}else{
			var dh = this.checkForDrag(evt.target);
			var rh = this.checkForResize(evt.target);
			// FIXME: we also need to handle horizontal-only or vertical-only
			// reisizing
			if(rh||dh){
				if(rh){ this.overResizeHandle = true; }
				if(dh){ this.overDragHandle = true; }
				document.body.style.cursor = "move";
			}else{
				this.overResizeHandle = false;
				this.overDragHandle = false;
				document.body.style.cursor = "";
			}
		}
		// update hoverTarget only when necessaray!
		if(evt.target != this.hoverNode){ 
			this.hoverNode = evt.target;
			var tdt = dojo.webui.widgetManager.getWidgetFromEvent(evt);
			this.hoverTarget = tdt;
			while((tdt)&&(!tdt["dragEnter"])&&(tdt != dojo.webui.widgetManager.root)){
				tdt = tdt.parent;
			}
			if(tdt != dojo.webui.widgetManager.root){
				if(tdt != this.dropTarget){
					if(this.dropTarget){
						this.dropTarget.dragLeave(this.dragSource);
					}
					this.dropTarget = tdt;
					if((this.dropTarget)&&(this.dropTarget["dragEnter"])){
						this.dropTarget.dragEnter(this.dragSource);
					}
				}
			}
		}
	}

	this.mouseDown = function(evt){
		this.init = this.curr;
		if(!this.hoverTarget){ return; }
		if(this.overResizeHandle){
			this.isResizing = true;
			this.resizeTarget = this.hoverTarget;
			if(this.resizeTarget["startResize"]){
				evt.preventDefault();
				evt.stopPropagation();
				this.resizeTarget.startResize(this.curr);
			}
			if(this.dragSource){
				this.dragSource.startDrag();
			}
		}else{
			if((this.hoverTarget["isDragSource"] === true)||(this.overDragHandle)){
				this.isDragging = true;
				this.dragSource = this.hoverTarget;
				while((this.dragSource)&&(!this.dragSource["isDragSource"])){
					this.dragSource = this.dragSource.parent;
				}
				if(!this.dragSource){
					this.isDragging = false;
				}else{
					document.body.style.cursor = "move";
				}
				this.dragSource.startDrag();
				var di = this.dragSource.getDragIcon();
				if(di){
					if(!this.dragIcon){
						this.dragIcon = document.createElement("span");
						with(this.dragIcon.style){
							position = "absolute";
							left = this.curr.x+15+"px";
							top = this.curr.y+15+"px";
							border = margin = padding = "0px";
							zIndex = "1000";
						}
						document.body.appendChild(this.dragIcon);
						dojo.xml.htmlUtil.setOpacity(this.dragIcon, 0.5);
					}
					this.dragIcon.appendChild(di);
					this.dragIcon.style.display = "";
				}
			}
		}
	}

	// turns out that these are pretty useless
	this.mouseOver = function(nativeEvt){ return; }
	this.mouseOut = function(nativeEvt){ return; }

	this.mouseUp = function(nativeEvt){ 
		this.drop(nativeEvt);
		if((this.isResizing)||(this.isDragging)){
			if(this.resizeTarget){
				this.resizeTarget.endResize(this.curr);
			}else{
				this.dragSource.endDrag();
				this.dragSource.selection.clear();
			}
			this.dropTarget = null;
			this.resizeTarget = null;
			this.isResizing = false;
			this.overResizeHandle = false;

			this.dragSource = null;
			this.isDragging = false;
			this.overDragHandle = false;

			document.body.style.cursor = "";
			if(this.dragIcon){
				this.dragIcon.style.display = "none";
				with(this.dragIcon){
					while(firstChild){
						removeChild(firstChild);
					}
				}
			}
		}
		this.init = [];
	}

	this.mouseDrag = function(evt){ 
		if(this.isResizing){
			if(this.resizeTarget){
				this.resizeTarget.updateResize(this.curr);
				evt.preventDefault();
				evt.stopPropagation();
			}
		}else if(this.isDragging){
			evt.preventDefault();
			evt.stopPropagation();
			this.drag(evt);
			if(this.dragIcon){
				this.dragIcon.style.left = this.curr.x+15+"px";
				this.dragIcon.style.top = this.curr.y+15+"px";
			}
		}
	}

}

try{
(function(){
	var tf = function(){
		var rw = null;
		if(dojo.render.html){
			function rwClass(){
				dojo.webui.Widget.call(this);
				dojo.webui.DomWidget.call(this, true);
				dojo.webui.HTMLWidget.call(this);
				this.buildRendering = function(){ return; }
				this.destroyRendering = function(){ return; }
				this.postInitialize = function(){ return; }
				this.cleanUp = function(){ return; }
				this.widgetType = "HTMLRootWidget";
			}
			rw = new rwClass();
			rw.domNode = document.body;
			// FIXME: need to attach to DOM events and the like here
			
			var htmldm = dojo.webui.htmlDragAndDropManager;
			dojo.event.connect(document, "onmousemove", htmldm, "mouseMove");
			dojo.event.connect(document, "onmouseover", htmldm, "mouseOver");
			dojo.event.connect(document, "onmouseout", htmldm, "mouseOut");
			dojo.event.connect(document, "onmousedown", htmldm, "mouseDown");
			dojo.event.connect(document, "onmouseup", htmldm, "mouseUp");

		}else if(dojo.render.svg){
			// FIXME: fill this in!!!
			function rwClass(){
				dojo.webui.Widget.call(this);
				dojo.webui.DomWidget.call(this, true);
				dojo.webui.SVGWidget.call(this);
				this.buildRendering = function(){ return; }
				this.destroyRendering = function(){ return; }
				this.postInitialize = function(){ return; }
				this.cleanUp = function(){ return; }
				this.widgetType = "SVGRootWidget";
			}
			rw = new rwClass();
			rw.domNode = document.documentElement;
		}
		var wm = dojo.webui.widgetManager;
		wm.root = rw;
		wm.add(rw);

		// extend the widgetManager with a getWidgetFromNode method
		wm.getWidgetFromNode = function(node){
			var filter = function(x){
				if(x.domNode == node){
					return true;
				}
			}
			var widgets = [];
			while((node)&&(widgets.length < 1)){
				widgets = this.getWidgetsByFilter(filter);
				node = node.parentNode;
			}
			if(widgets.length > 0){
				return widgets[0];
			}else{
				return null;
			}
		}

		wm.getWidgetFromEvent = function(domEvt){
			return this.getWidgetFromNode(domEvt.target);
		}

		wm.getWidgetFromPrimitive = wm.getWidgetFromNode;
	}

	// make sure we get called when the time is right
	dojo.event.connect(dojo.hostenv, "loaded", tf);
})();
}catch(e){ alert(e); }

