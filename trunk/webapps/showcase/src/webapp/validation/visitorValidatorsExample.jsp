<%-- 
	visitorValidatorsExample.jsp
	
	@author tm_jee
	@version $Date$ $Id$
--%>


<%@taglib uri="/webwork" prefix="ww" %>

<html>
<head><title>Showcase - Validation - VisitorValidatorsExample </title>
		<ww:url id="siteCss" value="/validation/validationExamplesStyles.css" includeContext="true" />
		<link rel="stylesheet" type="text/css" href='<ww:property value="%{siteCss}" />'>
</head>
<body>
	<ww:form method="POST" action="submitVisitorValidatorsExamples" namespace="/validation">
		<ww:textfield name="user.name" label="User Name" />
		<ww:textfield name="user.age" label="User Age" />
		<ww:textfield name="user.birthday" label="Birthday" />
		<ww:submit label="Submit" />
	</ww:form>
	
	<ww:include value="footer.jsp" />
</body>
</html>

