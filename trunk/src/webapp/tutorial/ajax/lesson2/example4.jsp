<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="../commonInclude.jsp" />
    </head>
  <body>

                    A simple DIV that updates every 5 seconds with loading text and reloading text
                    (because the action executes very quickly, the text will be difficult to see):<br/>

                    <div class="code">
                        <pre>
                            &lt;ww:remotediv id="fiveseconds" url="/tutorial/ajax/AjaxTest.action" updateFreq="5" loadingText="loading now" reloadingText="reloading page" /&gt;
                        </pre>
                    </div>

                    <ww:remotediv id="fiveseconds" url="/tutorial/ajax/AjaxTest.action" updateFreq="5" loadingText="loading now" reloadingText="reloading page" />


  </body>
</html>
