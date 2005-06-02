<div id="${tag.id}">${tag.loadingText?default("")?html}</div>
<script language="JavaScript" type="text/javascript">
	if (dojo) dojo.hostenv.loadModule("webwork.widgets.BindDiv");
</script>
<div dojoType='binddiv'
	<#if tag.id?if_exists != "">id="${tag.id?html}"</#if>
	<#if tag.url?if_exists != "">href="${tag.url}"</#if>
	<#if tag.reloadingText?if_exists != "">loadingHtml="${tag.reloadingText?html}"</#if>
	<#if tag.errorText?if_exists != "">errorHtml="${tag.errorText?html}"</#if>
	<#if tag.showErrorTransportText>showTransportError='true'</#if>
	<#if tag.updateFreq?exists>refresh='${tag.updateFreq}'</#if>
>