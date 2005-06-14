<input
    type="submit"
    dojoType='bindbutton'
<#if tag.formId?exists>                 formId='${tag.formId}'                      </#if>
<#if tag.formResultId?exists>           targetDiv='${tag.formResultId}'             </#if>
<#if tag.onLoadJS?exists>               onLoad="${tag.onLoadJS}"                    </#if>
<#if tag.notifyTopics?exists>           notifyTopics='${tag.notifyTopics}'>         </#if>
<#if tag.listenTopics?exists>           listenTopics='${tag.listenTopics}'>         </#if>
<#if parameters.name?exists>            name="${parameters.name?html}"              </#if>
<#if parameters.nameValue?exists>       value="${parameters.nameValue?html}"        </#if>
<#if parameters.cssClass?exists>        class="${parameters.cssClass?html}"         </#if>
<#if parameters.cssStyle?exists>        style="${parameters.cssStyle?html}"         </#if>
/>
