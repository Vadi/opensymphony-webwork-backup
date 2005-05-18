<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="commonInclude.jsp" />
    </head>
  <body>

        Update every 5 secs
        <ww:remotediv id="fivesec" url="'/AjaxTest.action'" updateFreq="5" />

  </body>
</html>