<%@ taglib prefix="ww" uri="webwork" %>
<html>
    <head>
        <title>JavaScript Topics</title>
        <%@ include file="../commonInclude.jsp" %>
    </head>

    <body>
        <p>
        As well as remotely recieving and sending data via AJAX, there also exists a mechanism
        which allows tags to communicate with each other.  This is in the form of JavaScript
        events, which can be published on a topic under a specific topic name.
        </p>

        <h2>Usage</h2>
        <p>
        The tag has the following structure and attributes:
        <div class="source">
<pre>
&lt;ww:topicScope&gt;

    DIV Component:
    &lt;ww:remotediv id="two" url="/AjaxTest.action" topicName="mylink1_click,mylink2_click" /&gt;

    Link Component:
    &lt;ww:remotediv id="three" url="/AjaxTest.action" topicName="mylink2_click" /&gt;

&lt;/ww:topicScope&gt;
</pre>
        </div>
        Currently the remote DIV tags listen on a specific topic, and remote link components publish
        on a specific topic.  Please see the details on other components as they are developed.
        </p>

        <p>
        The important thing to remember is that any component that utilizes events must be placed
        within the &lt;ww:topicScope/&gt; tags. This is so javascript can be generated during the
        page rendering process.
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
        parseWidgets: false,
        isDebug: false
    };
&lt;/script&gt;

&lt;script language="JavaScript" type="text/javascript" src="&lt;ww:url value="/webwork/dojo/__package__.js" /&gt;"&gt;&lt;/script&gt;
&lt;script language="JavaScript" type="text/javascript" src="&lt;ww:url value="/webwork/AjaxComponents.js" /&gt;"&gt;&lt;/script&gt;

&lt;script language="JavaScript" type="text/javascript"&gt;
    dojo.hostenv.loadModule("dojo.io.BrowserIO");
    dojo.hostenv.loadModule("dojo.event.topic");
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