<%@ taglib prefix="ww" uri="webwork" %>
<html>
    <head>
        <title>AJAX-based remote DIV tag</title>
        <%@ include file="../commonInclude.jsp" %>
    </head>

    <body>

        <p>
        The remote DIV tag is one of the simpler AJAX components.  It works in the same way as
        a normal HTML DIV tag would, but loads it's contents from a URL specified in the tag.
        </p>

        <h2>Usage</h2>
        <p>
        The tag takes the basic form of:

        <div class="source">
<pre>
&lt;ww:remotediv
    id="remotediv1"
    href="/MyAction.action"
    updateFreq="5000"
    delay="2000"
    loadingText="reloading..."
    errorText="There was a problem contacting the server!"
    showErrorTransportText="true"
    triggerTopics="topic1, topic2" /&gt; Initial Content &lt;/ww:remotediv&gt;
</pre>
        </div>

        The attributes are:
        <ul>
            <li>id - the unique id for the html element</li>
            <li>href - the url to obtain the contents of the DIV from</li>
            <li>updateFreq (can use altSyntax) - how often to update the contents.  A value of 0 only updates the contents once.</li>
            <li>delay (can use altSyntax) - how long to wait before the first remote call.  A value of 0 only updates the contents once.</li>
            <li>loadingText - the text to display to the user while the contents are being reloaded.</li>
            <li>errorText - the text to display to the user if there is an error (i.e. error contacting the url specified)</li>
            <li>showErrorTransportText (true/false, can use altSyntax) - true if you want to display the dojo transports error,  or the text to the user</li>
            <li>triggerTopics - a comma delimited list of topic names to listen to.
                    If a message is recieved the DIV tag contents will be refreshed.
                    Please see the <a href="">Topics</a> lesson for more details on additional configuration.</li>
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

        <p>
        There are also javascript functions to refresh the content, stop and start the refreshing of the
        component.  For the remote div with the component id "remotediv1":
        </p>

        <p>
        To start refreshing use the javascript:
<pre>
    remotediv1.start();
</pre>
        To stop refreshing use the javascript:
<pre>
    remotediv1.stop();
</pre>

        To refresh the content use the javascript:
<pre>
    remotediv1.bind();
</pre>
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

&lt;script language="JavaScript" type="text/javascript"&gt;
    dojo.hostenv.loadModule("dojo.io.BrowserIO");
    dojo.hostenv.loadModule("dojo.event.topic");
    dojo.hostenv.loadModule("webwork.widgets.Bind");
    dojo.hostenv.loadModule("webwork.widgets.BindDiv");
&lt;/script&gt;
</pre>
        </div>

        </p>

        <h2>Examples</h2>

        <p>
            <ol>
                <li>
                    <a href="example1.jsp">A simple DIV that refreshes only once</a>
                </li>

                <li>
                    <a href="example2.jsp">A simple DIV that updates every 2 seconds</a>
                </li>

                <li>
                    <a href="example3.jsp?period=3000">A simple DIV that obtains the update freq (3 secs) from the value stack/action</a>
                </li>

                <li>
                    <a href="example4.jsp">A simple DIV that updates every 5 seconds with loading text and reloading text</a>
                </li>

                <li>
                    <a href="example5.jsp">A simple DIV's that cannot contact the server</a>
                </li>

                <li>
                    <a href="example6.jsp">A simple DIV's that cannot contact the server and displays the transport error message</a>
                </li>

            </ol>


        </p>

    </body>
</html>