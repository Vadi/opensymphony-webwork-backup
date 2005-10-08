<#assign hasFieldErrors = fieldErrors?exists && fieldErrors[parameters.name]?exists/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<p<#rt/>
<#if parameters.id?exists>
 errorFor="${parameters.id}"<#rt/>
</#if>
>
        <span class="errorMessage">${error?html}</span><#t/>
</p>
</#list>
</#if>

<p>
<#include "/${parameters.templateDir}/simple/checkbox.ftl" />
<label<#rt/>
<#if parameters.id?exists>
 for="${parameters.id?html}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="checkboxErrorLabel"<#rt/>
<#else>
 class="checkboxLabel"<#rt/>
</#if>
>${parameters.label?html}</label><#rt/>
<#include "/${parameters.templateDir}/xhtml/controlfooter.ftl" /><#nt/>