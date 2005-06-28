<input
    type="submit"
    dojoType='bindbutton'
<#if parameters.formId?exists>          formId='${parameters.formId}'               </#if>
<#if parameters.name?exists>            name="${parameters.name?html}"              </#if>
<#if parameters.nameValue?exists>       value="${parameters.nameValue?html}"        </#if>
<#if parameters.cssClass?exists>        class="${parameters.cssClass?html}"         </#if>
<#if parameters.cssStyle?exists>        style="${parameters.cssStyle?html}"         </#if>

<#if parameters.resultDivId?exists>     targetDiv='${parameters.resultDivId}'       </#if>
<#if parameters.onLoadJS?exists>        onLoad="${parameters.onLoadJS}"             </#if>
<#if parameters.notifyTopics?exists>    notifyTopics='${parameters.notifyTopics}'  </#if>
<#if parameters.listenTopics?exists>    listenTopics='${parameters.listenTopics}'  </#if>
/>
