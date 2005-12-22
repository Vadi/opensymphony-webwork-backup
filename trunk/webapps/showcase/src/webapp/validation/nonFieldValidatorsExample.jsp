<%-- 
	nonFieldValidatorsExample.jsp
	
	@author tm_jee
	@version $Date$ $Id$
--%>


<%@taglib prefix="ww" uri="/webwork" %>

<html>
	<head>
		<title>Showcase - Validation - Non Field Validator Example</title>
		<ww:url id="siteCss" value="/validation/validationExamplesStyles.css" includeContext="true" />
		<link rel="stylesheet" type="text/css" href='<ww:property value="%{siteCss}" />'>
	</head>
	<body>
		<ww:if test="hasActionErrors()">
			<ww:iterator value="getActionErrors()" status="iteratorStatus">
			 	<ul>
					<li><span class="errorMessage"><ww:property value="top" /></span></li>
				</ul>
			</ww:iterator>
		</ww:if>
	
		<ww:form method="POST" action="submitNonFieldValidatorsExamples" namespace="/validation">
			<ww:textfield name="someText" label="Some Text" />
			<ww:textfield name="someTextRetype" label="Retype Some Text" />  
			<ww:textfield name="someTextRetypeAgain" label="Retype Some Text Again" />
			<ww:submit label="Submit" />
		</ww:form>
		
		<ww:include value="footer.jsp" />
	</body>
</html>

