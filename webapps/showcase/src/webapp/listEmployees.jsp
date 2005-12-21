<%--
  Created by IntelliJ IDEA.
  User: rene
  Date: 17.12.2005
  Time: 13:51:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="/webwork" prefix="ww" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Available Employees</title></head>

<body>
<h1>Available Employees</h1>
<table>
    <tr>
        <th>Id</th>
        <th>First Name</th>
        <th>Last Name</th>
    </tr>
    <ww:iterator value="availableItems">
        <tr>
            <td><a href="<ww:url action="edit"><ww:param name="empId" value="empId"/></ww:url>"><ww:property value="empId"/></a></td>
            <td><ww:property value="firstName"/></td>
            <td><ww:property value="lastName"/></td>
        </tr>
    </ww:iterator>
</table>
<p><a href="<ww:url action="edit" includeParams="false"/>">Create new Employee</a></p>
<p><a href="<ww:url action="showcase" namespace="/" includeParams="false"/>">Back to Showcase Startpage</a></p>
</body>
</html>
