<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title>View Category</title>
  </head>

  <body>
  <ww:push value="category">
  <ww:i18n name="org.hibernate.auction.localization.LocalizedMessages">
  <b><ww:text name="%{parentCategory.name}"/> &gt; <ww:text name="%{name}"/></b>
  </ww:i18n>

  <hr/>

  <ww:text name="text.created"/>: <ww:text name="format.date"><ww:param value="created"/></ww:text>
  <p/>

  <ww:text name="text.childCategories"/> :
  <p/>

  <ww:i18n name="org.hibernate.auction.localization.LocalizedMessages">
  <ww:iterator value="childCategories">
      <li><a href="<ww:url><ww:param name="id" value="id"/></ww:url>"><ww:text name="%{name}"/></a></li>
  </ww:iterator>
  </ww:i18n>

  <hr/>

  <ww:text name="text.itemsAuction"/>:
  <p/>

  <ww:iterator value="categorizedItems">
      <li><a href="<ww:url value="viewItem.action"><ww:param name="id" value="item.id"/></ww:url>"><ww:property value="item.name"/></a></li>
  </ww:iterator>

  <hr/>

  <a href="<ww:url value="addItem!default.action"><ww:param name="categoryIds" value="id"/></ww:url>"><ww:text name="text.addItem"/></a>

  </ww:push>
  </body>
</html>