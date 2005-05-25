<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="../commonInclude.jsp" />
    </head>
  <body>

                    A simple DIV's that cannot contact the server:<br/>

                    <div class="source">
                        <pre>
                            &lt;ww:remotediv id="error1" url="/tutorial/ajax/AjaxNoUrl.jsp" errorText="Could not contact server" /&gt;
                        </pre>
                    </div>

                    <ww:remotediv id="error1" url="/tutorial/ajax/AjaxNoUrl.jsp" errorText="Could not contact server" />


  </body>
</html>
