<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="webwork" prefix="ww"%>
<div class="dtree">
    <p><a href="javascript: tree.openAll();"><ww:text name="text.openAll"/></a> | <a href="javascript: tree.closeAll();"><ww:text name="text.closeAll"/></a></p>

	<script type="text/javascript">
    <!--
    tree = new dTree('tree');
    tree.add(0,-1,'<ww:text name="text.categories"/> ');
    <ww:i18n name="org.hibernate.auction.localization.LocalizedMessages">
    <ww:iterator value="categories">
    <ww:if test="top != null">
    tree.add(<ww:property value="id"/>,<ww:property value="(parentCategory == null)?0:parentCategory.id"/>,'<ww:text name="%{name}"/>','viewCategory.action?category=<ww:property value="id"/>','<ww:property value="name"/>','_top','/img/folder.gif','/img/folderopen.gif');
    </ww:if>
    </ww:iterator>
    </ww:i18n>
    document.write(tree);
    //-->
	</script>
</div>