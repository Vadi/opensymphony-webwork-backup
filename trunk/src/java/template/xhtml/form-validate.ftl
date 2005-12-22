<#if parameters.validate?default(false) == true>
<script src="${base}/webwork/xhtml/validation.js"></script>
    <#if parameters.onsubmit?exists>
        ${tag.addParameter('onsubmit', "return validateForm_${parameters.id}();${parameters.onblur}")}
    <#else>
        ${tag.addParameter('onsubmit', "return validateForm_${parameters.id}();")}
    </#if>
</#if>
