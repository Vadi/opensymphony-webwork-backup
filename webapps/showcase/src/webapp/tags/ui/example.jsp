<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>UI Tags Example</title>
    <ww:head/>
</head>

<body>

<ww:form method="post" enctype="multipart/form-data">
    <ww:textfield label="Name" name="name"/>
    <ww:datepicker label="Birthday" name="birthday"/>
    <ww:textarea label="Biograph" name="bio" cols="20" rows="3"/>
    <ww:select label="Favorite Color" list="{'Red', 'Blue', 'Green'}" name="favoriteColor"
            emptyOption="true" headerKey="None" headerValue="None"/>
    <ww:checkboxlist label="Friends" list="{'Patrick', 'Jason', 'Jay', 'Toby', 'Rene'}" name="friends"/>
    <ww:checkbox label="Age 18+" name="legalAge"/>
    <ww:doubleselect label="State" name="region" list="{'North', 'South'}"
                     doubleList="top == 'North' ? {'Oregon', 'Washington'} : {'Texas', 'Florida'}" doubleName="state"/>
    <ww:file label="Picture" name="picture"/>
    <ww:submit/>
</ww:form>

</body>
</html>