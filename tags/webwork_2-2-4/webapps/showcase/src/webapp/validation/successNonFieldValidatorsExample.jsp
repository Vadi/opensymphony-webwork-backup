<%--
	successNonFieldValidatorsExample.jsp
	
	@author tm_jee
	@version $Date$ $Id$
--%>


<%@taglib uri="/webwork" prefix="ww" %>

<html>
	<head><title>Showcase - Validation - SuccessNonFieldValidatorsExample</title></head>
	<body>
		<h1>Success !</h1>
		<table>
			<tr>
				<td>Some Text: </td>
				<td><ww:property value="someText" /></td>
			</tr>
			<tr>
				<td>Some Text Retyped: </td>
				<td><ww:property value="someTextRetype" /></td>
			</tr>
			<tr>
				<td>Some Text Retyped Again: </td>
				<td><ww:property value="someTextRetypeAgain" /></td>
			</tr>
		</table>
		
		<ww:include value="footer.jsp" />
	</body>
</html>

