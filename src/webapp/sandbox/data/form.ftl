<#if request.getParameter("name")?if_exists != "">
	Hello ${request.getParameter("name")}
</#if>
