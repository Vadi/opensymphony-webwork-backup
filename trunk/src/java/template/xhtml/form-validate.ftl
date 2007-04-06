<#if parameters.validate?default(false) == true>
	<script type="text/javascript" src="${base}/webwork/xhtml/validation.js"></script>
	<#if parameters.onsubmit?exists>
		${tag.addParameter('onsubmit', "${parameters.onsubmit}; customOnsubmit_${parameters.id}(); return validateForm_${parameters.id}();")}
	<#else>
		${tag.addParameter('onsubmit', "customOnsubmit_${parameters.id}(); return validateForm_${parameters.id}();")}
	</#if>
</#if>
