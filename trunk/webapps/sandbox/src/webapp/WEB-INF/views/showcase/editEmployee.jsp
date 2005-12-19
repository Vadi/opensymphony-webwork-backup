<%--
  Created by IntelliJ IDEA.
  User: rene
  Date: 17.12.2005
  Time: 13:52:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="webwork" prefix="ww" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ww:if test="currentEmployee!=null">
    <ww:set name="title" value="'Edit Employee'"/>
</ww:if>
<ww:else>
    <ww:set name="title" value="'Create Employee'"/>
</ww:else>
<html>
<head>
    <title><ww:property value="#title"/></title>
    <link href="<%=request.getContextPath()%>/webwork/jscalendar/calendar-blue.css" rel="stylesheet" type="text/css" media="all"/>
</head>

<body>
<h1><ww:property value="#title"/></h1>

<ww:action id="skillAction" namespace="/showcase/skill" name="list"/>
<ww:form action="save">
    <ww:textfield label="Employee Id" name="currentEmployee.empId"/>
    <ww:textfield label="First Name" name="currentEmployee.firstName"/>
    <ww:textfield label="Last Name" name="currentEmployee.lastName"/>
    <ww:datepicker label="Birthdate" name="currentEmployee.birthDate"/>
    <ww:textfield label="Salary" name="currentEmployee.salary"/>
    <ww:checkbox fieldValue="true" label="Married" name="currentEmployee.married"/>
    <ww:combobox list="availablePositions" label="Position" name="currentEmployee.position"/>
    <ww:select list="#skillAction.availableItems" label="Main Skill" name="currentEmployee.mainSkill"/>
    <ww:select list="#skillAction.availableItems" label="Other Skills" name="currentEmployee.otherSkills" multiple="true"/>
    <ww:password label="Password" name="currentEmployee.password"/>
    <ww:radio list="availableLevels" name="currentEmployee.level"/>
    <ww:textarea label="Comment" name="currentEmployee.comment" cols="50" rows="3"/>
    <ww:submit value="Save"/>
</ww:form>
<p><a href="<ww:url action="list"/>">Back to Employee List</a></p>
</body>
</html>