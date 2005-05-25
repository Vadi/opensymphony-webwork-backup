<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="../commonInclude.jsp" />
    </head>
  <body>

                   A simple DIV that updates every 2 seconds:<br/>

                    <div class="code">
                        <pre>
                            &lt;ww:remotediv id="twoseconds" url="%{'/tutorial/ajax/AjaxTest.action'}" updateFreq="2" /&gt;
                        </pre>
                    </div>

                    <ww:remotediv id="twoseconds" url="%{'/tutorial/ajax/AjaxTest.action'}" updateFreq="2" />


  </body>
</html>
