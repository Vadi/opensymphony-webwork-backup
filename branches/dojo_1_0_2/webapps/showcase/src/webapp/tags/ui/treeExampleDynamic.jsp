<%@taglib prefix="ww" uri="/webwork" %>

<html>
<head>
<title>Showcase - UI Tag Example - Tree Example (Dynamic)</title>
<ww:head theme="ajax" debug="true" />
</head>
<body>

<!-- START SNIPPET: treeExampleDynamicJsp -->

<script>
	dojo.addOnLoad(function() {
		var obj = {
			treeNodeSelected: function(nodeId) {
				dojo.io.bind({
					url: "<ww:url value='/tags/ui/ajax/dynamicTreeSelectAction.action' />?nodeId="+nodeId,
					load: function(type, data, evt) {
						var displayDiv = dojo.byId("displayId");
						displayDiv.innerHTML = data;
					},
					mimeType: "text/html"
				});
			}, 
			treeNodeExpanded: function(nodeId) {
				alert('node '+nodeId+' expanded');
			},
			treeNodeCollapsed: function(nodeId) {
				alert('node '+nodeId+' collapsed');
			}
		};
		dojo.event.topic.subscribe("treeSelected", obj, "treeNodeSelected");
		dojo.event.topic.subscribe("treeNodeExpanded", obj, "treeNodeExpanded");
		dojo.event.topic.subscribe("treeNodeCollapsed", obj, "treeNodeCollapsed");
	});
</script>



<div style="float:left; margin-right: 50px;">
<ww:tree 
    id="myTree"
	theme="ajax"
	rootNode="%{treeRootNode}" 
	childCollectionProperty="children" 
	nodeIdProperty="id"
	nodeTitleProperty="name"
	treeSelectedTopic="treeSelected"
	treeExpandedTopic="treeNodeExpanded"
	treeCollapsedTopic="treeNodeCollapsed"
	>
</ww:tree> 
</div>

<div id="displayId">
Please click on any of the tree nodes.
</div>

<!-- END SNIPPET: treeExampleDynamicJsp -->

</body>
</html>