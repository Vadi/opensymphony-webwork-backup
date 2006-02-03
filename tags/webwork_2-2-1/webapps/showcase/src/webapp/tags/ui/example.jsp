<%@ taglib prefix="ww" uri="/webwork" %>
<html>
<head>
    <title>UI Tags Example</title>
    <ww:head/>
</head>

<body>

<ww:form action="exampleSubmit" method="post" enctype="multipart/form-data">
    <ww:textfield label="Name" name="name"/>
    <ww:datepicker label="Birthday" name="birthday"/>
    <ww:textarea label="Biograph" name="bio" cols="20" rows="3"/>
    <ww:select label="Favorite Color" list="{'Red', 'Blue', 'Green'}" name="favoriteColor"
            emptyOption="true" headerKey="None" headerValue="None"/>
    <ww:select label="Favourite Language" list="favouriteLanguages" name="favouriteLanguage"
    		listKey="key" listValue="description" emptyOption="true" headerKey="None" 
    		headerValue="None"/>
    <ww:checkboxlist label="Friends" list="{'Patrick', 'Jason', 'Jay', 'Toby', 'Rene'}" name="friends"/>
    <ww:checkbox label="Age 18+" name="legalAge"/>
    <ww:doubleselect label="State" name="region" list="{'North', 'South'}"
    				 value="'South'" doubleValue="'Florida'"
                     doubleList="top == 'North' ? {'Oregon', 'Washington'} : {'Texas', 'Florida'}" 
                     doubleName="state"
                     headerKey="-1" 
                     headerValue="---------- Please Select ----------"
                     emptyOption="true"
                     />
    <ww:doubleselect label="Favourite Vehical" name="favouriteVehicalType" 
    				 list="vehicalTypeList" listKey="key" listValue="description" 
    				 value="'MotorcycleKey'" doubleValue="'YamahaKey'"
    				 doubleList="vehicalSpecificList"
    				 doubleListKey="key" doubleListValue="description"
    				 doubleName="favouriteVehicalSpecific" headerKey="-1"
    				 headerValue="---------- Please Select ----------"
    				 emptyOption="true"
    				 /> 
    <ww:file label="Picture" name="picture"/>
    <ww:optiontransferselect 
    	label="Favourite Cartoons Characters"
		name="leftSideCartoonCharacters" 
		leftTitle="Left Title"
		rightTitle="Right Title"
		list="{'Popeye', 'He-Man', 'Spiderman'}" 
		multiple="true"
		headerKey="headerKey"
		headerValue="--- Please Select ---"
		emptyOption="true"
		doubleList="{'Superman', 'Mickey Mouse', 'Donald Duck'}" 
		doubleName="rightSideCartoonCharacters"
		doubleHeaderKey="doubleHeaderKey"
		doubleHeaderValue="--- Please Select ---" 
		doubleEmptyOption="true"
		doubleMultiple="true"
	/>
    
    
    
    <ww:submit/>
</ww:form>

</body>
</html>