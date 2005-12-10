<html>
<head>
<title>WebWork Tutorial - Example 2 (Freemarker Version)</title>
<meta name="help-path" content="/help/help2_1.html"/>
</head>
<body>
<style type="text/css">
  .errorMessage { color: red; }
  .wwFormTable {}
  .label {font-style:italic; }
  .errorLabel {font-style:italic; color:red; }
  .errorMessage {font-weight:bold; text-align: center; color:red; }
  .checkboxLabel {}
  .checkboxErrorLabel {color:red; }
  .required {color:red;}
</style>
<p>UI Form Tags Example using Freemarker:</p>
<@ww.form action="/ftl_formProcessing.action" method="post">
       <@ww.checkbox name="checkbox" label="A checkbox" fieldValue="checkbox_value" />
       <@ww.file name="file" label="A file field" />
       <@ww.hidden name="hidden" value="hidden_value" />
       <@ww.label label="A label" />
       <@ww.password name="password" label="A password field" />
       <@ww.radio name="radio" label="Radio buttons" list="{'One', 'Two', 'Three'}" />
       <@ww.select name="select" label="A select list" list="{'One', 'Two', 'Three'}" emptyOption="true" />
       <@ww.textarea name="textarea" label="A text area" rows="3" cols="40" />
       <@ww.textfield name="textfield" label="A text field" />
       <@ww.submit value="Send Form" />
</@ww.form>
</body>
</html>
