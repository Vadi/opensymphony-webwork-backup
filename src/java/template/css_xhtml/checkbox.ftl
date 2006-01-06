<#include "/${parameters.templateDir}/css_xhtml/controlheader.ftl" />
<div<#rt/><#if parameters.id?exists>id="wwctrl_${parameters.id}"<#rt/></#if>>
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
<#include "/${parameters.templateDir}/css_xhtml/controlfooter.ftl" /><#nt/>