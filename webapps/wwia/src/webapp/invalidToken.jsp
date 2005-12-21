<%@ taglib uri="webwork" prefix="ww" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Invalid Token</title></head>
  <body>
  <div style="border=1;border.style=dashed">
  <span align="center" class="errorMessage">Invalid Form Token!</span><p/>
  There was either no form token or an invalid form token submitted. This can happen when refresh
  is hit on a page after a form is processed or when the submit button is pressed multiple times. Please
  hit your back button to return to the form.
  </div>
  </body>
</html>