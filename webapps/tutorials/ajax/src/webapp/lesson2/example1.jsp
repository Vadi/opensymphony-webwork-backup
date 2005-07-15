<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="../commonInclude.jsp" />
    </head>
  <body>

                    A simple DIV that refreshes only once after 5 seconds:<br/>

                    <div class="code">
<pre>
    &lt;ww:div
        id="once"
        cssStyle="border: 1px solid yellow;"
        href="/AjaxTest.action"
        theme="ajax"
        delay="5000"
        loadingText="loading..." &gt;Initial Content&lt;/ww:div&gt;
</pre>
                    </div>

                    <ww:div
                        id="once"
                        theme="ajax"
                        cssStyle="border: 1px solid yellow;"
                        href="/AjaxTest.action"
                        delay="5000"
                        loadingText="loading..." >
                    Initial Content</ww:div>

  </body>
</html>
