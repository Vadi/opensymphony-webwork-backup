</table>
<#include "/${parameters.templateDir}/simple/form-close.ftl" />
<#if parameters.validate?exists>
<script>
    function validateForm_${parameters.id}() {
        form = document.getElementById("${parameters.id}");
        clearErrorRows(form.childNodes[1]);
        clearErrorLabels(form);

        var errors = false;
    <#list parameters.tagNames as tagName>
        <#list tag.getValidators("${tagName}") as validator>
        // field name: ${validator.fieldName}
        // validator name: ${validator.validatorType}
        if (form.elements['${validator.fieldName}']) {
            field = form.elements['${validator.fieldName}'];

            <#if validator.validatorType = "requiredstring">
            if (field.value == null || field.value == "" || field.value.match("\W+")) {
                addError(field, "${validator.defaultMessage}");
                errors = true;
            }
            </#if>
        }
        </#list>
    </#list>

        return !errors;
    }
</script>
</#if>