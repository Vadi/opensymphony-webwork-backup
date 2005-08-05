<div class="tab_contents_header" id="tab_contents_${parameters.id}" >
<div dojoType='binddiv'
    id="tab_contents_update_${parameters.id}"
    class="tab_contents"
	<#if parameters.href?if_exists != "">href="${parameters.href}"</#if>
	<#if parameters.loadingText?if_exists != "">loadingHtml="${parameters.loadingText?html}"</#if>
	<#if parameters.errorText?if_exists != "">errorHtml="${parameters.errorText?html}"</#if>
	<#if parameters.showErrorTransportText?exists>showTransportError='true'</#if>
	<#if parameters.delay?exists>delay='${parameters.delay}'</#if>
	<#if parameters.updateFreq?exists>refresh='${parameters.updateFreq}'</#if>
	<#if parameters.listenTopics?exists>listenTopics='${parameters.listenTopics}'</#if>
	<#if parameters.afterLoading?exists>onLoad='${parameters.afterLoading}'</#if>

<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.readonly?exists>
 readonly="readonly"<#rt/>
</#if>
<#if parameters.tabindex?exists>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.tabindex?exists>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
>
