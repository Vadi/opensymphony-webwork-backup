<%@taglib uri="/webwork" prefix="ww" %>

<html>
<head>
	<title>Showcase - Tags - UI Tags</title>
</head>
<body>
	<h1>UI Tags</h1>
	
	<ul>
		<li><ww:url id="url" namespace="/tags/ui" action="example" method="input" /><ww:a href="%{url}">UI Example</ww:a></li>
		<%--li><ww:url id="url" namespace="/tags/ui" action="populateUsingIterator" method="input" /><ww:a href="%{url}">UI population using iterator tag</ww:a></li--%>
	</ul>
</body>
</html>
