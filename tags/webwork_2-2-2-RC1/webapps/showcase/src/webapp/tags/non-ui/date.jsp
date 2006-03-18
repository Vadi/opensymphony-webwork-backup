<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>UI Tags Example: Date</title>
    <ww:head/>
</head>

<body>
<ww:action id="myDate" name="date" namespace="/" executeResult="false" />

<table>
    <tr>
        <th>Name</th>
        <th>Format</th>
        <th>Output</th>
    </tr>
    <tr>
        <td><strong>Current date</strong></td>
        <td>yyyy/MM/dd hh:mm:ss</td>
        <td><ww:date name="#myDate.now" format="yyyy/MM/dd hh:mm:ss" /></td>
    </tr>
    <tr>
        <td><strong>Current date</strong></td>
        <td>dd.MM.yyyy hh:mm:ss</td>
        <td><ww:date name="#myDate.now" format="dd.MM.yyyy hh:mm:ss" /></td>
    </tr>
    <tr>
        <td><strong>Current time (24h)</strong></td>
        <td>HH:mm:ss</td>
        <td><ww:date name="#myDate.now" format="HH:mm:ss" /></td>
    </tr>
    <tr>
        <td><strong>Before date</strong></td>
        <td>MMM, dd yyyy</td>
        <td><ww:date name="#myDate.before" format="MMM, dd yyyy" /></td>
    </tr>
    <tr>
        <td><strong>Before date</strong></td>
        <td>nice</td>
        <td><ww:date name="#myDate.before" nice="true"/></td>
    </tr>
    <tr>
        <td><strong>After date</strong></td>
        <td>dd.MM.yyyy</td>
        <td><ww:date name="#myDate.after" format="dd.MM.yyyy" /></td>
    </tr>
    <tr>
        <td><strong>After date</strong></td>
        <td>nice</td>
        <td><ww:date name="#myDate.after" nice="true"/></td>
    </tr>
</table>
</body>
</html>