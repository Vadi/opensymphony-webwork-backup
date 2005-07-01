<div dojoType='binddiv'
	<#if tag.id?if_exists != "">id="${tag.id?html}"</#if>
	<#if tag.href?if_exists != "">href="${tag.href}"</#if>
	<#if tag.loadingText?if_exists != "">loadingHtml="${tag.loadingText?html}"</#if>
	<#if tag.errorText?if_exists != "">errorHtml="${tag.errorText?html}"</#if>
	<#if tag.showErrorTransportText>showTransportError='true'</#if>
	<#if tag.delay?exists>delay='${tag.delay}'</#if>
	<#if tag.updateFreq?exists>refresh='${tag.updateFreq}'</#if>
	<#if tag.listenTopics?exists>listenTopics='${tag.listenTopics}'</#if>
	<#list parameters?keys as key>
	    <#if parameters[key]?exists>${key}="${parameters[key]?html}"</#if>
	</#list>
>