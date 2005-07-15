<div dojoType='binddiv'
	<#if parameters.id?if_exists != "">id="${parameters.id?html}"</#if>
	<#if parameters.href?if_exists != "">href="${parameters.href}"</#if>
	<#if parameters.loadingText?if_exists != "">loadingHtml="${parameters.loadingText?html}"</#if>
	<#if parameters.errorText?if_exists != "">errorHtml="${parameters.errorText?html}"</#if>
	<#if parameters.showErrorTransportText?exists>showTransportError='true'</#if>
	<#if parameters.delay?exists>delay='${parameters.delay}'</#if>
	<#if parameters.updateFreq?exists>refresh='${parameters.updateFreq}'</#if>
	<#if parameters.listenTopics?exists>listenTopics='${parameters.listenTopics}'</#if>
	<#list parameters?keys as key>
	    <#if parameters[key]?exists>${key}="${parameters[key]?html}"</#if>
	</#list>
>