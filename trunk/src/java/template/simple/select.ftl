<select name="$!webwork.htmlEncode($parameters.name)"
    <#if parameters.size?exists>             size="$!webwork.htmlEncode($parameters.size)"         </#if>
    <#if parameters.disabled?exists>         disabled="disabled"                                   </#if>
    <#if parameters.tabindex?exists>         tabindex="$!webwork.htmlEncode($parameters.tabindex)" </#if>
    <#if parameters.id?exists>               id="$!webwork.htmlEncode($parameters.id)"             </#if>
    <#if parameters.multiple?exists>         multiple="multiple"                                   </#if>
    <#if parameters.cssClass?exists>         class="$!webwork.htmlEncode($parameters.cssClass)"    </#if>
    <#if parameters.cssStyle?exists>         style="$!webwork.htmlEncode($parameters.cssStyle)"    </#if>
    <#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
>

<#if parameters.headerKey?exists && parameters.headerValue?exists>
    <option value="${parameters.headerKey}?html">${parameters.headerValue?html}</option>
</#if>

<#if parameters.emptyOption?exists>
    <option value=""></option>
</#if>

<#assign items = parameters.list/>
<#if items?exists>
    <#list items as item>
        ${stack.push(item)}

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

        <option value="${itemKey?html}"
        ${itemValue?html}
        </option>

        ${stack.pop()}
    </#list>
</#if>

</select>