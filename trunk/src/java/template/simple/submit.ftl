<input type="submit"
<#if parameters.name?exists>       name="${parameters.name?html}"           </#if>
<#if parameters.nameValue?exists>  value="${parameters.nameValue?html}"     </#if>
<#if parameters.cssClass?exists>   class="${parameters.cssClass?html}"      </#if>
<#if parameters.cssStyle?exists>   style="${parameters.cssStyle?html}"      </#if>
/>