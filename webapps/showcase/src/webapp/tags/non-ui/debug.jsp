<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>UI Tags Example: Debug</title>
    <ww:head/>
</head>

<body>
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