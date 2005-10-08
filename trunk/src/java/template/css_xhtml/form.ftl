<#if parameters.validate?exists>
<script src="${base}/webwork/validationClient.js"></script>
<script src="${base}/dwr/interface/validator.js"></script>
<script src="${base}/dwr/engine.js"></script>
<script src="${base}/webwork/template/css_xhtml/validation.js"></script>
</#if>
<#include "/${parameters.templateDir}/simple/form.ftl" />
