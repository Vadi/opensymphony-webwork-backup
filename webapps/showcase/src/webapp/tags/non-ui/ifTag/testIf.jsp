<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="ww" uri="/webwork" %>    
    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--  1 -->
<ww:if test="true">
	1]THIS SHOULD APPEAR <br/>
</ww:if>
<ww:else>
	THIS SHOULD NOT APPEAR <br/>
</ww:else>


<!--  2 -->
<ww:if test="false">
	THIS SHOULD NOT APPEAR <br/>
</ww:if>
<ww:elseif test="true">
	2]THIS SHOULD APPEAR <br/>
</ww:elseif>

<!--  3 -->
<ww:if test="false">
	THIS SHOULD NOT APPEAR <br/>
</ww:if>
<ww:elseif test="false">
	THIS SHOULD NOT APPEAR <br/>
</ww:elseif>
<ww:elseif test="true">
	3]THIS SHOULD APPEAR <br/>
</ww:elseif>
<ww:elseif test="true">
	THIS SHOULD NOT APPEAR <br/>
</ww:elseif>
<ww:else>
	THIS SHOULD NOT APPEAR <br/>
</ww:else>

<!-- 4 -->
<ww:if test="false">
	THIS SHOULD NOT APPEAR<br/>
</ww:if>
<ww:elseif test="false">
	THIS SHOULD NOT APPEAR<br/>
</ww:elseif>
<ww:else>
	4]THIS SHOULD APPEAR<br/>
</ww:else>

<!-- 5 -->
<ww:if test="false">
	THIS SHOULD NOT APPEAR<br/>
</ww:if>
<ww:elseif test="false">
	THIS SHOULD NOT APPEAR<br/>
</ww:elseif>
<ww:elseif test="false">
	THIS SHOULD NOT APPEAR<br/>
</ww:elseif>
<ww:elseif test="false">
	THIS SHOULD NOT APPEAR<br/>
</ww:elseif>
<ww:else>
	5]THIS SHOULD APPEAR<br/>
</ww:else>


</body>
</html>