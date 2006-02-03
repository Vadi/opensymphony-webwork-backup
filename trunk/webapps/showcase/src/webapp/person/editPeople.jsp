<%@ taglib prefix="ww" uri="/webwork" %>

<html>
<head>
    <title>Edit Persons (batch-edit)</title>
</head>

<body>
<ww:form action="doEditPerson" theme="simple" validate="false">

    <table>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
        </tr>
        <ww:iterator id="p" value="persons">
            <tr>
                <td>
                    <ww:property value="%{id}" />
                </td>
                <td>
                    <ww:textfield label="First Name" name="persons(%{id}).name" value="%{name}" theme="simple" />
                </td>
                <td>
                    <ww:textfield label="Last Name" name="persons(%{id}).lastName" value="%{lastName}" theme="simple"/>
                </td>
            </tr>
        </ww:iterator>
    </table>

    <ww:submit value="Save all persons"/>
</ww:form>
</body>
</html>