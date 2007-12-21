<%--
  @author tmjee
  @version $Date$ $Id$
--%>
<%@taglib prefix="ww" uri="/webwork" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="decorator" content="plain" />
  <title>ConfigBrowserTest</title>
</head>
<body>
    <table>
        <tr>
            <td colspan="3">Config Browser Test</td>
        </tr>
        <tr>
            <td>open</td>
            <td>
                <ww:url action="guess" namespace="/continuations" includeParams="none" encode="false" />
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>type</td>
            <td>guess_guess</td>
            <td>1</td>
        </tr>
        <tr>
            <td>clickAndWait</td>
            <td>guess_0</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>guess</td>
            <td></td>
        </tr>
        <tr>
            <td>type</td>
            <td>guess_guess</td>
            <td>2</td>
        </tr>
        <tr>
            <td>clickAndWait</td>
            <td>guess_0</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>guess</td>
            <td></td>
        </tr>
    </table>
</body>
</html>
