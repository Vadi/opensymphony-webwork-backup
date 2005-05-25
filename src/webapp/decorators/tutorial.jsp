<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="ww" uri="webwork" %>
<decorator:useHtmlPage id="p"/>
<%
    String path = request.getServletPath();
    int lessonIdx = path.indexOf("/lesson") + 7;
    int lesson = Integer.parseInt(path.substring(lessonIdx, path.indexOf('/', lessonIdx)));
    int lessonCount = Integer.parseInt(p.getProperty("meta.lessonCount"));
%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>Lesson <%=lesson%>: <decorator:title/></title>
        <style type="text/css" media="screen">
            @import url("<ww:url value="/shared/css/tutorial.css"/>");
        </style>
    </head>

    <body>

    <div id="wrap">
        <div id="header">
            <img src="<ww:url value="/shared/images/logo-small.png"/>"/>
        </div>

        <div id="content">
            <h1>Lesson <%=lesson%>: <decorator:title/></h1>
<decorator:body/>
        </div>

        <div id="menu">
            <div class="menuGroup">
                <h1>Lessons</h1>
                <ul>
                <%
                    for (int i = 1; i <= lessonCount; i++) {
                        if (i == lesson) {
                %>
                    <li>Lesson <%= i %></li>
                <%
                        } else {
                %>
                    <li><a href="../lesson<%= i %>">Lesson <%= i %></a></li>
                <%
                        }
                    }
                %>
                </ul>
            </div>

            <div class="menuGroup">
                <h1>Tutorials</h1>
                <ul>
                    <li>Getting started</li>
                    <li>Ajax</li>
                    <li>XSLT</li>
                </ul>
            </div>
        </div>

        <div id="footer">
            Copyright OpenSymphony, 1999-2005.
        </div>
    </div>

    </body>
</html>
