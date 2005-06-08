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
    &lt;ww:div
        id="fiveseconds"
        cssStyle="border: 1px solid yellow;"
        href="/tutorial/ajax/AjaxTest.action"
        theme="ajax"
        delay="1000"
        updateFreq="5000"
        errorText="There was an error"
        loadingText="reloading" &gt;loading now&lt;/ww:div&gt;
</pre>
                    </div>

    <ww:div
        id="fiveseconds"
        cssStyle="border: 1px solid yellow;"
        href="/tutorial/ajax/AjaxTest.action"
        theme="ajax"
        delay="1000"
        updateFreq="5000"
        errorText="There was an error"
        loadingText="reloading" >loading now</ww:div>


  </body>
</html>
