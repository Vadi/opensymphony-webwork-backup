<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="commonInclude.jsp" />
    </head>
  <body>

        No such URL
        <ww:remotediv id="nourl" url="'/AjaxNoUrl.jsp'" errorText="'Could not contact server'" showErrorTransportText="'true'" />

  </body>
</html>