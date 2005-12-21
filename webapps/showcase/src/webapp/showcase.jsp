<%@ taglib uri="webwork" prefix="ww" %>
<html>
<head>
    <title>Showcase</title>
</head>

<body>
<h1>Showcase samples</h1>

<p>The given examples will demonstrate the usages of all WebWork tags as well as validations etc.</p>

<p>
    <ul>
        <li><ww:url id="url" value="/continuations/guess.action"/><ww:a href="%{url}">Continuations example</ww:a></li>
        <li><a href="showcase/skill/list.action">List available Skills</a></li>
        <li><a href="showcase/skill/edit.action">Create/Edit Skill</a></li>
        <li><a href="showcase/employee/list.action">List available Employees</a></li>
        <li><a href="showcase/employee/edit.action">Create/Edit Employee</a></li>
    </ul>
</p>
</body>
</html>
