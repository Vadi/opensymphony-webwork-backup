<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title></title>
  </head>

  <body>
    <ww:form action="createUser">
      <ww:token/>
      <ww:textfield label="%{getText('username')}" name="user.username"/>
      <ww:password label="%{getText('password')}" name="user.password"/>
      <ww:textfield label="%{getText('firstname')}" name="user.firstname"/>
      <ww:textfield label="%{getText('lastname')}" name="user.lastname"/>
      <ww:textfield label="%{getText('email')}" name="user.email"/>
      <ww:submit value="Submit"/>
    </ww:form>
  </body>
</html>