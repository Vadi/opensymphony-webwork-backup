<#assign hasFieldErrors = fieldErrors?exists && fieldErrors[parameters.name]?exists/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<tr<#rt/>
<#if parameters.id?exists>
 errorFor="${parameters.id}"<#rt/>
</#if>
>
    <td align="left" valign="top" colspan="2"><#rt/>
        <span class="errorMessage">${error?html}</span><#t/>
    </td><#lt/>
</tr>
</#list>
</#if>
<#if parameters.labelposition?default("") == 'top'>
<tr>
    <td colspan="2">
<#if parameters.label?exists> <label<#t/>
<#if parameters.id?exists>
 for="${parameters.id?html}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="checkboxErrorLabel"<#rt/>
<#else>
 class="checkboxLabel"<#rt/>
</#if>
>${parameters.label?html}</label><#rt/>
</#if>
    </td>
</tr>
</#if>
<tr>
	<td valign="top" align="right">
		<#if parameters.required?default(false)>
        	<span class="required">*</span><#t/>
		</#if>
		<#if parameters.tooltip?exists>
      		<img src='<@ww.url value="/webwork/tooltip/tooltip.gif" />' alt="${parameters.tooltip}" title="${parameters.tooltip}" onmouseover="return escape('${parameters.tooltip?js_string}');" />
		</#if>
<#if parameters.labelposition?default("") == 'left'>
<#if parameters.label?exists> <label<#t/>
<#if parameters.id?exists>
 for="${parameters.id?html}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="checkboxErrorLabel"<#rt/>
<#else>
 class="checkboxLabel"<#rt/>
</#if>
>${parameters.label?html}</label><#rt/>
</#if>
</#if>
    </td>
    <td valign="top" align="left">

                	<#include "/${parameters.templateDir}/simple/checkbox.ftl" />
<#if parameters.labelposition?default("") != 'top' && parameters.labelposition?default("") != 'left'>
<#if parameters.label?exists> <label<#t/>
<#if parameters.id?exists>
 for="${parameters.id?html}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="checkboxErrorLabel"<#rt/>
<#else>
 class="checkboxLabel"<#rt/>
</#if>
>${parameters.label?html}</label><#rt/>
</#if>
</#if>
 <#include "/${parameters.templateDir}/xhtml/controlfooter.ftl" /><#nt/>