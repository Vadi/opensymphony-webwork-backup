<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title>View Category</title>
  </head>

  <body>
  <ww:push value="category">

  <b><ww:property value="parentCategory.name"/> &gt; <ww:property value="name"/></b>
 (<a href="<ww:url value="editCategory.action" includeParams="none"><ww:param name="categoryId" value="id"/></ww:url>">edit</a>)
  <hr/>

  Child categories:
  <p/>

  <ww:iterator value="childCategories">
      <li><a href="<ww:url><ww:param name="categoryId" value="id"/></ww:url>"><ww:property value="name"/></a></li>
  </ww:iterator>

  <hr/>

  Items for auction:
  <p/>

  <ww:iterator value="categorizedItems">
      <li><a href="<ww:url value="viewItem.action"><ww:param name="id" value="item.id"/></ww:url>"><ww:property value="item.name"/></a></li>
  </ww:iterator>

  <hr/>

  <a href="<ww:url value="addItem!default.action"><ww:param name="categoryIds" value="id"/></ww:url>">Add new item</a>

  </ww:push>
  </body>
</html>