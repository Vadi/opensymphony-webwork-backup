<html>
<head>
<title>WebWork Tutorial - Example 4 (Freemarker Version)</title>
<meta name="help-path" content="/help/help4_1.html"/>
</head>
<body>
<p>Custom Component Example (Freemarker):</p>
<@ww.component template="components/datefield.vm" >
      <@ww.param name="label" value="%{'Date'}" />
      <@ww.param name="name" value="%{'mydatefield'}" />
      <@ww.param name="mysize" value="%{'3'}" />
      <@ww.param name="yearsize" value="%{'6'}" />
</@ww.component>
<br/>
<br/>
</body>
</html>
