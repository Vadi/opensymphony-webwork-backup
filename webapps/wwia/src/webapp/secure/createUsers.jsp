<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title></title>
  </head>

  <body>
    <ww:form action="createUsers">
      <ww:token/>

      <ww:textfield label="%{getText('username')}" name="users[0].username"/>
      <ww:password label="%{getText('password')}" name="users[0].password"/>
      <ww:textfield label="%{getText('firstname')}" name="users[0].firstname"/>
      <ww:textfield label="%{getText('lastname')}" name="users[0].lastname"/>
      <ww:textfield label="%{getText('email')}" name="users[0].email"/>

      <tr><td colspan="2"><hr/></td></tr>

      <ww:textfield label="%{getText('username')}" name="users[1].username"/>
      <ww:password label="%{getText('password')}" name="users[1].password"/>
      <ww:textfield label="%{getText('firstname')}" name="users[1].firstname"/>
      <ww:textfield label="%{getText('lastname')}" name="users[1].lastname"/>
      <ww:textfield label="%{getText('email')}" name="users[1].email"/>

      <tr><td colspan="2"><hr/></td></tr>

      <ww:textfield label="%{getText('username')}" name="users[2].username"/>
      <ww:password label="%{getText('password')}" name="users[2].password"/>
      <ww:textfield label="%{getText('firstname')}" name="users[2].firstname"/>
      <ww:textfield label="%{getText('lastname')}" name="users[2].lastname"/>
      <ww:textfield label="%{getText('email')}" name="users[2].email"/>

      <tr><td colspan="2"><hr/></td></tr>

      <ww:submit value="Submit"/>
    </ww:form>
  </body>
</html>