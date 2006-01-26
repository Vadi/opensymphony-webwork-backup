<#if !stack.findValue("#optiontransferselect_js_included")?exists>
	<script language="javascript" src="<@ww.url value="/webwork/optiontransferselect/optiontransferselect.js" encode='false' />"></script>
	<#assign temporaryVariable = stack.setValue("#optiontransferselect_js_included", "true") />
</#if>
<table border="0">
<tr>
<td>
<#if parameters.leftTitle?exists>
	<label for="leftTitle">${parameters.leftTitle}</label><br/>
</#if>
<#include "/${parameters.templateDir}/simple/select.ftl" /> </td>
<td valign="middle" align="center">
	<#if parameters.allowAddToLeft?default(true)>
		<#assign addToLeftLabel = parameters.addToLeftLabel?default("<-")?html/>
		<#if parameters.doubleHeaderKey?exists>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${addToLeftLabel}" onclick="moveSelectedOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '${parameters.doubleHeaderKey}', '')" /><br/><br/>
		<#else>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${addToLeftLabel}" onclick="moveSelectedOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '')" /><br/><br/>
		</#if>
	</#if>
	<#if parameters.allowAddToRight?default(true)>
		<#assign addToRightLabel=parameters.addToRightLabel?default("->")?html />
		<#if parameters.headerKey?exists>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${addToRightLabel}" onclick="moveSelectedOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '${parameters.headerKey}', '')" /><br/><br/>
		<#else>
			<input type="button"
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${addToRightLabel}" onclick="moveSelectedOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '')" /><br/><br/>
		</#if>
	</#if>
	<#if parameters.allowAddAllToLeft?default(true)>
		<#assign addAllToLeftLabel=parameters.addAllToLeftLabel?default("<<--")?html />
		<#if parameters.doubleHeaderKey?exists>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass}"
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle}"
			</#if>
			 value="${addAllToLeftLabel}" onclick="moveAllOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '${parameters.doubleHeaderKey}', '')" /><br/><br/>
		<#else>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${addAllToLeftLabel}" onclick="moveAllOptions(document.getElementById('${parameters.doubleId?html}'), document.getElementById('${parameters.id?html}'), false, '')" /><br/><br/>
		</#if>
	</#if>
	<#if parameters.allowAddAllToRight?default(true)>
		<#assign addAllToRightLabel=parameters.addAllToRightLabel?default("-->>")?html />
		<#if parameters.headerKey?exists>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${addAllToRightLabel}" onclick="moveAllOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '${parameters.headerKey}', '')" /><br/><br/>	
		<#else>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${addAllToRightLabel}" onclick="moveAllOptions(document.getElementById('${parameters.id?html}'), document.getElementById('${parameters.doubleId?html}'), false, '')" /><br/><br/>	
		</#if>
	</#if>
	<#if parameters.allowSelectAll?default(true)>
		<#assign selectAllLabel=parameters.selectAllLabel?default("<*>")?html />
		<#if parameters.headerKey?exists && parameters.doubleHeaderKey?exists>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${selectAllLabel}" onclick="selectAllOptionsExceptSome(document.getElementById('${parameters.id?html}'), 'key', '${parameters.headerKey}');selectAllOptionsExceptSome(document.getElementById('${parameters.doubleId?html}'), 'key', '${parameters.doubleHeaderKey}');" /><br/><br/>
		<#elseif parameters.headerKey?exists>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${selectAllLabel}" onclick="selectAllOptionsExceptSome(document.getElementById('${parameters.id?html}'), 'key', '${parameters.headerKey}');selectAllOptions(document.getElementById('${parameters.doubleId?html}'));" /><br/><br/>
		<#elseif parameters.doubleHeaderKey?exists>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${selectAllLabel}" onclick="selectAllOptions(document.getElementById('${parameters.id?html}'));selectAllOptionsExceptSome(document.getElementById('${parameters.doubleId?html}'), 'key', '${parameters.doubleHeaderKey}');" /><br/><br/>
		<#else>
			<input type="button" 
			<#if parameters.buttonCssClass?exists>
			 class="${parameters.buttonCssClass?html}" 
			</#if>
			<#if parameters.buttonCssStyle?exists>
			 style="${parameters.buttonCssStyle?html}"
			</#if>
			 value="${selectAllLabel}" onclick="selectAllOptions(document.getElementById('${parameters.id?html}'));selectAllOptions(document.getElementById('${parameters.doubleId?html}'));" /><br/><br/>
		</#if>
		
	</#if>
</td>
<td>
<#if parameters.rightTitle?exists>
	<label for="rightTitle">${parameters.rightTitle}</label><br/>
</#if>
<select 
	name="${parameters.doubleName?default("")?html}"
	<#if parameters.get("doubleSize")?exists>
	size="${parameters.get("size")?html}"		
	</#if>
	<#if parameters.doubleDisabled?default(false)>
	disabled="disabled"
	</#if>
	<#if parameters.doubleMultiple?exists>
	multiple="multiple"
	</#if>
	<#if parameters.doubleTabindex?exists>
	tabindex="${parameters.tabindex?html}"
	</#if>
	<#if parameters.doubleId?exists>
	id="${parameters.doubleId?html}"
	</#if>
	<#if parameters.doubleCssClass?exists>
	class="${parameters.cssClass?html}"
	</#if>
	<#if parameters.doubleCssStyle?exists>
	style="${parameters.cssStyle?html}"
	</#if>
    <#if parameters.doubleOnclick?exists>
    onclick="${parameters.doubleOnclick?html}"
    </#if>
    <#if parameters.doubleOndblclick?exists>
    ondblclick="${parameters.doubleOndblclick?html}"
    </#if>
    <#if parameters.doubleOnmousedown?exists>
    onmousedown="${parameters.doubleOnmousedown?html}"
    </#if>
    <#if parameters.doubleOnmouseup?exists>
    onmouseup="${parameters.doubleMnmouseup?html}"
    </#if>
    <#if parameters.doubleOnmousemove?exists>
    onmousemove="${parameters.doubleOnmousemove?html}"
    </#if>
    <#if parameters.doubleOnmouseout?exists>
    onmouseout="${parameters.doubleOnmouseout?html}"
    </#if>
    <#if parameters.doubleOnfocus?exists>
    onfocus="${parameters.doubleOnfocus?html}"
    </#if>
    <#if parameters.doubleOnblur?exists>
    onblur="${parameters.doubleOnblur?html}"
    </#if>
    <#if parameters.doubleOnkeypress?exists>
    onkeypress="${parameters.doubleOnkeypress?html}"
    </#if>
    <#if parameters.doubleOnKeydown?exists>
    onkeydown="${parameters.doubleOnkeydown?html}"
    </#if>
    <#if parameters.doubleOnkeyup?exists>
    onkeyup="${parameters.doubleOnkeyup?html}"
    </#if>
    <#if parameters.doubleOnselect?exists>
    onselect="${parameters.doubleOnselect?html}"
    </#if>
    <#if parameters.doubleOnchange?exists>
    onchange="${parameters.doubleOnchange?html}"
    </#if>
>
	<#if parameters.doubleHeaderKey?exists && parameters.doubleHeaderValue?exists>
    <option value="${parameters.doubleHeaderKey?html}">${parameters.doubleHeaderValue?html}</option>
	</#if>
	<#if parameters.doubleEmptyOption?default(false)>
    <option value=""></option>
	</#if>
	<@ww.iterator value="parameters.doubleList">
        <#if parameters.doubleListKey?exists>
            <#assign doubleItemKey = stack.findValue(parameters.doubleListKey) />
        <#else>
            <#assign doubleItemKey = stack.findValue('top') />
        </#if>
        <#assign doubleItemKeyStr = doubleItemKey.toString() />
        <#if parameters.listValue?exists>
            <#assign doubleItemValue = stack.findString(parameters.doubleListValue) />
        <#else>
            <#assign doubleItemValue = stack.findString('top') />
        </#if>
    	<option value="${doubleItemKeyStr?html}"<#rt/>
        <#if tag.contains(parameters.doubleNameValue, doubleItemKey)>
 		selected="selected"<#rt/>
        </#if>
    	>${doubleItemValue?html}</option><#lt/>
	</@ww.iterator>
</select> </td>
</tr>
</table>

