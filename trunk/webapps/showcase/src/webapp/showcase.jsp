<%-- 
	showcase.jsp
	
	@version $Date$ $Id$
--%>

<%@ taglib uri="/webwork" prefix="ww" %>
<html>
<head>
    <title>Showcase</title>
</head>

<body>
<h1>Showcase samples</h1>

<p>The given examples will demonstrate the usages of all WebWork tags as well as validations etc.</p>

<p>
	<ul>
		<!-- continuation -->
		<li><ww:url id="url" namespace="/continuations" action="guess"/><ww:a href="%{url}">Continuations Example</ww:a></li>
		
		<!-- tags -->
		<li><ww:url id="url" value="/tags"/><ww:a href="%{url}">Tags Examples</ww:a></li>
		
		<!-- crud -->
		<li><ww:url id="url" value="/empmanager"/><ww:a href="%{url}">CRUD Examples</ww:a></li>
		
		<!-- validation -->
		<li><ww:url id="url" value="/validation"/><ww:a href="%{url}">Validation Examples</ww:a></li>
	</ul>
</p>

</body>
</html>
