<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>JavaScript Validation Input</title>
        <link rel="stylesheet" href="styles.css">
        <ww:component template="'validation'"/>
    </head>

    <body>
        <ww:form name="'javascriptValidation'" action="'javascriptValidation'" validate="true" >
            <ww:textfield label="'Required String'" name="'requiredString'" required="true" />
            <ww:textfield label="'Some Int'" name="'intRange'" required="true"/>
            <ww:select label="'Email (select)'" name="'email'" list="{'Select', 'foo@bar.com', 'baz@biz.com'}"/>
            <ww:textfield label="'URL'" name="'url'" required="true"/>
            <ww:textfield label="'Date'" name="'date'" required="true"/>
            <tr><td colspan="2"><hr/></td></tr>
            <ww:textfield label="'Bean Text'" name="'bean.text'" required="true"/>
            <ww:textfield label="'Bean Date'" name="'bean.date'" required="true"/>
            <ww:textfield label="'Bean Number'" name="'bean.number'" required="true"/>
            <ww:submit value="'Submit'"/>
        </ww:form>
    </body>
</html>