<#assign benchmark=JspTaglibs["/WEB-INF/taglibs-benchmark.tld"] />
<#assign webwork=JspTaglibs["/WEB-INF/webwork.tld"] />

<#assign count = 100/>

<h2>generate output below ${count} times</h2>
<@benchmark.duration >
	<@repeat macro=output count=count/>
</@benchmark.duration>

<hr>
<@output/>

<#macro output count=1>
	<ul>
		<#list countries as country>
			<li>${country[0]}</li>
		</#list>
	</ul>
</#macro>

<#macro repeat macro count>
	<#list 1..count as c>
		<@macro/>
	</#list>
</#macro>