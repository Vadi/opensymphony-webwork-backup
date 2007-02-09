<#include "/${parameters.templateDir}/simple/select.ftl" />
<#assign startCount = 0/>
<#if parameters.headerKey?exists && parameters.headerValue?exists>
    <#assign startCount = startCount + 1/>
</#if>
<#if parameters.emptyOption?exists>
    <#assign startCount = startCount + 1/>
</#if>

<br/>
<select<#rt/>
 name="${parameters.doubleName?default("")?html}"<#rt/>
<#if parameters.doubleDisabled?default(false)>
 disabled="disabled"<#rt/>
</#if>
<#if parameters.doubleTabindex?exists>
 tabindex="${parameters.doubleTabindex?html}"<#rt/>
</#if>
<#if parameters.doubleId?exists>
 id="${parameters.doubleId?html}"<#rt/>
</#if>
<#if parameters.doubleCssClass?exists>
 class="${parameters.doubleCssClass?html}"<#rt/>
</#if>
<#if parameters.doubleCssStyle?exists>
 style="${parameters.doubleCssStyle?html}"<#rt/>
</#if>
<#if parameters.doubleTitle?exists>
 title="${parameters.doubleTitle?html}"<#rt/>
</#if>
<#if parameters.doubleMultiple?default(false)>
 multiple="multiple"<#rt/>
</#if>

<#if parameters.doubleOnblur?exists>
 onblur="${parameters.doubleOnblur?html}"<#rt/>
</#if>
<#if parameters.doubleOnchange?exists>
 onchange="${parameters.doubleOnchange?html}"<#rt/>
</#if>
<#if parameters.doubleOnclick?exists>
 onclick="${parameters.doubleOnclick?html}"<#rt/>
</#if>
<#if parameters.doubleOndblclick?exists>
 ondblclick="${parameters.doubleOndblclick?html}"<#rt/>
</#if>
<#if parameters.doubleOnfocus?exists>
 onfocus="${parameters.doubleOnfocus?html}"<#rt/>
</#if>
<#if parameters.doubleOnblur?exists>
 onblur="${parameters.doubleOnblur?html}"<#rt/>
</#if>
<#if parameters.doubleOnkeydown?exists>
 onkeydown="${parameters.doubleOnkeydown?html}"<#rt/>
</#if>
<#if parameters.doubleOnkeypress?exists>
 onkeypress="${parameters.doubleOnkeypress?html}"<#rt/>
</#if>
<#if parameters.doubleOnkeyup?exists>
 onkeyup="${parameters.doubleOnkeyup?html}"<#rt/>
</#if>
<#if parameters.doubleOnmousedown?exists>
 onkeyup="${parameters.doubleOnmousedown?html}"<#rt/>
</#if>
<#if parameters.doubleOnmousemove?exists>
 onmousemove="${parameters.doubleOnmousemove?html}"<#rt/>
</#if>
<#if parameters.doubleOnmouseout?exists>
onmouseout="${parameters.doubleOnmouseout?html}"<#rt/>
</#if>
<#if parameters.doubleOnmouseup?exists>
 onmouseup="${parameters.doubleOnmouseup?html}"<#rt/>
</#if>
<#if parameters.doubleOnselect?exists>
 onselect="${parameters.doubleOnselect?html}"<#rt/>
</#if>

>
</select>
<script type="text/javascript">
<#assign itemCount = startCount/>
    var ${parameters.id}Group = new Array(${parameters.listSize} + ${startCount});
    for (i = 0; i < (${parameters.listSize} + ${startCount}); i++)
    ${parameters.id}Group[i] = new Array();

<@ww.iterator value="parameters.list">
    <#if parameters.listKey?exists>
        <#assign itemKey = stack.findValue(parameters.listKey)/>
    <#else>
        <#assign itemKey = stack.findValue('top')/>
    </#if>
    <#if parameters.listValue?exists>
        <#assign itemValue = stack.findString(parameters.listValue)/>
    <#else>
        <#assign itemValue = stack.findString('top')/>
    </#if>
    <#assign doubleItemCount = 0/>
    <@ww.iterator value="${parameters.doubleList}">
        <#if parameters.doubleListKey?exists>
            <#assign doubleItemKey = stack.findValue(parameters.doubleListKey)/>
        <#else>
            <#assign doubleItemKey = stack.findValue('top')/>
        </#if>
        <#assign doubleItemKeyStr = doubleItemKey.toString() />
        <#if parameters.doubleListValue?exists>
            <#assign doubleItemValue = stack.findString(parameters.doubleListValue)/>
        <#else>
            <#assign doubleItemValue = stack.findString('top')/>
        </#if>
    ${parameters.id}Group[${itemCount}][${doubleItemCount}] = new Option("${doubleItemValue}", "${doubleItemKeyStr}");
        <#assign doubleItemCount = doubleItemCount + 1/>
    </@ww.iterator>
    <#assign itemCount = itemCount + 1/>
</@ww.iterator>

    var ${parameters.id}Temp = document.${parameters.formName}.${parameters.doubleName};
<#assign itemCount = startCount/>
<#assign redirectTo = 0/>
<@ww.iterator value="parameters.list">
    <#if parameters.listKey?exists>
        <#assign itemKey = stack.findValue(parameters.listKey)/>
    <#else>
        <#assign itemKey = stack.findValue('top')/>
    </#if>
    <#if tag.contains(parameters.nameValue, itemKey)>
        <#assign redirectTo = itemCount/>
    </#if>
    <#assign itemCount = itemCount + 1/>
</@ww.iterator>
    ${parameters.id}Redirect(${redirectTo});
    function ${parameters.id}Redirect(x) {
    	var selected = false;
        for (m = ${parameters.id}Temp.options.length - 1; m >= 0; m--) {
            ${parameters.id}Temp.options[m] = null;
        }

        for (i = 0; i < ${parameters.id}Group[x].length; i++) {
            ${parameters.id}Temp.options[i] = new Option(${parameters.id}Group[x][i].text, ${parameters.id}Group[x][i].value);
            <#if parameters.doubleNameValue?exists>
            	if (${parameters.id}Temp.options[i].value == '${parameters.doubleNameValue}') {
            		${parameters.id}Temp.options[i].selected = true;
            		selected = true;
            	}
            </#if>
        }

        if ((${parameters.id}Temp.options.length > 0) && (! selected)) {
           	${parameters.id}Temp.options[0].selected = true;
        }
    }
</script>
