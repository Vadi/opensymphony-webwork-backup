 <script language="JavaScript" type="text/javascript">
        <!--
        dojo.require("dojo.lang.*");
        dojo.require("dojo.widget.*");
        dojo.require("dojo.widget.Tree");
        // dojo.hostenv.writeIncludes();
        -->
 </script>
<div dojoType="Tree"   
	<#if parameters.blankIconSrc?exists>
	gridIconSrcT="<@ww.url value='${parameters.blankIconSrc}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcL?exists>
	gridIconSrcL="<@ww.url value='${parameters.gridIconSrcL}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcV?exists>
	gridIconSrcV="<@ww.url value='${parameters.gridIconSrcV}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcP?exists>
	gridIconSrcP="<@ww.url value='${parameters.gridIconSrcP}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcC?exists>
	gridIconSrcC="<@ww.url value='${parameters.gridIconSrcC}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcX?exists>
	gridIconSrcX="<@ww.url value='${parameters.gridIconSrcX}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcY?exists>
	gridIconSrcY="<@ww.url value='${parameters.gridIconSrcY}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.gridIconSrcZ?exists>
	gridIconSrcZ="<@ww.url value='${parameters.gridIconSrcZ}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.expandIconSrcPlus?exists>
	expandIconSrcPlus="<@ww.url value='${parameters.expandIconSrcPlus}' includeParams='none'/>"
	</#if>
	<#if parameters.expandIconSrcMinus?exists>
	expandIconSrcMinus="<@ww.url value='${parameters.expandIconSrcMinus?html}'includeParams='none' />"
	</#if>
	<#if parameters.iconWidth?exists>
	iconWidth="<@ww.url value='${parameters.iconWidth?html}' encode="false" />"
	</#if>
	<#if parameters.iconHeight?exists>
	iconHeight="<@ww.url value='${parameters.iconHeight?html}' encode="false" />"
	</#if>
	<#if parameters.toggleDuration?exists>
	toggleDuration=${parameters.toggleDuration?c}
	</#if>
	<#if parameters.templateCssPath?exists>
	templateCssPath="<@ww.url value='${parameters.templateCssPath}' encode="false" includeParams='none'/>"
	</#if>
	<#if parameters.showGrid?exists>
	showGrid="${parameters.showGrid?default(true)?string}"
	</#if>
	<#if parameters.showRootGrid?exists>
	showRootGrid="${parameters.showRootGrid?default(true)?string}"
	</#if>
    <#if parameters.id?exists>
    id="${parameters.id?html}"
    </#if>
    <#if parameters.treeSelectedTopic?exists>
    publishSelectionTopic="${parameters.treeSelectedTopic?html}"
    </#if>
    <#if parameters.treeExpandedTopic?exists>
    publishExpandedTopic="${parameters.treeExpandedTopic?html}"
    </#if>
    <#if parameters.treeCollapsedTopic?exists>
    publishCollapsedTopic="${parameters.treeCollapsedTopic?html}"
    </#if>
    <#if parameters.toggle?exists>
    toggle="${parameters.toggle?html}"
    </#if>
    >
    <#if parameters.label?exists>
    <div dojoType="TreeNode" title="${parameters.label?html}"
    <#if parameters.nodeIdProperty?exists>
    id="${stack.findValue(parameters.nodeIdProperty)}"
    <#else>
    id="${parameters.id}_root" 
    </#if>
    >
    <#elseif parameters.rootNode?exists>
    ${stack.push(parameters.rootNode)}
    <#include "/${parameters.templateDir}/ajax/treenode-include.ftl" />
    <#assign oldNode = stack.pop()/> <#-- pop the node off of the stack, but don't show it -->
    </#if>
