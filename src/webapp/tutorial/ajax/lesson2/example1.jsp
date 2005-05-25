<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="../commonInclude.jsp" />
    </head>
  <body>

                    A simple DIV that refreshes only once:<br/>

                    <div class="code">
                        <pre>
                            &lt;ww:remotediv id="once" url="%{'/tutorial/ajax/AjaxTest.action'}" /&gt;
                        </pre>
                    </div>

                    <ww:remotediv id="once" url="%{'/tutorial/ajax/AjaxTest.action'}" />


  </body>
</html>
