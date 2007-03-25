<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>Acme Corp</title>
</head>

<body>
<p>
    <h4>Using URL Tag</h4>
    <ww:url id="changeLanguageURLen" action="changeLocale" includeParams="none">
        <ww:param name="request_locale">en</ww:param>
    </ww:url>

    <ww:url id="changeLanguageURLde" action="changeLocale" includeParams="none">
        <ww:param name="request_locale">de</ww:param>
    </ww:url>

    <ww:url id="changeLanguageURLfr" action="changeLocale" includeParams="none">
        <ww:param name="request_locale">fr</ww:param>
    </ww:url>

    <ww:a href="%{changeLanguageURLen}">English (en)</ww:a>
    <ww:a href="%{changeLanguageURLde}">German (de)</ww:a>
    <ww:a href="%{changeLanguageURLfr}">French (fr)</ww:a>
</p>
<p>
    <h4>Using href with request_locale parameter</h4>

    <a href="changeLocale.action?request_locale=en">English (en)</a>
    <a href="changeLocale.action?request_locale=de">German (de)</a>
    <a href="changeLocale.action?request_locale=fr">French (fr)</a>
</p>

<p>
    <h4></h4>

    <ww:url id="changeLanguageURLenParam" action="changeLocale?request_locale=en"  includeParams="none"/>
    <ww:url id="changeLanguageURLdeParam" action="changeLocale?request_locale=de"  includeParams="none"/>
    <ww:url id="changeLanguageURLfrParam" action="changeLocale?request_locale=fr"  includeParams="none"/>

    <ww:a href="%{changeLanguageURLenParam}">English (en)</ww:a>
    <ww:a href="%{changeLanguageURLdeParam}">German (de)</ww:a>
    <ww:a href="%{changeLanguageURLfrParam}">French (fr)</ww:a>
</p>

<hr width="90%" />
<h4>Internationalized Labels for Locale <ww:property value="%{locale}"/></h4>
<table>
    <tr>
        <th>i18n key</th>
        <th>value</th>
    </tr>
    <tr>
        <td><strong>label.add</strong></td>
        <td><ww:property value="%{getText('label.add')}" /></td>
    </tr>
    <tr>
        <td><strong>label.cancel</strong></td>
        <td><ww:property value="%{getText('label.cancel')}" /></td>
    </tr>
    <tr>
        <td><strong>label.delete</strong></td>
        <td><ww:property value="%{getText('label.delete')}" /></td>
    </tr>
    <tr>
        <td><strong>label.edit</strong></td>
        <td><ww:property value="%{getText('label.edit')}" /></td>
    </tr>
    <tr>
        <td><strong>label.save</strong></td>
        <td><ww:property value="%{getText('label.save')}" /></td>
    </tr>
    <tr>
        <td><strong>label.search</strong></td>
        <td><ww:property value="%{getText('label.search')}" /></td>
    </tr>
</table>
</body>
</html>
