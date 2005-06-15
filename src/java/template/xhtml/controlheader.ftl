<#--
	Only show message if errors are available.
	This will be done if ActionSupport is used.
-->
<#assign hasFieldErrors = fieldErrors?exists && fieldErrors[parameters.name]?exists/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<tr errorFor="${parameters.id}">
<#if parameters.labelposition?default("") == 'top'>
    <td align="left" valign="top" colspan="2">
<#else>
    <td align="center" valign="top" colspan="2">
</#if>
        <span class="errorMessage">${error?html}</span>
    </td>
</tr>
</#list>
</#if>
<#--
	if the label position is top,
	then give the label it's own row in the table
-->
<tr>
    <#compress><#if parameters.labelposition?default("") == 'top'><td align="left" valign="top" colspan="2"><#else><td align="right" valign="top"></#if><#if parameters.label?exists><label <#if parameters.id?exists>for="${parameters.id?html}"</#if> <#if hasFieldErrors>class="errorLabel"<#else>class="label"</#if>><#if parameters.required?default(false)><span class="required">*</span></#if>${parameters.label?html}:</label></#if></td></#compress>
<#-- add the extra row -->
<#if parameters.labelposition?default("") == 'top'>
</tr>
<tr>
</#if>
<#if parameters.form?exists && parameters.form.validate?default(false) == true>
	<#-- can't mutate the data model in freemarker -->
    <#if parameters.onblur?exists>
        ${tag.addParameter('onblur', "validate(this);${parameters.onblur}")}
    <#else>
        ${tag.addParameter('onblur', "validate(this);")}
    </#if>
</#if>
    <td>
