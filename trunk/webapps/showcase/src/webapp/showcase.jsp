<%-- 
	showcase.jsp
	
	@version $Date$ $Id$
--%>

<%@ taglib uri="/webwork" prefix="ww" %>
<html>
<head>
    <title>Showcase</title>
</head>

<body>
<h1>Showcase samples</h1>

<p>The given examples will demonstrate the usages of all WebWork tags as well as validations etc.</p>

<p>
    <ul>
        <li><ww:url id="url" namespace="/continuations" action="guess"/><ww:a href="%{url}">Continuations example</ww:a></li>
        <li><ww:url id="url" namespace="/tags/ui" action="example" method="input"/><ww:a href="%{url}">UI Tags example</ww:a></li>
        <li><ww:url id="url" namespace="/validation" action="quizBasic" method="input"/><ww:a href="%{url}">Validation (basic)</ww:a></li>
        <li><ww:url id="url" namespace="/validation" action="quizClient" method="input"/><ww:a href="%{url}">Validation (client)</ww:a></li>
        <li><ww:url id="url" namespace="/validation" action="quizAjax" method="input"/><ww:a href="%{url}">Validation (ajax)</ww:a></li>
        <li><ww:url id="url" namespace="/skill" action="list"/><ww:a href="%{url}">List available Skills</ww:a></li>
        <li><ww:url id="url" namespace="/skill" action="edit"/><ww:a href="%{url}">Create/Edit Skill</ww:a></li>
        <li><ww:url id="url" namespace="/employee" action="list"/><ww:a href="%{url}">List available Employees</ww:a></li>
        <li><ww:url id="url" namespace="/employee" action="edit"/><ww:a href="%{url}">Create/Edit Employee</ww:a></li>
        <li><ww:url id="url" namespace="/validation" action="list"/><ww:a href="%{url}">Validation Examples</ww:a></li>
    </ul>
</p>
</body>
</html>
