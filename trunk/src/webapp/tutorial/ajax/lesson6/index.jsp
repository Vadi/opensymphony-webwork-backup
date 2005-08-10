<%@ taglib prefix="ww" uri="webwork" %>
<html>
    <head>
        <title>Tabbed Panes</title>
        <%@ include file="../commonInclude.jsp" %>
    </head>

    <body>
        <p>
        The tabbed component is the first of the more complex ajax components.
        </p>

        FORM ATTRIBUTES:

            private String errorText;
            private boolean showErrorTransportText;
            private String notifyTopics;
            private String useAjax;
            private String resultDivId;
            private String onLoadJS;
            private String listenTopics;
        
        <h2>Usage</h2>
        <p>
        Here is example code for the tabbel pannel, using both a local tab and a remote tab:
        <div class="source">
<pre>
&lt;ww:topicScope&gt;

    &lt;ww:tabbedPanel id="test" &gt;
        &lt;ww:panel id="one" tabName="one" &gt;
            This is the first pane&lt;br/&gt;
        &lt;/ww:panel&gt;
        &lt;ww:remotepanel url="/tutorial/ajax/AjaxTest.action" id="two" tabName="remote panel two" /&gt;
    &lt;/ww:tabbedPanel&gt;

&lt;/ww:topicScope&gt;
</pre>
        </div>
        </p>

                <p>
                The tabbel panel container tag has the following structure and attributes:

                <div class="source">
<pre>
    &lt;ww:tabbedPanel
        id="remotediv1"
        theme="remotediv1"
        template="remote panel two"&gt; {tab tags} &lt;/ww:panel&gt;
</pre>
                </div>

                The attributes are:
                <ul>
                    <li>id - the unique id for the html element</li>
                    <li>theme - the theme to use</li>
                    <li>template - the template to use</li>
                </ul>
                </p>

                <p>
                The local panel tag has the following structure and attributes:

                <div class="source">
<pre>
    &lt;ww:panel
        id="remotediv1"
        tabName="remote panel two"&gt; your content &lt;/ww:panel&gt;
</pre>
                </div>

                The attributes are:
                <ul>
                    <li>id - the unique id for the html element</li>
                    <li>tabName - the text to display to the user as the name of the tab, in the top tab list</li>
                </ul>
                </p>

                <p>
                The remote panel tag has the following structure and attributes:

                <div class="source">
<pre>
    &lt;ww:remotepanel
        id="remotediv1"
        tabName="remote panel two"
        url="/MyAction.action"
        loadingText="loading now"
        reloadingText="reloading page"
        errorText="There was a problem contacting the server!"
        showErrorTransportText="true"
        topicName="topic1, topic2" /&gt;
</pre>
                </div>

                The attributes are:
                <ul>
                    <li>id - the unique id for the html element</li>
                    <li>tabName - the text to display to the user as the name of the tab, in the top tab list</li>
                    <li>url - the url to obtain the contents of the DIV from</li>
                    <li>updateFreq (can use altSyntax) - how often to update the contents.  A value of 0 only updates the contents once.</li>
                    <li>loadingText - the text to display to the user while the contents are loading the very first time</li>
                    <li>reloadingText - the text to display to the user while the contents are reloading (every time after the very first time)</li>
                    <li>errorText - the text to display to the user if there is an error (i.e. error contacting the url specified)</li>
                    <li>showErrorTransportText (true/false, can use altSyntax) - whether you want to display the transports error text to the user</li>
                    <li>topicName - a comma delimited list of topic names to listen to.
                            If a message is recieved the DIV tag contents will be refreshed.
                            Please see the <a href="../lesson4">Topics</a> lesson for more details on additional configuration.</li>
                </ul>
                </p>

        <p>
        The tabbed component utilizes the event framework.  Because of this, it is important to
        remember that the tabbed component needs to be placed
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

&lt;link rel="stylesheet" type="text/css" href="&lt;ww:url value="/webwork/tabs.css"/&gt;"&gt;

</pre>

        <p>
        If you are looking for the "nifty" rounded corner look, there is additional configuration.  This assumes that
        the background color of the tabs is white.  If you are using a different color, please modify the parameter
        in the Rounded() method.
        </p>

<div class="source">
<pre>
&lt;link rel="stylesheet" type="text/css" href="&lt;ww:url value="/webwork/niftycorners/niftyCorners.css"/&gt;"&gt;
&lt;link rel="stylesheet" type="text/css" href="&lt;ww:url value="/webwork/niftycorners/niftyPrint.css"/&gt;" media="print"&gt;
&lt;script type="text/javascript" src="&lt;ww:url value="/webwork/niftycorners/nifty.js"/&gt;"&gt;&lt;/script&gt;
&lt;script type="text/javascript"&gt;
    window.onload=function(){
        if(!NiftyCheck())
            return;
        Rounded("li.tab_selected","top","white","transparent","border #ffffffS");
        Rounded("li.tab_unselected","top","white","transparent","border #ffffffS");
        // "white" needs to be replaced with the background color
    }
&lt;/script&gt;

</pre>
        </div>

        </p>

        <h2>Examples</h2>

        <p>
            <ol>
                <li>
                    <a href="example8.jsp">Various remote and local tabbed panels working together</a>
                </li>

            </ol>


        </p>

    </body>
</html>