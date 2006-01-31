<%@taglib prefix="ww" uri="/webwork" %>

<html>
<head>
<title>Show Case - Tags - UI Tags - Optiontransferselect</title>
<ww:head />
</head>
<body>

<ww:form action="lotsOfOptiontransferselectSubmit" namespace="/tags/ui" method="post">
	<ww:optiontransferselect 
		headerKey="-1"
		headerValue="--- Please Select ---"
		doubleHeaderKey="-1"
		doubleHeaderValue="--- Please Select ---"
		emptyOption="true"
		doubleEmptyOption="true"
		label="Favourite and Non-Favourite Cartoon Characters"
		leftTitle="Favourite Cartoon Characters"
		rightTitle="Non Favourite Cartoon Characters"
		name="favouriteCartoonCharacters" 
		list="defaultFavouriteCartoonCharacters" 
		doubleName="notFavouriteCartoonCharacters"
		doubleList="defaultNotFavouriteCartoonCharacters" />
		
	<ww:optiontransferselect
		label="Favourite Cars"
		leftTitle="Favourite Cars"
		rightTitle="Non Favourite Cars"
		name="favouriteCars"
		list="defaultFavouriteCars"
		doubleName="notFavouriteCars"
		doubleList="defaultNotFavouriteCars" />
		
	<ww:optiontransferselect 
		headerKey="-1"
		headerValue="--- Please Select ---"
		doubleHeaderKey="-1"
		doubleHeaderValue="--- Please Select ---"
		label="Favourite Motorcycles"
		leftTitle="Favourite Motorcycles"
		rightTitle="Non Favourite Motorcycles"
		name="favouriteMotorcycles" 
		list="defaultFavouriteMotorcycles"
		doubleName="notFavouriteMotorcycles"
		doubleList="defaultNotFavouriteMotorcycles" />
		
		
	<ww:optiontransferselect 
		emptyOption="true"
		doubleEmptyOption="true"
		label="Favourite Countries"
		leftTitle="Favourite Countries"
		rightTitle="Non Favourite Countries"
		name="favouriteCountries" 
		list="defaultFavouriteCountries" 
		doubleName="notFavouriteCountries" 
		doubleList="defaultNotFavouriteCountries" /> 
		
	<ww:submit value="Submit It" />
</ww:form>

</body>
