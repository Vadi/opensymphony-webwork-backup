<#--
<tr>
    <td colspan="2"><div <#rt/>
-->
<#include "/${parameters.templateDir}/${themeProperties.parent}/controlheader.ftl" />
<#--
	If we don't have the followings :-
	  - parameters.name
	  - parameters.id
	  - parameters.form.id
	 there's insufficient info for us to do a bind that knows which method in the action to execute.
-->
<#if parameters.name?exists && parameters.id?exists && parameters.form.id?exists>
<script type="text/javascript">
	function doBeforeBind_${parameters.id}() {
		// remove the hidden input field if one exists, cause we only can click on one button and that button's name should 
		// be the name of the hidden input
	    if (document.getElementById('hidden_action')) { 
			document.getElementById('${parameters.form.id}').removeChild(document.getElementById('hidden_action'));
		}
		var inputElement = document.createElement("input");
		inputElement.setAttribute("id", "hidden_action");
		inputElement.setAttribute("type", "hidden");
		inputElement.setAttribute("name", "${parameters.name?html}");
		inputElement.setAttribute("value", "");
		document.getElementById('${parameters.form.id}').appendChild(inputElement);
	};
    dojo.addOnLoad(function() {
    	dojo.event.connect(
    		dojo.widget.byId('${parameters.id}'),
    		"beforeBind",
    		"doBeforeBind_${parameters.id}"
    	);
    });
</script>
</#if>
<button type="submit" dojoType="webwork:BindButton"<#rt/>
<#if parameters.id?exists>
id="${parameters.id}"<#rt/>
</#if>
<#if parameters.form?exists && parameters.form.id?exists>
 formId="${parameters.form.id}"<#rt/>
</#if>
<#if parameters.name?exists>
 name="${parameters.name?html}"<#rt/>
</#if>
<#if parameters.nameValue?exists>
 value="<@ww.property value="parameters.nameValue"/>"<#rt/>
</#if>
<#if parameters.cssClass?exists>
 class="${parameters.cssClass?html}"<#rt/>
</#if>
<#if parameters.cssStyle?exists>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
<#if parameters.resultDivId?exists>
 targetDiv="${parameters.resultDivId}"<#rt/>
</#if>
<#if parameters.onLoadJS?exists>
 onLoad="${parameters.onLoadJS}"<#rt/>
</#if>
<#if parameters.preInvokeJS?exists>
 preInvokeJS="${parameters.preInvokeJS}"<#rt/>
</#if>
<#if parameters.notifyTopics?exists>
 notifyTopics="${parameters.notifyTopics}"<#rt/>
</#if>
<#if parameters.listenTopics?exists>
 listenTopics="${parameters.listenTopics}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl"/>
><#if parameters.nameValue?exists><@ww.property value="parameters.nameValue"/><#rt/></#if></button>
<#--include "/${parameters.templateDir}/xhtml/controlfooter.ftl" /-->
<#include "/${parameters.templateDir}/${themeProperties.parent}/controlfooter.ftl" />

