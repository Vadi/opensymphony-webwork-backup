<#macro heading>
	<h1><#nested/></h1>
</#macro>
<#macro msg>
	<p class="msg"><#nested/></p>
</#macro>
<#macro title>
	<p class="title"><#nested/></p>
</#macro>
<#macro page title>
	<html>
		<head>
			<title>WebWork Example : ${title}</title>
			<link rel="stylesheet" href="styles.css">
		</head>
		<body>
			<@heading>${title}</@>
			<a href='/'>back to menu</a>
			<#nested/>
		</body>
	</html>
</#macro>
