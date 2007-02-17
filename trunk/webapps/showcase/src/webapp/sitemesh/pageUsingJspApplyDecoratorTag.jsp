
<%@taglib prefix="ww" uri="/webwork" %>
<%@taglib prefix="page" uri="sitemesh-page" %>
<%@taglib prefix="decorator" uri="sitemesh-decorator" %>


<html>
	<head>
		<title>WebWork showcase - sitemesh</title>	
	</head>
	<body>
		<h1>Example using Freemarker ApplyDecorator Tag</h1>
		
		<page:applyDecorator name="panelJsp" page="/sitemesh/panel1.jsp" />
		
		<hr/>
		
		<page:applyDecorator name="panelJsp" page="/sitemesh/panel2.jsp" />
		
	</body>
</html>





