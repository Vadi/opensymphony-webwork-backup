<#assign ww = JspTaglibs["/WEB-INF/webwork.tld"] />
<#function url value>
	<#local v><@ww.url value=value/></#local>
	<#return v/>
</#function>

<#macro code>
	<div class="source">
		<pre><@html_escape><#nested/></@></pre>
	</div>
</#macro>

<#macro example heading="">
	<#if heading?exists><h1>${heading}</h1></#if>

	<h2>Sample Source</h2>
	<div class="source">
		<pre><@html_escape><#nested/></@></pre>
	</div>

	<h2>Live Example</h2>
	<#nested/>

</#macro>
