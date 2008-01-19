<%--
  @author tmjee
  @version $Date$ $Id$
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ww" uri="/webwork" %>

<ww:action id="myDate" name="date" namespace="/" executeResult="false" />

<html>
  <head>
      <meta name="decorator" content="plain" />
      <title>DateTagTest</title>
  </head>
  <body>
    <table>
        <tr>
            <td>open</td>
            <td><ww:url value="/tags/non-ui/date.jsp" includeContext="true" includeParams="none" /></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td><ww:date name="#myDate.before" format="MMM, dd yyyy" /></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td><ww:date name="#myDate.before" nice="true"/></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td><ww:date name="#myDate.after" format="dd.MM.yyyy" /></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td><ww:date name="#myDate.after" nice="true"/></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td><ww:date name="#myDate.past" format="dd/MM/yyyy hh:mm"/></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td><ww:date name="#myDate.past" format="MM-dd-yy"/></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td><ww:date name="#myDate.past" nice="true"/></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td><ww:date name="#myDate.future" nice="true"/></td>
            <td></td>
        </tr>
    </table>
  </body>
</html>
