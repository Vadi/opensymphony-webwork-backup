<%--
  @author tmjee
  @version $Date$ $Id$
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ww" uri="/webwork" %>
<html>
  <head>
      <title>SubsetIteratorTagTest</title>
      <meta content="plain" name="decorator" />
  </head>
  <body>
    <table>
        <tr>
            <td colspan="3">SubsetIteratorTagTest</td>
        </tr>
        <tr>
            <td>open</td>
            <td><ww:url action="showSubsetTagDemo" namespace="/tags/non-ui/subsetIteratorTag" includeContext="true" includeParams="none" /></td>
            <td></td>
        </tr>
        <tr>
            <td>type</td>
            <td>submitSubsetTagDemo_iteratorValue</td>
            <td>aaa,bbb,ccc,ddd,eee,fff</td>
        </tr>
        <tr>
            <td>type</td>
            <td>submitSubsetTagDemo_count</td>
            <td>3</td>
        </tr>
        <tr>
            <td>type</td>
            <td>submitSubsetTagDemo_start</td>
            <td>2</td>
        </tr>
        <tr>
            <td>clickAndWait</td>
            <td>submitSubsetTagDemo_0</td>
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
    </table>
  </body>
</html>