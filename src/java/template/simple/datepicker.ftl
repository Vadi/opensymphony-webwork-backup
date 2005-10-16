<#include "/${parameters.templateDir}/simple/text.ftl" />
<a href="javascript:cal_${parameters.id}.popup('<@ww.url value="/webwork/tigracalendar"/>');"><img src="<@ww.url value="/webwork/tigracalendar/img/cal.gif"/>" width="16" height="16" border="0" alt="Click Here to Pick up the date"></a>
<#if !stack.findValue("#datepicker_included")?exists>
<#assign trash = stack.setValue("#datepicker_included", true)/>
<script language="JavaScript" src="<@ww.url value="/webwork/tigracalendar/calendar2.js"/>"></script>
</#if>
<script langauge="JavaScript">
var cal_${parameters.id} = new calendar2(document.forms['${parameters.form.id}'].elements['${parameters.name}']);
cal_${parameters.id}.year_scroll = true;
cal_${parameters.id}.time_comp = false;
</script>

