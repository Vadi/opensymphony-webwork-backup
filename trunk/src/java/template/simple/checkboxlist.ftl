<#assign items = parameters.list/>
<#if items?exists>
    <#assign itemCount = 0/>
    <#list items as item>
        <#assign itemCount = itemCount + 1/>
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
<input type="checkbox" name="${parameters.name?html}" value="${itemKey?html}" id="${parameters.name?html}-${itemCount}"<#rt/>
        <#if tag.contains(parameters.nameValue, itemKey)>
 checked="checked"<#rt/>
        </#if>
        <#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
/>
<label for="${parameters.name?html}-${itemCount}" class="checkboxLabel">${itemValue?html}</label>
        <#assign trash = stack.pop()/>
    </#list>
<#else>
  &nbsp;
</#if>