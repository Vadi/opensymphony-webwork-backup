<#include "/${parameters.templateDir}/simple/text.ftl" />
<br/>
<#if parameters.list?exists>
<select onChange="this.form.elements['${parameters.name?html}'].value=this.options[this.selectedIndex].value"<#rt/>
    <#if parameters.disabled?exists && parameters.disabled>
 disabled="disabled"<#rt/>
    </#if>
>
    <#list parameters.list as param>
    <option value="${param?html}"<#rt/>
        <#if parameters.name = param>
 selected="selected"<#rt/>
        </#if>
    ><#t/>
            ${param?html}<#t/>
    </option><#lt/>
    </#list>
</select>
</#if>