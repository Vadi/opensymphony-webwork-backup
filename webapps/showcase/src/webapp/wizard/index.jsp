<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Showcase Wizard Example</title>
</head>
<body>

	<ww:url id="url" action="start" namespace="/wizard" />	
	<ww:a href="%{#url}">Wizard Example</ww:a>
	<ww:url id="url" action="startTabbedWizard" namespace="wizard" />
	<ww:a href="%{#url}">Tabbed Panel Wizard Example</ww:a>
	
</body>
</html>

