<%@ taglib uri="webwork" prefix="ww" %>

<html>
    <head>
        <title>JavaScript Validation Input</title>
    </head>
    
    <body>
        <ww:form name="'test'" action="javascript" validate="true" >
            <ww:textfield label="'Can't be empty'" name="'test'" required="true"/>
            <ww:submit value="'Submit'"/>
        </ww:form>
    </body>
</html>