<#include "tigris-macros.ftl">
<@startPage pageTitle="Actions in namespace"/>
<h3>Actions in <#if namespace == ""> default namespace <#else> ${namespace} </#if></h3>
<table>
	<tr>
		<td>
			<ul>
			<#list actionNames as name>
				<li><a href="showConfig.action?namespace=${namespace}&actionName=${name}">${name}</a></li>
			</#list>
			</ul>
		</td>
		<td>
			<!-- Placeholder for namespace graph -->
		</td>
	</tr>
</table>
<@endPage />