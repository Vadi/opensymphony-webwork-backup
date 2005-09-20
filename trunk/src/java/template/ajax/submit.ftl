<button type="submit" dojoType="BindButton"<#rt/>
<#if parameters.formId?exists>
 formId="${parameters.formId}"<#rt/>
</#if>
<#if parameters.name?exists>
 name="${parameters.name?html}"<#rt/>
</#if>
<#if parameters.nameValue?exists>
 value="${parameters.nameValue?html}"<#rt/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.resultDivId?exists>
 targetDiv="${parameters.resultDivId}"<#rt/>
</#if>
<#if parameters.onLoadJS?exists>
 onLoad="${parameters.onLoadJS}"<#rt/>
</#if>
<#if parameters.notifyTopics?exists>
 notifyTopics="${parameters.notifyTopics}"<#rt/>
</#if>
<#if parameters.listenTopics?exists>
 listenTopics="${parameters.listenTopics}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl"/>
/>
