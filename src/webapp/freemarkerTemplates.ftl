<#include "/ftl/examples.ftl"/>
<#assign ww=JspTaglibs["/WEB-INF/webwork.tld"]>
<@page title='Freemarker Demonstration of JSP tag libs'>

	<@title>Test Form</@>
	<@ww.form action='test.action' template='form.ftl'>
		<@ww.textfield name="'test'" label="'test'" template='text.ftl'/>
		<@ww.submit value="'Go'" template='submit.ftl'/>
	</@>

	<#if test?exists>
		<@title>Submitted Form Data</@>
		<@msg>test = ${test}</@>
	</#if>

	<#if req.getParameter('test')?exists>
		<@title>Direct access to request parameters</@>
		<@msg>test = ${req.getParameter('test')}</@>
	</#if>

	<#if testRequestAttrib?exists>
		<@title>Submitted Form Data stored in request attributes</@>
		<@msg>testRequestAttrib = ${testRequestAttrib}</@>
	</#if>
</@>