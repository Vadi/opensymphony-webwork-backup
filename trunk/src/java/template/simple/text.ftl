<input type="text"
                                         name="${parameters.name?default("")?html}"
<#if parameters.size?exists>             size="${parameters.size?html}"            </#if>
<#if parameters.maxlength?exists>        maxlength="${parameters.maxlength?html}"  </#if>
<#if parameters.nameValue?exists>        value="${parameters.nameValue?html}"      </#if>
<#if parameters.disabled?default(false)>
                                         disabled="disabled"                                      </#if>
<#if parameters.readonly?exists>         readonly="readonly"                                      </#if>
<#if parameters.tabindex?exists>         tabindex="${parameters.tabindex?html}"    </#if>
<#if parameters.id?exists>               id="${parameters.id?html}"                </#if>
<#if parameters.cssClass?exists>         class="${parameters.cssClass?html}"       </#if>
<#if parameters.cssStyle?exists>         style="${parameters.cssStyle?html}"       </#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
/>