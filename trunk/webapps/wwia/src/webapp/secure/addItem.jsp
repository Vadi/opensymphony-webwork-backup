<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title></title>
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
      <ww:select label="Category" name="categoryIds" multiple="true"
                 list="#categoryPicker.categories" listKey="id" listValue="#indent({top, ''}) + name" />

      <ww:component template="hr"/>

      <ww:iterator value="#counter">
        <ww:set name="index" value="top - 1"/>
        <ww:textfield label="Name" name="items[%{#index}].name"/>
        <ww:textfield label="Desc" name="items[%{#index}].description"/>
        <ww:textfield label="Price" name="items[%{#index}].initialPrice"/>
        <ww:textfield label="Start Date" name="items[%{#index}].startDate"/>
        <ww:textfield label="End Date" name="items[%{#index}].endDate"/>
        <tr>
          <td colspan="2">
            <a href="#" onclick="removeItem(<ww:property value="#index"/>)">Remove</a>
          </td>
        </tr>
        <ww:component template="hr"/>
      </ww:iterator>

      <tr>
        <td colspan="2">
          <input type="button" value="Another" onclick="addAnother();"/>
        </td>
      </tr>
      <ww:submit value="Submit"/>
    </ww:form>
  </body>
</html>