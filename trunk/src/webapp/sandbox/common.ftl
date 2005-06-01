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
