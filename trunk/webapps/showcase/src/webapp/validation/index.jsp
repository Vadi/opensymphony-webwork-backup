<%--
	index.jsp

	@author tm_jee
	@version $Date$ $Id$
--%>

<%@taglib uri="/webwork" prefix="ww" %>

<html>
	<head>
		<title>Showcase - Validation</title>
	</head>
	<body>
		<h1>Validation Examples</h1>
		
		<ww:url id="fieldValidatorUrl" action="showFieldValidatorsExamples" namespace="/validation" />
		<ww:url id="nonFieldValidatorUrl" action="showNonFieldValidatorsExamples" namespace="/validation" />
		<ww:url id="visitorValidatorUrl" action="showVisitorValidatorsExamples" namespace="/validation" />
		<ww:url id="backToShowcase" action="showcase" namespace="/" />
		
		<ul>
			<li><ww:a href="%{fieldValidatorUrl}">Field Validators</ww:a></li>
			<li><ww:a href="%{nonFieldValidatorUrl}">Non Field Validator</ww:a></li>
			<li><ww:a href="%{visitorValidatorUrl}">Visitor Validator</ww:a></li>
			<li><ww:a href="%{backToShowcase}">Back To Showcase</ww:a>
		</ul>
	</body>
</html>

