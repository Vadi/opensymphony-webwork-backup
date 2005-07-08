<a dojoType='bindanchor'
	<#if parameters.id?if_exists != "">id="${parameters.id?html}"     </#if>
	<#if parameters.href?if_exists != "">href="${parameters.href}"    </#if>
	<#if parameters.notifyTopics?exists>notifyTopics='${parameters.notifyTopics}'</#if>
	<#if parameters.errorText?if_exists != "">errorHtml="${parameters.errorText?html}"</#if>
	<#if parameters.showErrorTransportText>showTransportError='true'</#if>
    <#if parameters.name?exists>             name="${parameters.name?html}"         </#if>
    <#if parameters.cssClass?exists>         class="${parameters.cssClass?html}"    </#if>
    <#if parameters.cssStyle?exists>         style="${parameters.cssStyle?html}"    </#if>
    evalResult='true'
>



