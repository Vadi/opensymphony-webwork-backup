<%@ taglib prefix="ww" uri="/webwork" %>
<!-- // START SNIPPET: common-include -->
<script language="JavaScript" type="text/javascript">
    // Dojo configuration
    djConfig = {
        baseRelativePath: "<ww:url includeParams="none" value="/webwork/dojo/"/>",
        isDebug: false
    };
</script>

<script language="JavaScript" type="text/javascript"
        src="<ww:url includeParams="none" value="/webwork/dojo/__package__.js" />"></script>
<script language="JavaScript" type="text/javascript"
        src="<ww:url includeParams="none" value="/webwork/CommonFunctions.js" />"></script>

<script language="JavaScript" type="text/javascript">
    dojo.hostenv.loadModule("dojo.io.BrowserIO");
    dojo.hostenv.loadModule("dojo.event.topic");
    dojo.hostenv.loadModule("webwork.widgets.Bind");
    dojo.hostenv.loadModule("webwork.widgets.BindDiv");
    dojo.hostenv.loadModule("webwork.widgets.BindButton");
    dojo.hostenv.loadModule("webwork.widgets.BindAnchor");
</script>
<!-- // END SNIPPET: common-include -->
