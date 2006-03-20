<div <#rt/>
<#if parameters.align?exists>
    align="${parameters.align?html}"<#t/>
</#if>
<#if parameters.id?exists>
    id="wwctrl_${parameters.id}"<#rt/>
</#if>
><#t/>
<#include "/${parameters.templateDir}/simple/reset.ftl" />
</div><#t/>
