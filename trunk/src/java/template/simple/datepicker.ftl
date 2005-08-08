<#include "/${parameters.templateDir}/simple/text.ftl" />
<a href="javascript:cal1.popup('<@ww.url value="/webwork/tigracalendar"/>');"><img src="<@ww.url value="/webwork/tigracalendar/img/cal.gif"/>" width="16" height="16" border="0" alt="Click Here to Pick up the date"></a>
<script language="JavaScript" src="<@ww.url value="/webwork/tigracalendar/calendar2.js"/>"></script>
<script langauge="JavaScript">
var cal1 = new calendar2(document.forms['${parameters.form.id}'].elements['${parameters.name}']);
cal1.year_scroll = true;
cal1.time_comp = false;
</script>

