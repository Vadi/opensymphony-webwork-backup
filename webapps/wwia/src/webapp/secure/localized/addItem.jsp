<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title>Add An Item</title>
    <META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
  </head>

  <body>
    <%@ include file="/shared/jsp/categoryPicker.jspf"%>

    <ww:bean id="counter" name="com.opensymphony.webwork.util.Counter">
      <ww:param name="last" value="numItems" />
    </ww:bean>

    <script>
    function addAnother() {
      document.forms['addItem'].action = 'addItem!addItem.action';
      document.forms['addItem'].submit();
    }

    function removeItem(i) {
      document.forms['addItem'].action = 'addItem!removeItem.action';
      document.forms['addItem'].removeItem.value = i;
      document.forms['addItem'].submit();
    }
    </script>

    <ww:form name="addItem" action="addItem" method="post" >
      <ww:hidden name="numItems"/>
      <ww:hidden name="removeItem"/>
      <ww:i18n name="org.hibernate.auction.localization.LocalizedMessages">
      <ww:select label="%{getText('text.category',null)}" name="categoryIds" multiple="true"
                 list="#categoryPicker.categories.{? #this instanceof org.hibernate.auction.model.Category}" listKey="id" listValue="#indent({top, ''}) + getText(name)" />
      </ww:i18n>
      <ww:component template="hr"/>

      <ww:iterator value="#counter">
        <ww:set name="index" value="top - 1"/>
        <ww:textfield label="%{getText('text.name')}" name="items[%{#index}].name"/>
        <ww:textfield label="%{getText('text.desc')}" name="items[%{#index}].description"/>
        <ww:textfield label="%{getText('text.price')}" name="items[%{#index}].initialPrice"/>
        <ww:textfield label="%{getText('text.startDate')}" name="items[%{#index}].startDate"/>
        <ww:textfield label="%{getText('text.endDate')}" name="items[%{#index}].endDate"/>
        <tr>
          <td colspan="2">
            <a href="#" onclick="removeItem(<ww:property value="#index"/>)"><ww:text name="text.remove"/></a>
          </td>
        </tr>
        <ww:component template="hr"/>
      </ww:iterator>

      <tr>
        <td colspan="2">
          <input type="button" value="<ww:text name="text.another"/>" onclick="addAnother();"/>
        </td>
      </tr>
      <ww:submit value="%{getText('text.submit')}"/>
    </ww:form>
  </body>
</html>