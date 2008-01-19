<%--
  @author tmjee
  @version $Date$ $Id$
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ww" uri="/webwork" %>
<html>
  <head>
      <meta name="decorator" content="plain" />
      <title>ActionTagTest</title>
  </head>
  <body>
    <table>
        <tr colspan="3">
            <td>ActionTagTest</td>
        </tr>
        <tr>
            <td>open</td>
            <td><ww:url action="showActionTagDemo" namespace="/tags/non-ui/actionTag" includeContext="true" includeParams="none" /></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>This is Not - Included by the Action Tag</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>This is INCLUDED by the action tag</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>This is INCLUDED by the action tag (Page2)</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>This is INCLUDED by the action tag (Page3)</td>
            <td></td>
        </tr>
    </table>
  </body>
</html>