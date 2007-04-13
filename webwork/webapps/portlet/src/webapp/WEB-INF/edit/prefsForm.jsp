<%@ taglib prefix="ww" uri="/webwork" %>
<ww:form action="prefsFormSave.action" method="POST">
	<ww:textfield label="Preference one" name="preferenceOne" value="%{preferenceOne}"/>
	<ww:textfield label="Preference two" name="preferenceTwo" value="%{preferenceTwo}"/>
	<ww:submit value="Save prefs"/>
</ww:form>

<a href="<ww:url action="index"/>">Back to front page</a>