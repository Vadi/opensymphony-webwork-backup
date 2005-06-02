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
<#assign dojoBase = url('/webwork/dojo')/>

<#macro dojoRuntime includes=[] isDebug=false>
	<script language="JavaScript" type="text/javascript">
		djConfig = { 
			baseRelativePath: "${dojoBase}/",
			isDebug: ${isDebug?string}
		};
	</script>

	<script src="${dojoBase}/__package__.js" language="JavaScript" type="text/javascript" ></script>

	<script language="JavaScript" type="text/javascript">
		<#list includes as include>
			dojo.hostenv.loadModule("${include}");
		</#list>
	</script>
	
</#macro>