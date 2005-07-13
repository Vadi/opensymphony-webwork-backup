<%@ taglib uri="webwork" prefix="ww" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Category</title>

  </head>
  <body id="page-home">
      <ww:div id="main" href="/catalog/remote/activeCategory.action" theme="ajax" listenTopics="categorySelected" loadingText="loading...">
        <ww:include page="category.jsp"/>
        <ww:param name="onLoad" value="Rounded('div.productDetails','tr bl','#ECF1F9','#CDFFAA','smooth border #88D84F');"/>
      </ww:div>
  </body>
</html>