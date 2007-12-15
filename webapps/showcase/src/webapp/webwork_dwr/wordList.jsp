<%--
  Created by IntelliJ IDEA.
  User: tmjee
  Date: Dec 15, 2007
  Time: 9:35:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ww" uri="/webwork" %>

<h4>Word List</h4>
<ul>
<ww:iterator id="currentWord" value="%{allWords}">
    <li>
        <ww:property value="%{#currentWord}" />
    </li>
</ww:iterator>
</ul>
