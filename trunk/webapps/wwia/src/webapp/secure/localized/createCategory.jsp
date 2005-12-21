<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title><ww:text name="text.createCategory"/></title>
  </head>

  <body>
    <%@ include file="/shared/jsp/categoryPicker.jspf"%>

      <ww:form action="saveCategory">
      <ww:i18n name="org.hibernate.auction.localization.LocalizedMessages">
      <ww:select label="%{getText('text.parent')}" name="category.parentCategory"
                 value="category.parentCategory.id" list="#categoryPicker.categories"
                 listKey="id" listValue="#indent({top, ''}) + getText(name)" />
      </ww:i18n>
      <ww:if test="category.id != null">
       <ww:hidden name="category" value="%{category.id}"/>
      </ww:if>
      <ww:textfield label="%{getText('text.name')}" name="category.name"/>
      <ww:label name="getText('text.localizedTexts')"/>
      <ww:iterator value="@org.hibernate.auction.localization.Locales@locales()">
      <ww:textfield label="%{key}" name="categoryTexts['%{value}']"/>
      </ww:iterator>
      <ww:submit value="%{getText('text.submit')}"/>
    </ww:form>
  </body>
</html>