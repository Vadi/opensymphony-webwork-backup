<%-- 
   fieldValidatorExample.jsp
   
   @author tm_jee
   @version $Date$ $Id$
--%>

<%@taglib prefix="ww" uri="/webwork" %>

<html>
	<head>
		<title>Showcase - Validation - Field Validators Example</title>
		<ww:url id="siteCss" includeContext="true" value="/validation/validationExamplesStyles.css" />
		<ww:head />
		<!--  link rel="stylesheet" type="text/css" href='<ww:property value="%{siteCss}" />'-->
		</style>
	</head>
	<body>
	
	<!-- START SNIPPET: fieldValidatorsExample -->
	
		<h3>All Field Errors Will Appear Here</h3>
		<ww:fielderror />
		<hr/>
		
		<h3>Field Error due to 'Required String Validator Field' Will Appear Here</h3>
		<ww:fielderror>
			<ww:param value="%{'requiredStringValidatorField'}" />
		</ww:fielderror>
		<hr/>
		
		<h3>Field Error due to 'String Length Validator Field' Will Appear Here</h3>
		<ww:fielderror>
			<ww:param>stringLengthValidatorField</ww:param>
		</ww:fielderror>
		<hr/>
	
		<ww:form action="submitFieldValidatorsExamples" namespace="/validation" method="POST">
			<ww:textfield label="Required Validator Field" name="requiredValidatorField" />
			<ww:textfield label="Required String Validator Field" name="requiredStringValidatorField" />
			<ww:textfield label="Integer Validator Field" name="integerValidatorField" />
			<ww:textfield label="Date Validator Field" name="dateValidatorField" />
			<ww:textfield label="Email Validator Field" name="emailValidatorField" />
			<ww:textfield label="String Length Validator Field" name="stringLengthValidatorField" />
			<ww:textfield label="Regex Validator Field" name="regexValidatorField"/>
			<ww:textfield label="Field Expression Validator Field" name="fieldExpressionValidatorField" />
			<ww:submit label="Submit" />
		</ww:form>
		
    <!-- END SNIPPET: fieldValidatorsExample -->
		
		
		<ww:include value="footer.jsp" />
	</body>
</html>
