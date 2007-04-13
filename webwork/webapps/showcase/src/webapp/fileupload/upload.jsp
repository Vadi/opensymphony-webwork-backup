<%@ page 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/webwork" prefix="ww" %>
<html>
<head>
    <title>Showcase</title>
    <ww:head />
</head>

<body>
    <h1>Fileupload sample</h1>
	<p>There is a file upload limit of 1048576 bytes (approximately 2MB)</p>
	
	<ww:fielderror />
	
	<p/>
	
    <ww:form action="doUpload" method="POST" enctype="multipart/form-data">
        <ww:file name="upload" label="File"/>
        <ww:textfield name="caption" label="Caption"/>
        <ww:submit />
    </ww:form>
</body>
</html>

