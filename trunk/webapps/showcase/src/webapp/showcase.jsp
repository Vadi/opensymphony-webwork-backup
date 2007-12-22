<%-- 
	showcase.jsp
	
	@version $Date$ $Id$
--%>

<%@ taglib uri="/webwork" prefix="ww" %>
<html>
<head>
    <title>Showcase</title>
    <ww:head theme="simple"/>
</head>

<body>
<h1>Showcase samples</h1>

<p>The given examples will demonstrate the usages of all WebWork tags as well as validations etc.</p>

<p>
	<ul>
        <!-- config-browser -->
        <li><ww:url id="url" namespace="/config-browser" action="index"/><ww:a href="%{url}">Configuration browser (Great for development!)</ww:a></li>

		<!-- continuation -->
		<li><ww:url id="url" namespace="/continuations" action="guess"/><ww:a href="%{url}">Continuations Example</ww:a></li>
		
		<!-- tags -->
		<li><ww:url id="url" value="/tags"/><ww:a href="%{url}">Tags Examples</ww:a></li>
		
		<!-- fileupload -->
		<li><ww:url id="url" namespace="/fileupload" action="upload"/><ww:a href="%{url}">File Upload Example</ww:a></li>

		<!-- crud -->
		<li><ww:url id="url" value="/empmanager"/><ww:a href="%{url}">CRUD Examples</ww:a></li>
		
		<!-- person manager sample -->
		<li><ww:url id="url" value="/person"/><ww:a href="%{url}">PersonManager Sample</ww:a></li>

        <!-- validation -->
        <li><ww:url id="url" value="/validation"/><ww:a href="%{url}">Validation Examples</ww:a></li>

        <!-- ajax -->
        <li><ww:url id="url" value="/ajax"/><ww:a href="%{url}">AJAX Examples</ww:a></li>
        
        <!-- action chaining -->
		<li><ww:url id="url" namespace="actionchaining" action="actionChain1" method="input" /><ww:a href="%{url}">Action Chaining Example</ww:a></li>

        <!-- execute and wait -->
        <li><ww:url id="url" value="/wait"/><ww:a href="%{url}">Execute and Wait Examples</ww:a></li>

        <!-- token -->
        <li><ww:url id="url" value="/token"/><ww:a href="%{url}">Token Examples (double post)</ww:a></li>

        <!-- filedownload -->
        <li><ww:url id="url" value="/filedownload"/><ww:a href="%{url}">File Download Example</ww:a></li>
        
        <!-- model-driven -->
        <li><ww:url id="url" action="modelDriven" namespace="/modelDriven" method="input"/><ww:a href="%{url}">Model Driven Example</ww:a></li>
        
        <!-- freemarker -->
        <li><ww:url id="url" value="/freemarker" /><ww:a href="%{#url}">Freemarker Example</ww:a></li>
        
        <!--  wizard -->
        <li><ww:url id="url" action="index" namespace="/wizard" /><ww:a href="%{url}">Wizard Example</ww:a></li>
        
        <!--  session Invalidation -->
        <li><ww:url id="url" action="start" namespace="/sessionInvalidation"/><ww:a href="%{url}">Session Invalidation Example</ww:a></li>
        
        <!--  flash  -->
        <li><ww:url id="url" action="start" namespace="/flash"/><ww:a href="%{url}">Flash Example</ww:a></li>

        <!--  i18n  -->
        <li><ww:url id="url" action="changeLocale" namespace="/i18n"/><ww:a href="%{url}">I18N Example</ww:a></li>
        
        <!-- MessageStoreInterceptor (capture error/field/action error/messages across request -->
        <li><ww:url id="url" action="showForm" namespace="/messageStore" /><ww:a href="%{url}">Message Store Eample</ww:a></li>

        <!-- WebWork / DWR integration -->
        <li><ww:url id="url" action="index" namespace="/webwork_dwr" /><ww:a href="%{url}">WebWork DWR integration</ww:a></li>

        <!-- JSON -->
        <li><ww:url id="url" action="index" namespace="/json" /><ww:a href="%{url}">JSON</ww:a></li>

    </ul>
</p>

</body>
</html>
