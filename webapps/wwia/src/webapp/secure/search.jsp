<%@ taglib prefix="ww" uri="webwork"%>
<%--
  Created by IntelliJ IDEA.
  User: plightbo
  Date: Nov 5, 2004
  Time: 5:05:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Simple jsp page</title></head>
  <body>

  <ww:bean id="counter" name="com.opensymphony.webwork.util.Counter">
    <ww:param name="last" value="pages"/>
  </ww:bean>

  <ww:text name="pages.total">
      <ww:param value="pages"/>
      <ww:param>
        <ww:if test="page > 1">
          <ww:text name="pages.prev">
            <ww:param><a href="<ww:url><ww:param name="page" value="page - 1"/></ww:url>"></ww:param>
            <ww:param></a></ww:param>
          </ww:text>
        </ww:if>

        <ww:iterator value="#counter">
            <a href="<ww:url><ww:param name="page" value="top"/></ww:url>"><ww:text name="pages.pages">
                <ww:param value="top"/>
            </ww:text></a>
        </ww:iterator>

        <ww:if test="page < pages">
          <ww:text name="pages.next">
            <ww:param><a href="<ww:url><ww:param name="page" value="page + 1"/></ww:url>"></ww:param>
            <ww:param></a></ww:param>
          </ww:text>
        </ww:if>
      </ww:param>
  </ww:text>

  <ww:iterator value="items">
      <li><ww:property value="name"/>, <ww:property value="description"/></li>
  </ww:iterator>

  </body>
</html>