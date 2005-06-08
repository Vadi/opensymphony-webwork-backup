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
    &lt;ww:div
        id="twoseconds"
        cssStyle="border: 1px solid yellow;"
        href="%{#url}"
        theme="ajax"
        delay="2000"
        updateFreq="2000"
        errorText="There was an error"
        loadingText="loading..." &gt;Initial Content&lt;/ww:div&gt;
</pre>
    </div>

	<ww:url id='url' value="/tutorial/ajax/AjaxTest.action"/>
    <ww:div
        id="twoseconds"
        cssStyle="border: 1px solid yellow;"
        href="%{#url}"
        theme="ajax"
        delay="2000"
        updateFreq="2000"
        errorText="There was an error"
        loadingText="loading..." >Initial Content</ww:div>

  </body>
</html>
