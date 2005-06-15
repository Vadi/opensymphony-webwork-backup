<#ftl strip_whitespace=true>
<#if parameters.onclick?exists>
    onclick="${parameters.onclick?html}"
</#if>
<#if parameters.ondblclick?exists>
    ondblclick="${parameters.ondblclick?html}"
</#if>
<#if parameters.onmousedown?exists>
    onmousedown="${parameters.onmousedown?html}"
</#if>
<#if parameters.onmouseup?exists>
    onmouseup="${parameters.onmouseup?html}"
</#if>
<#if parameters.onmouseover?exists>
    onmouseover="${parameters.onmouseover?html}"
</#if>
<#if parameters.onmousemove?exists>
    onmousemove="${parameters.onmousemove?html}"
</#if>
<#if parameters.onmouseout?exists>
    onmouseout="${parameters.onmouseout?html}"
</#if>
<#if parameters.onfocus?exists>
    onfocus="${parameters.onfocus?html}"
</#if>
<#if parameters.onblur?exists>
    onblur="${parameters.onblur?html}"
</#if>
<#if parameters.onkeypress?exists>
    onkeypress="${parameters.onkeypress?html}"
</#if>
<#if parameters.onkeydown?exists>
    onkeydown="${parameters.onkeydown?html}"
</#if>
<#if parameters.onkeyup?exists>
    onkeyup="${parameters.onkeyup?html}"
</#if>
<#if parameters.onselect?exists>
    onselect="${parameters.onselect?html}"
</#if>
<#if parameters.onchange?exists>
    onchange="${parameters.onchange?html}"
</#if>