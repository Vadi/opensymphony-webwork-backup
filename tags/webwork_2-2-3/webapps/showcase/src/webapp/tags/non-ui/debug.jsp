<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>UI Tags Example: Debug</title>
    <ww:head/>
</head>
<ww:url id="console" action="editPerson"  namespace="/person" includeParams="none" encode="false">
    <ww:param name="debug" value="'console'" />
</ww:url>
<ww:url id="xml" action="editPerson"  namespace="/person" includeParams="none" encode="false">
    <ww:param name="debug" value="'xml'" />
</ww:url>

<body>
	<h1>Debug the ValueStack</h1>

	<p/>
	Add <tt style="font-size: 12px; font-weight:bold;color: blue;">debug=console</tt> or
    <tt style="font-size: 12px; font-weight:bold;color: blue;">debug=xml</tt> to the URL parameters.
    <p/>
    <h3>Show the Debug Console: debug=console</h3>
    <p/>
    <b>Sample URL:</b> <ww:property value="console" />
    <p/>
    <a href="<ww:property value="console" />">ValueStack in Debug Console</a>
    <p/>
    <b>Usage:</b> Just enter OGNL expressions into the console window and press Return. <br>
    The OGNL expression will be submitted against the current action and the result will be shown within the console output.<br>
    <p/>
    <b>Example:</b>
    Enter <code>persons</code> into the command line and hit Return
    <p/>
    <h3>Dump the ValueStack as XML: debug=xml</h3>
    <p/>
    <b>Sample URL:</b> <ww:property value="xml" />
    <p/>
    <a href="<ww:property value="xml" />">ValueStack Debug as XML</a>
    <p/>

	<h1>Debug Tag Usage</h1>

	<p/>
	This page shows a simple example of using the debug tag.  <br/>
	Just add <tt style="font-size: 12px; font-weight:bold;color: blue;">&lt;ww:debug /&gt;</tt> to your JSP page
    and you will see the debug link.
    <p/>
    Just click on the Debug label to see the WebWork ValueStack Debug information.
    <p/>
    <ww:debug />
</body>
</html>