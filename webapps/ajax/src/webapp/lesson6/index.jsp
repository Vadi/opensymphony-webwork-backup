<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>AJAX-based form submitting</title>
    <%@ include file="../commonInclude.jsp" %>
</head>

<body>

<p>
    Allows a form to be submitted asynchronously. The resulting content can be placed in any form
    element (the same one as the form, or another). Javascript can also be executed on completion.
</p>

<h2>Usage</h2>

<p>
    The tag takes the basic form of:

<div class="source">
    <pre>
        &lt;ww:form
        id='formId'
        cssStyle="border: 1px solid green;"
        action='/AjaxRemoteForm.action'
        method='post'
        theme="ajax" &gt;

        &lt;ww:submit value="GO" theme="ajax" &gt;
        &lt;ww:param name="resultDivId" &gt;formId&lt;/ww:param&gt;
        &lt;ww:param name="onLoadJS" &gt;alert('form submitted');"&lt;/ww:param&gt;
        &lt;/ww:submit&gt;

        &lt;ww:form&gt;
    </pre>
</div>

<div>
    To enable remote form submitting 2 things need to be done.
    <ol>
        <li>The ww:form tag needs to be used, with the theme="ajax"</li>
        <li>The ww:submit tag needs to be used, with the theme="ajax"</li>
    </ol>
</div>

<div>
    There are 2 ww:param tags that are used in conjuction with the ajax theme
    for the ww:submit. These are:
    <ul>
        <li>&lt;ww:param name="resultDivId" &gt;formId&lt;/ww:param&gt; -
            This defines the HTML node id (most likely a DIV) that the resulting
            content should be placed. This can be the same id as the form, or a
            different id altogether.</li>
        <li>&lt;ww:param name="onLoadJS" &gt;alert('form submitted');"&lt;/ww:param&gt; -
            Defines any javascript that will be executed after the form has been submitted.</li>
    </ul>
</div>

<h2>Configuration</h2>

<p>
    There is common javascript configuration that needs to be present for the AJAX component to work. It is
    suggested that this is placed in a common page decorator to avoid duplication.
    <div class="source">
        <pre>
            &lt;script language="JavaScript" type="text/javascript"&gt;
            // Dojo configuration
            djConfig = {
            baseRelativePath: "&lt;ww:url value="webwork/dojo/"/&gt;",
            isDebug: false
            };
            &lt;/script&gt;

            &lt;script language="JavaScript" type="text/javascript"
            src="&lt;ww:url value="/webwork/dojo/__package__.js" /&gt;"&gt;&lt;/script&gt;

            &lt;script language="JavaScript" type="text/javascript"&gt;
            dojo.hostenv.loadModule("dojo.io.BrowserIO");
            dojo.hostenv.loadModule("dojo.event.topic");
            dojo.hostenv.loadModule("webwork.widgets.Bind");
            dojo.hostenv.loadModule("webwork.widgets.BindButton");
            &lt;/script&gt;
        </pre>
    </div>

</p>

<h2>Examples</h2>

<p>
    <ol>
        <li>
            <a href="example9.jsp">Form examples</a>
        </li>
    </ol>
</p>

</body>
</html>