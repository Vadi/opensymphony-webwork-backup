<select<#rt/>
 name="${parameters.name?default("")?html}"<#rt/>
<#if parameters.size?exists>
 size="${parameters.size?html}"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.tabindex?exists>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.id?exists>
 id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.multiple?exists>
 multiple="multiple"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
>
<#if parameters.headerKey?exists && parameters.headerValue?exists>
    <option value="${parameters.headerKey?html}">${parameters.headerValue?html}</option>
</#if>
<#if parameters.emptyOption?exists>
    <option value=""></option>
</#if>
<#assign items = parameters.list/>
<#if items?exists>
    <#list items as item>
        <#assign trash = stack.push(item)/>
        <#if parameters.listKey?exists>
            <#assign itemKey = stack.findValue(parameters.listKey)/>
        <#else>
            <#assign itemKey = item/>
        </#if>
        <#if parameters.listValue?exists>
            <#assign itemValue = stack.findValue(parameters.listValue)/>
        <#else>
            <#assign itemValue = item/>
        </#if>
    <option value="${itemKey?html}"<#rt/>
        <#if tag.contains(parameters.nameValue, itemKey) == true>
 selected="selected"<#rt/>
        </#if>
    >${itemValue?html}</option><#lt/>
        <#assign trash = stack.pop()/>
    </#list>
</#if>
</select>