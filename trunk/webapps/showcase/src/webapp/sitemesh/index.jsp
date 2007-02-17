<%@taglib prefix="ww" uri="/webwork" %>

<html>
	<head>
		<title>WebWork showcase - sitemesh</title>
	</head>
	<body>
		<ul>
			<li>
				<ww:url id="url" action="useFreemarkerApplyDecoratorTransform" namespace="/sitemesh" />
				<ww:a href="%{#url}">Using Freemarker applydecorator transform</ww:a>
			</li>
			<li>
				<ww:url id="url" action="useFreemarkerApplyDecoratorTag" namespace="/sitemesh" />
				<ww:a href="%{#url}">Using Freemarker applydecorator tag</ww:a>
			</li>
			<li>
				<ww:url id="url" action="useJspApplyDecoratorTag" namespace="/sitemesh" />
				<ww:a href="%{#url}">Using Jsp applyDecorator tag</ww:a>
			</li>
		</ul>
	</body>
</html>


