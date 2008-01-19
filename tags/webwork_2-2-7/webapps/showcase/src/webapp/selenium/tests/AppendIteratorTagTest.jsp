<%--
  @author tmjee
  @version $Date$ $Id$
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ww" uri="/webwork" %>
<html>
  <head>
      <title>Append Iterator Tag Test</title>
      <meta name="decorator" content="plain" />
  </head>
  <body>
    <table>
        <tr>
            <td colspan="3">AppendIteratorTagTest</td>
        </tr>
        <tr>
            <td>open</td>
            <td><ww:url action="showAppendTagDemo" namespace="/tags/non-ui/appendIteratorTag" includeContext="true" includeParams="none" /></td>
            <td></td>
        </tr>
        <tr>
            <td>type</td>
            <td>submitAppendTagDemo_iteratorValue1</td>
            <td>aaa,bbb,ccc</td>
        </tr>
        <tr>
            <td>type</td>
            <td>submitAppendTagDemo_iteratorValue2</td>
            <td>ddd,eee,fff</td>
        </tr>
        <tr>
            <td>clickAndWait</td>
            <td>submitAppendTagDemo_0</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>aaa</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>bbb</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>ccc</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>ddd</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>eee</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>fff</td>
            <td></td>
        </tr>
    </table>
  </body>
</html>