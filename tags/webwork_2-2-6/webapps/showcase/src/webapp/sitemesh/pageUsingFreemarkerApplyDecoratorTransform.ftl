
<html>
<head>
	<title>WebWork Showcase - Sitemesh</title>
</head>
<body>
	
	<h1>Example using Freemarker ApplyDecorator Transform</h1>
	
	<@sitemesh.applydecorator name="panel" page="/sitemesh/panel1.ftl" />
	
	<hr/>
	
	<@sitemesh.applydecorator name="panel" page="/sitemesh/panel2.ftl" />
	
</body>
</html>


