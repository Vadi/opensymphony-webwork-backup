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
                <ww:url action="actionNames" namespace="/config-browser" includeParams="none" encode="false" />
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>default</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>/config-browser</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>/messageStore</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>/continuations</td>
            <td></td>
        </tr>
        <tr>
            <td>verifyTextPresent</td>
            <td>/tags/ui</td>
            <td></td>
        </tr>
    </table>
</body>
</html>