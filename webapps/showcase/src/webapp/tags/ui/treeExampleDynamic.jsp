<%@taglib prefix="ww" uri="/webwork" %>

<html>
<head>
<title>Showcase - UI Tag Example - Tree Example (Dynamic)</title>
<ww:head theme="ajax" debug="true" />
</head>
<body>

<!-- START SNIPPET: treeExampleDynamicJsp -->

<script>
	function treeNodeSelected(nodeId) {
		dojo.io.bind({
			url: "<ww:url value='/tags/ui/ajax/dynamicTreeSelectAction.action' />?nodeId="+nodeId,
			load: function(type, data, evt) {
				var displayDiv = dojo.byId("displayId");
				displayDiv.innerHTML = data;
			},
			mimeType: "text/html"
		});
	};

	dojo.event.topic.subscribe("treeSelected", this, "treeNodeSelected");
</script>



<div style="float:left; margin-right: 50px;">
<ww:tree 
    id="myTree"
	theme="ajax"
	rootNode="%{treeRootNode}" 
	childCollectionProperty="children" 
	nodeIdProperty="id"
	nodeTitleProperty="name"
	treeSelectedTopic="treeSelected">
</ww:tree> 
</div>

<div id="displayId">
Please click on any of the tree nodes.
</div>

<!-- END SNIPPET: treeExampleDynamicJsp -->

</body>
</html>