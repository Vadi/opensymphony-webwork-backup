<%-- 
	successVisitorValidatorsExample.jsp
	
	@author tm_jee
	@version $Date$ $Id$
--%>



<%@taglib uri="/webwork" prefix="ww" %>

<html>
	<head><title>Showcase - Validation - SuccessVisitorValidatorsExameple</title></head>
	<body>
		<h1>Success !</h1>
		<table>
			<tr>
				<td>User Name:</td>
				<td><ww:property value="user.name" /></td>
			</tr>
			<tr>
				<td>User Age:</td>			
				<td><ww:property value="user.age" /></td>
			</tr>
			<tr>
				<td>User Birthday:</td>
				<td><ww:property value="user.birthday" /></td>
			</tr>
		</table>
		
		<ww:include value="footer.jsp" />
		
	</body>
</html>

