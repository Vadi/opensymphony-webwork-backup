<#--
START SNIPPET: supported-validators
Only the following validators are supported:
* required validator
* requiredstring validator
* email validator
* url validator
* int validator
END SNIPPET: supported-validators
-->
<#if parameters.validate?exists>
<script>
    function validateForm_${parameters.id}() {
        form = document.getElementById("${parameters.id}");
        clearErrorMessages(form);
        clearErrorLabels(form);

        var errors = false;
    <#list parameters.tagNames as tagName>
        <#list tag.getValidators("${tagName}") as validator>
        // field name: ${validator.fieldName}
        // validator name: ${validator.validatorType}
        if (form.elements['${validator.fieldName}']) {
            field = form.elements['${validator.fieldName}'];
            var error = "${validator.getMessage(action)?js_string}";
            <#if validator.validatorType = "required">
            if (field.value == "") {
                addError(field, error);
                errors = true;
            }
            <#elseif validator.validatorType = "requiredstring">
            if (field.value != null && (field.value == "" || field.value.match("\W+"))) {
                addError(field, error);
                errors = true;
            }
            <#elseif validator.validatorType = "email">
            if (field.value != null && field.value.length > 0 && field.value.match(/^\S+@\S+\.(com|net|org|info|edu|mil|gov|biz|ws|us|tv|cc|aero|arpa|coop|int|jobs|museum|name|pro|travel|nato|.{2,2})$/gi) == null) {
                addError(field, error);
                errors = true;
            }
            <#elseif validator.validatorType = "url">
            if (field.value != null && field.value.length > 0 && field.value.match(/^((file:\/\/\S+)|(ftp|http|https):\/\/\S+\.(com|net|org|info|edu|mil|gov|biz|ws|us|tv|cc|aero|arpa|coop|int|jobs|museum|name|pro|travel|nato|.{2,2}))$/ig) == null) {
                addError(field, error);
                errors = true;
            }
            <#elseif validator.validatorType = "int">
            if (field.value != null && (parseInt(field.value) < ${validator.min} || parseInt(field.value) > ${validator.max})) {
                addError(field, error);
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