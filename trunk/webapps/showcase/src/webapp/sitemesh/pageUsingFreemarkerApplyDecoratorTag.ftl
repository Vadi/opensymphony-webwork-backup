
<#assign page=JspTaglibs["/WEB-INF/sitemesh-page.tld"] />

<html>
	<head>
		<title>WebWork showcase - sitemesh</title>	
	</head>
	<body>
		<h1>Example using Freemarker ApplyDecorator Tag</h1>
		
		<@page.applyDecorator name="panelDec" page="/sitemesh/panel1.ftl" />
		
		<hr/>
		
		<@page.applyDecorator name="panelDec" page="/sitemesh/panel2.ftl" />
		
	</body>
</html>

