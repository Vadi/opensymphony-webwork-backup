<%@ taglib prefix="ww" uri="/webwork" %>
<!--// START SNIPPET: common-include-->
<script language="JavaScript" type="text/javascript">
    // Dojo configuration
    djConfig = {
        baseRelativePath: "<ww:url includeParams="none" value="/webwork/dojo/"/>",
        isDebug: false,
        debugAtAllCosts: true // not needed, but allows the Venkman debugger to work with the includes
    };
</script>

<script language="JavaScript" type="text/javascript"
        src="<ww:url includeParams="none" value="/webwork/dojo/dojo.js" />"></script>
<script language="JavaScript" type="text/javascript"
        src="<ww:url includeParams="none" value="/webwork/CommonFunctions.js" />"></script>

<script language="JavaScript" type="text/javascript">
    dojo.require("dojo.io.BrowserIO");
    dojo.require("dojo.event.topic");
    dojo.require("webwork.widgets.Bind");
    dojo.require("webwork.widgets.BindDiv");
    dojo.require("webwork.widgets.BindButton");
    dojo.require("webwork.widgets.BindAnchor");
    dojo.hostenv.writeIncludes(); // not needed, but allows the Venkman debugger to work with the includes
</script>
<!--// END SNIPPET: common-include-->
