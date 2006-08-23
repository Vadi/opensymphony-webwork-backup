<%@taglib prefix="ww" uri="/webwork" %>
<html>
<head>
<title>Showcase - UI Tag Example - Tree Example (Static)</title>
<ww:head theme="ajax" debug="true"  />
</head>
<body>

<!-- START SNIPPET: treeExampleStaticJsp -->

<script>
	function treeNodeSelected(nodeId) {
		dojo.io.bind({
			url: "<ww:url value='/tags/ui/ajax/staticTreeSelectAction.action'/>?nodeId="+nodeId,
			load: function(type, data, evt) {
				var divDisplay = dojo.byId("displayIt");
				divDisplay.innerHTML=data;
			},
			mimeType: "text/html"
		});
	};

	dojo.event.topic.subscribe("treeSelected", this, "treeNodeSelected");
</script>


<div style="float:left; margin-right: 50px;">
<ww:tree label="parent" id="parentId" theme="ajax"  
showRootGrid="true" showGrid="true" treeSelectedTopic="treeSelected">
	<ww:treenode theme="ajax" label="child1" id="child1Id">
		<ww:treenode theme="ajax" label="grandchild1" id="grandchild1Id"/>
		<ww:treenode theme="ajax" label="grandchild2" id="grandchild2Id"/>
		<ww:treenode theme="ajax" label="grandchild3" id="grandchild3Id"/>
	</ww:treenode>
	<ww:treenode theme="ajax" label="child2" id="child2Id"/>
	<ww:treenode theme="ajax" label="child3" id="child3Id"/>
	<ww:treenode theme="ajax" label="child4" id="child4Id"/>
	<ww:treenode theme="ajax" label="child5" id="child5Id">
		<ww:treenode theme="ajax" label="gChild1" id="gChild1Id"/>
		<ww:treenode theme="ajax" label="gChild2" id="gChild2Id"/>
	</ww:treenode>
</ww:tree>
</div>


<div id="displayIt">
Please click on any node on the tree.
</div>

<!-- END SNIPPET: treeExampleStaticJsp  -->

</body>
</html>