<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>Ajax Examples</title>
            <jsp:include page="commonInclude.jsp" />
            <script language="JavaScript" type="text/javascript" src="/webwork-example/webwork/AjaxComponents.js"></script>
    </head>
  <body>

        Update once
        <ww:remotediv id="once" url="'/AjaxTest.action'" />

        <br/><br/>
        Update every 5 secs
        <ww:remotediv id="fivesec" url="'/AjaxTest.action'" updateFreq="5" />

        <br/><br/>
        Update every 2 secs
        <ww:remotediv id="twosec" url="'/AjaxTest.action'" updateFreq="2" />

        <br/><br/>
        Update every 10 secs with reloading message
        <ww:remotediv id="tensec" url="'/AjaxTest.action'" updateFreq="10"
            loadingText="'loading now'" reloadingText="'reloading page'" />

        <br/><br/>
        No such URL
        <ww:remotediv id="nourl" url="'/AjaxNoUrl.jsp'" errorText="'Could not contact server'" />

        <br/><br/>
        No such URL
        <ww:remotediv id="nourlto" url="'/AjaxNoUrl.jsp'" showErrorTransportText="'true'" />

  </body>
</html>