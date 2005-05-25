<%@ taglib uri="webwork" prefix="ww" %>

    <script language="JavaScript" type="text/javascript">
       	// Dojo configuration
       	djConfig = {
       		baseRelativePath: "<ww:url value="%{'webwork/dojo/'}"/>",
       		parseWidgets: false,
       		isDebug: true
       	};
       </script>

       <script language="JavaScript" type="text/javascript" src="<ww:url value="%{'/webwork/dojo/__package__.js'}" />"></script>
       <script language="JavaScript" type="text/javascript" src="<ww:url value="%{'/webwork/AjaxComponents.js'}" />"></script>

       <script language="JavaScript" type="text/javascript">
            dojo.hostenv.loadModule("dojo.io.BrowserIO");
       	    dojo.hostenv.loadModule("dojo.event.topic");
       </script>
