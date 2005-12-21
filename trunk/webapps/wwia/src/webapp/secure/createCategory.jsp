<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title><ww:if test="category.id ==null"><ww:text name="text.createCategory"/></ww:if><ww:else><ww:text name="text.editCategory"/></ww:else></title>
  </head>

  <body>
    <%@ include file="/shared/jsp/categoryPicker.jspf"%>
    <ww:form action="saveCategory">
      <ww:token name="category.token" />
       <ww:if test="category.id != null">
       <ww:hidden name="categoryId" value="%{category.id}"/>
      </ww:if>
      <ww:select label="%{getText('text.parent')}" name="category.parentCategory"
                value="category.parentCategory.id" list="#categoryPicker.categories"
                listKey="id" listValue="#indent({top, ''}) + name" />
      <ww:textfield label="%{getText('text.name')}" name="category.name"/>
      <ww:submit value="Save"/>
    </ww:form>
  </body>
</html>