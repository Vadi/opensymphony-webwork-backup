<html>
<head>
    <title>New Person</title>
</head>

<body>
<@ww.form action="newPerson">
    <@ww.textfield label="First Name" name="person.name"/>
    <@ww.textfield label="Last Name" name="person.lastName"/>
    <@ww.submit value="Create person"/>
</@ww.form>
</body>
</html>