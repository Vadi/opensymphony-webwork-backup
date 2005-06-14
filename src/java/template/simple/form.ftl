<form 
<#if parameters.namespace?exists> namespace="${parameters.namespace?html}"  </#if>
<#if parameters.id?exists>        id="${parameters.id?html}"                </#if>
<#if parameters.name?exists>      name="${parameters.name?html}"            </#if>
<#if parameters.action?exists>    action="${parameters.action?html}"        </#if>
<#if parameters.target?exists>    target="${parameters.target?html}"        </#if>
<#if parameters.method?exists>    method="${parameters.method?html}"        </#if>
<#if parameters.enctype?exists>   enctype="${parameters.enctype?html}"      </#if>
<#if parameters.cssClass?exists>  class="${parameters.cssClass?html}"       </#if>
<#if parameters.cssStyle?exists>  style="${parameters.cssStyle?html}"       </#if>
<#if tag.useAjax?upper_case == "YES" || tag.useAjax?upper_case == "TRUE" > onSubmit="return false;" </#if>
>
