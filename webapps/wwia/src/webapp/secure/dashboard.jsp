<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title></title>
  </head>

  <body>
    <a href="newCategory.action">Create a category</a>

    <hr/>

    <ww:iterator value="categories">
        <li><a href="<ww:url value="viewCategory.action"><ww:param name="categoryId" value="id"/></ww:url>"><ww:property value="name"/></a></li>
    </ww:iterator>
  </body>
</html>