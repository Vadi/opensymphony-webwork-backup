<%--
  @author tmjee
  @version $Date$ $Id$
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ww" uri="/webwork" %>
<html>
  <head>
      <title>IteratorGeneratorTagTest</title>
      <meta name="decorator" content="plain" />
  </head>
  <body>
    <table>
        <tr>
            <td colspan="3">IteratorGeneratorTagTest</td>
        </tr>
        <tr>
            <td>open</td>
            <td><ww:url action="showGeneratorTagDemo" namespace="/tags/non-ui/iteratorGeneratorTag" includeContext="true" includeParams="none" /> </td>
            <td></td>
        </tr>
        <tr>
            <td>type</td>
            <td>submitGeneratorTagDemo_value</td>
            <td>aaa,bbb,ccc,ddd,eee,fff,ggg,hhh,iii,</td>
        </tr>
        <tr>
            <td>type</td>
            <td>submitGeneratorTagDemo_separator</td>
            <td>,</td>
        </tr>
        <tr>
            <td>type</td>
            <td>submitGeneratorTagDemo_count</td>
            <td>3</td>
        </tr>
        <tr>
            <td>clickAndWait</td>
            <td>submitGeneratorTagDemo_0</td>
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
            <td>verifyTextNotPresent</td>
            <td>ddd</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextNotPresent</td>
            <td>eee</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextNotPresent</td>
            <td>fff</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextNotPresent</td>
            <td>ggg</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextNotPresent</td>
            <td>hhh</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextNotPresent</td>
            <td>iii</td>
            <td></td>
        </tr>

    </table>
  </body>
</html>

