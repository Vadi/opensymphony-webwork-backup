<%@ taglib prefix="ww" uri="webwork" %>
<html>
    <head>
        <title>AJAX-based remote link tag</title>
        <%@ include file="../commonInclude.jsp" %>
    </head>

    <body>
        <p>
        The remote link tag is one of the simpler AJAX components.  It works in the same way as
        a normal HTML &lt;a href="..." /&gt; tag would, but when the user clicks on the link an
        AJAX call is made to the server.  Any response coming back is evaluated as a JavaScript
        expression.
        </p>

        <h2>Usage</h2>
        <p>
        The tag takes the basic form of:
        <div class="source">
<pre>
&lt;ww:remotelink
    id="link1"
    href="/AjaxRemoteLink.action"
    showErrorTransportText="true"
    errorText="An Error ocurred"
    notifyTopics="testTopic" &gt;My remote Link&lt;/ww:remotelink&gt;
</pre>
        </div>
        The attributes are:
        <ul>
            <li>id - the unique id for the html element</li>
            <li>href - the url to submit when the link is clicked.  This can contain %{} elements in order
                        to provide id's or other server-side procession information for this specific link.</li>
            <li>errorText - the text to display to the user if there is an error (i.e. error contacting the url specified)</li>
            <li>showErrorTransportText (true/false, can use altSyntax) - whether you want to display the transports error text to the user</li>
            <li>notifyTopics - the topics to publish a message to after a javascript result is returned and evaluated.
                            Please see the <a href="lesson4.jsp">Topics</a> lesson for more details on additional configuration.</li>
        </ul>
        </p>

        <p>
        Additionally, there are pass though attributes.  These are:
        <ul>
            <li>cssClass</li>
            <li>cssStyle</li>
            <li>template</li>
            <li>theme</li>
        </ul>
        </p>

        <h2>Configuration</h2>
        <p>
        There is common javascript configuration that needs to be present for the AJAX component to work.  It is
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
&lt;script language="JavaScript" type="text/javascript"
        src="&lt;ww:url value="/webwork/AjaxComponents.js" /&gt;"&gt;&lt;/script&gt;

&lt;script language="JavaScript" type="text/javascript"&gt;
    dojo.hostenv.loadModule("dojo.io.BrowserIO");
    dojo.hostenv.loadModule("dojo.event.topic");
    dojo.hostenv.loadModule("webwork.widgets.Bind");
    dojo.hostenv.loadModule("webwork.widgets.BindAnchor");
&lt;/script&gt;
</pre>
        </div>

        </p>

        <h2>Examples</h2>

        <p>
            <ol>
                <li>
                    <a href="example7.jsp">Various remote links and remote DIV's working together</a>
                </li>

            </ol>


        </p>

    </body>
</html>