</form>

<script>
	function customOnsubmit() {
	
	<#--
	   Code that will auto-select optiontransferselect elements upon containing form submission
	-->
	<#if (parameters.optiontransferselectIds?if_exists?size > 0)>
		<#assign selectTagIds = parameters.optiontransferselectIds.keySet()/>
		<#list selectTagIds as tmpId>
			// auto-select optiontrasferselect (left side) with id ${tmpId}
			var selectObj = document.getElementById("${tmpId}");
			selectAllOptions(selectObj);
			selectUnselectMatchingOptions(selectObj, null, "unselect", false, "key");
			<#if parameters.optiontransferselectIds.get(tmpId)?exists>
				<#assign selectTagHeaderKey = parameters.optiontransferselectIds.get(tmpId).toString()/>
				selectUnselectMatchingOptions(selectObj, "${selectTagHeaderKey}", "unselect", false, "key");
			</#if>
		</#list>
	</#if>
	<#if (parameters.optiontransferselectDoubleIds?if_exists?size > 0) >		
		<#assign doubleSelectTagIds = parameters.optiontransferselectDoubleIds.keySet()/>
		<#list doubleSelectTagIds as tmpDoubleId>
			// auto-select optiontransferselect (right side) with id ${tmpDoubleId}
			var doubleSelectObj = document.getElementById("${tmpDoubleId}");
			selectAllOptions(doubleSelectObj);
			selectUnselectMatchingOptions(doubleSelectObj, null, "unselect", false, "key");
			<#if parameters.optiontransferselectDoubleIds.get(tmpDoubleId)?exists>
				<#assign doubleSelectTagHeaderKey = parameters.optiontransferselectDoubleIds.get(tmpDoubleId)/>
				selectUnselectMatchingOptions(doubleSelectObj, "${doubleSelectTagHeaderKey}", "unselect", false, "key");
			</#if>		
		</#list>
	</#if>
	
	
	
	<#--
	   Code that will auto select updownselect elements upon its containing form submission
	-->
	<#if (parameters.updownselectIds?if_exists?size > 0)>
		<#assign updownselectTagIds = parameters.updownselectIds.keySet() />
		<#list updownselectTagIds as tmpUpdownselectTagId>
			var updownselectObj = document.getElementById("${tmpUpdownselectTagId}");
			selectAllOptions(updownselectObj);
			selectUnselectMatchingOptions(updownselectObj, null, "unselect", false, "key");
			<#if parameters.updownselectIds.get(tmpUpdownselectTagId)?exists>
				<#assign updownselectHeaderKey = parameters.updownselectIds.get(tmpUpdownselectTagId) />
				selectUnselectMatchingOptions(updownselectObj, "${updownselectHeaderKey}", "unselect", false, "key");
			</#if>
		</#list>
	</#if>
	
	}
</script>

