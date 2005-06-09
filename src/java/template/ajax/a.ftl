<a dojoType='bindanchor'
	<#if tag.id?if_exists != "">id="${tag.id?html}"     </#if>
	<#if tag.href?if_exists != "">href="${tag.href}"    </#if>
	<#if tag.notifyTopics?exists>notifyTopics='${tag.notifyTopics}'</#if>
	<#if tag.errorText?if_exists != "">errorHtml="${tag.errorText?html}"</#if>
	<#if tag.showErrorTransportText>showTransportError='true'</#if>
    <#if parameters.name?exists>             name="${parameters.name?html}"         </#if>
    <#if parameters.cssClass?exists>         class="${parameters.cssClass?html}"    </#if>
    <#if parameters.cssStyle?exists>         style="${parameters.cssStyle?html}"    </#if>
    evalOnLoad='true'
>



