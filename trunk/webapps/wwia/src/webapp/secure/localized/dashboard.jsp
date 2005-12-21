<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="webwork" prefix="ww"%>
<ww:set name="noNav" scope="request" value="true"/>
<html>
  <head>
    <title>Dashboard</title>
  </head>

  <body>
    <a href="newCategory.action"><ww:text name="text.createCategory"/> </a>

    <hr/>
    <ww:action name="categoryTree" executeResult="true"/>
  </body>
</html>