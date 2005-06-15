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
<input type="radio" name="${parameters.name?html}" id="${parameters.name?html}${itemKey?html}"<#rt/>
<#if tag.contains(parameters.nameValue, itemKey)>
 checked="checked"<#rt/>
</#if>
<#if itemKey?exists>
 value="${itemKey?html}"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.tabindex?exists>
 tabindex="${parameters.tabindex?html}"<#rt/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
/><#rt/>
<label for="${parameters.name?html}${itemKey?html}"><#rt/>
    ${itemValue}<#t/>
</label>
        <#assign trash = stack.pop()/>
    </#list>
</#if>
