<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
    <title>WebWork2 Examples</title>
    <style type="text/css">
    BODY { font-size : 100%; }
    BODY, TD, TH, DIV, P {
        font-family : verdana, arial, helvetica, sans-serif;
        font-size : 9pt;
    }
    H1 {
        font-size : 14pt;
        border-bottom : 1px #ccc solid;
    }
    TABLE {
        border : 1px #ccc solid;
    }
    TH {
        text-align : left;
        background-color : #ddd;
    }
    UL {
        padding-bottom : 0.5em;
    }
    LI {
        margin-bottom : 0.5em;
    }
    .title {
        font-weight : bold;
        font-size : 11pt;
    }
    .msg {
        color : #f00;
    }
    </style>
</head>

<body>

<h1>WebWork2 Demos</h1>

<p class="msg">
Note, some of the demos might be slow the first time they are run as resources
are loaded and JSP pages are compiled.
</p>

<p class="title">
Simple Demos
</p>

<ul>
    <li><a href="SimpleCounter.action">Simple counter</a></li>
    
    <li><a href="VelocityCounter.action">Simple counter</a> using a
        <tt>VelocityResult</tt>
    </li>
    
    <li><a href="action.jsp">Simple counter</a> called via the 
        <tt>ww:action</tt> tag
    </li>
    
    <li><a href="action.jsp">Simple counter</a> being called via the
        <tt>#tag(Action)</tt> tag using Velocity as the view
    </li>
    
    <li><a href="bean.jsp">Simple counter</a> being called via the
        <tt>ww:bean</tt> tag
    </li>
</ul>


<p class="title">
UI Tag Demos
</p>

<ul>
    <li><a href="TagTest.action">Sample usages of the UI tags</a></li>
    
    <li><a href="VelocityTagTest.action">Sample usages of the UI tags</a> using
        Velocity as the view
    </li>
    
    <li><a href="tags.vm">Sample usages of the UI tags</a> using directly
        accessed Velocity templates.
    </li>
</ul>


<p class="title">
Stress Tests
</p>

<ul>
    <li><a href="CountryTest.action">Performance benchmark of a 200+ element
        array</a>
    </li>
</ul>


<p class="title">
Iterator Demos
</p>

<ul>
    <li><a href="IteratorTest.action">Iterator tag</a> example</li>
</ul>


<p class="title">
Form Demos
</p>

<ul>
    <li><a href="showForm.action">Form tag and Token tag example</a> for hidden
        transaction tokens
    </li>
    
    <li><a href="showVelocityForm.action">Form tag and Token tag example</a> for
        hidden transaction tokens using Veloctity as the view
    </li>
    
    <li><a href="form2.jsp">Form tag and Token tag example</a> for hidden
        transaction tokens. This shows the original result rather than an error
        (<tt>TokenSessionInterceptor</tt>).
    </li>
</ul>


<p class="title">
Exceptions
</p>
    
<ul>
    <li><a href="exception.action">Example of what happens when an exception is
        thrown</a>
    </li>
</ul>


<p class="title">
Configuration Browser
</p>

<ul>
    <li><a href="config-browser/actionNames.action">View the WebWork2
        configuration browser</a>
        <p>
        This is an example of a reusable set of Actions and views bundled as a
        Jar file. The config browser can be included with any WebWork2
        application by dropping the Jar file in your classpath (usually under
        <tt>WEB-INF/lib</tt>) and adding
        <tt>&lt;include file=&quot;config-browser.xml&quot;/&gt;</tt> to your
        <tt>xwork.xml</tt> file
        </p>
    </li>
</ul>


<p class="title">
Validation Demos
</p>

<ul>
    <li><a href="basicValidation.action">Basic validation example</a></li>
    
    <li><a href="visitorValidation.action">Visitor validation example</a></li>
    
    <li><a href="expressionValidation.action">Visitor validation example</a>
        with expression validation
    </li>
</ul>


<p class="title">
Jasper Reports Demos
</p>

<ul>    
    <li><a href="jasperTest.action">Jasper Reports PDF example</a></li>
    
    <li><a href="jasperHTMLTest.action">Jasper Reports HTML example</a></li>
    
    <li><a href="jasperXMLTest.action">Jasper Reports XML example</a></li>
    
    <li><a href="jasperCSVTest.action">Jasper Reports CSV example</a></li>
</ul>


<p class="title">
I18n Demos
</p>

<ul>
    <li><a href="i18n/jsp/index.html">JSP example of i18n</a></li>
    
    <li><a href="i18n/velocity/index.html">Velocity example of i18n</a></li>
</ul>


<p class="title">
Misc Demos
</p>

<ul>    
    <li><a href="redirect.action">Example redirect</a> using the
        <tt>ServletRedirectResult</tt> where the location is resolved via OGNL.
    </li>
    
    <li><a href="parseLocation.action">A FormTag example</a> using a location
        resolved via OGNL.
    </li>
    
    <li><a href="displaytag.vm">Example use of 3rd party JSP tags</a> in 
        Velocity.
    </li>
    
    <li><a href="include.vm">Example of the include tag in use</a></li>
    
    <li><a href="TabbedPaneTest.action">Tabbed pane example</a></li>
    
    <li><a href="select.action">Select tag example</a></li>
    
    <li><a href="indexedProperties.action">Indexed property example</a></li>
</ul>

<br><br>

</body>
</html>