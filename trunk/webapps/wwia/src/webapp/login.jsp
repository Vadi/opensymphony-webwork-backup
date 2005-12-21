<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title>Login</title>
  </head>

  <body>
    <ww:form action="login">
      <ww:textfield label="%{getText('username')}" name="user.username"/>
      <ww:password label="%{getText('password')}" name="user.password"/>
      <ww:submit value="Submit"/>
    </ww:form>

    <hr/>

    <a href="createUserPrepare.action">Create an account</a>

  </body>
</html>