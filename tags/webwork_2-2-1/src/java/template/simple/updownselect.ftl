<#if !stack.findValue("#optiontransferselect_js_included")?exists>
	<script language="javascript" src="<@ww.url value="/webwork/optiontransferselect/optiontransferselect.js" encode='false' />"></script>
	<#assign temporaryVariable = stack.setValue("#optiontransferselect_js_included", "true") />
</#if>
<table>
<tr><td>
<#include "/${templateDir}/simple/select.ftl" />
</td></tr>
<tr><td>
<#if parameters.allowMoveUp?default(true)>
	<#assign defMoveUpLabel="${parameters.moveUpLabel?default('^')}" />
	<#if parameters.headerKey?exists>
		&nbsp;<input type="button" value="${defMoveUpLabel}" onclick="moveOptionUp(document.getElementById('${parameters.id}'), '${parameters.headerKey}');" />&nbsp;
	<#else>
		&nbsp;<input type="button" value="${defMoveUpLabel}" onclick="moveOptionUp(document.getElementById('${parameters.id}'));" />&nbsp;
	</#if>
</#if>
<#if parameters.allowMoveDown?default(true)>
	<#assign defMoveDownLabel="${parameters.moveDownLabel?default('v')}" />
	<#if parameters.headerKey?exists>
		&nbsp;<input type="button" value="${defMoveDownLabel}" onclick="moveOptionDown(document.getElementById('${parameters.id}'), '${parameters.headerKey}');" />&nbsp;
	<#else>
		&nbsp;<input type="button" value="${defMoveDownLabel}" onclick="moveOptionDown(document.getElementById('${parameters.id}'));" />&nbsp;
	</#if>
</#if>
<#if parameters.allowSelectAll?default(true)>
	<#assign defSelectAllLabel="${parameters.selectAllLabel?default('*')}" />
	<#if parameters.headerKey?exists>
		&nbsp;<input type="button" value="${defSelectAllLabel}" onclick="selectAllOptionsExceptSome(document.getElementById('${parameters.id}'), 'key', '${parameters.headerKey}');" />&nbsp;
	<#else>
		&nbsp;<input type="button" value="${defSelectAllLabel}" onclick="selectAllOptions(document.getElementById('${parameters.id}'));" />&nbsp;
	</#if>
</#if>
</td></tr>
</table>
