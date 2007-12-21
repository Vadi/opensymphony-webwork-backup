<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Showcase - Validation - Collection Validator</title>
</head>
<body>

	<ww:form action="submitCollectionValidationExample" namespace="/validation">
		<ww:iterator value="%{new int[2]}" status="stat">
			<ww:label label="Agent" value="%{#stat.index}" />
			<ww:textfield label="Name" name="%{'persons['+#stat.index+'].name'}" value="" />
			<ww:textfield label="Age" name="%{'persons['+#stat.index+'].age'}" value="" />
		</ww:iterator>
		<ww:submit />
	</ww:form>

</body>
</html>


