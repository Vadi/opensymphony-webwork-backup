<%--
  @author tmjee
  @version $Date$ $Id$
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ww" uri="/webwork" %>
<html>
  <head>
      <meta name="decorator" content="plain" />
      <title>DebugTagTest</title>
  </head>
  <body>
    <table>
        <tr>
            <td colspan="3">DebugTagTest</td>
        </tr>
        <tr>
            <td>open</td>
            <td><ww:url value="/tags/non-ui/debug.jsp" includeContext="true" includeParams="none" /></td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>ValueStack in Debug Console</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>ValueStack Debug as XML</td>
            <td></td>
        </tr>
        <tr>
            <td>click</td>
            <td>debugAnchor</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>WebWork ValueStack Debug</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>Value Stack Contents</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>Stack Context</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>These items are available using the #key notation</td>
            <td></td>
        </tr>

        
    </table>

  </body>
</html>