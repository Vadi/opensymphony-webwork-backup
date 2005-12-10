<html>
<head>
<title>WebWork Tutorial - Example 3 (Freemarker Version)</title>
<meta name="help-path" content="/help/help3_1.html"/>
</head>
<body>
<H2>Template & Theme Change Example (Freemarker Version):</H2>
<br/> More detail please refer to template/components directory.
<p>
<@ww.checkbox name="checkbox" label="A checkbox" fieldValue="checkbox_value" theme="xhtml/components" />
</p>
<p>
<@ww.textfield name="textfield" label="A text field" template="components/mytextfield.vm" />
</p>
</body>
</html>
