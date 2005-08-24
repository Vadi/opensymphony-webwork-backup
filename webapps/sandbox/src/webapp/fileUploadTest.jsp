<%@ taglib uri="webwork" prefix="ww" %>

<html>
<head>
    <title>File Upload Test</title>
</head>
<body>

    <h2>File Upload Test</h2>

    <ww:if test="hasActionMessages()">
        <table style="border-color=green; border-style=solid; border-width=thin;">
            <tr><th align="center">Infomational Messages:</th></tr>
            <ww:iterator id="actMsg" value="actionMessages">
            <tr><td align="center"><ww:property value="#attr.actMsg"/></td></tr>
            </ww:iterator>
        </table>
        <hr size="2"/>
    </ww:if>

    <ww:if test="hasActionErrors()">
        <table style="border-color=red; border-style=solid; border-width=thin;">
            <tr><th align="center">Errors Messages:</th></tr>
            <ww:iterator id="actErr" value="actionErrors">
            <tr><td align="center"><ww:property value="#attr.actErr"/></td></tr>
            </ww:iterator>
        </table>
        <hr size="2"/>
    </ww:if>

    <form name="uploadForm" action="/upload/attempt.action" method="post" enctype="multipart/form-data">
        <table style="border-style=solid; border-width=thin;">
            <tr>
                <td colspan="2">This form should disallow text files, and allow GIF files under 100 KBs to be uploaded.</td>
            </tr>

            <ww:file name="uploadedItem" label="Test File"/>

            <tr>
                <td><input type="reset" value="Reset Form"/></td>
                <td><input type="submit" value="Upload File"/></td>
            </tr>
        </table>
    </form>

</body>
</html>
