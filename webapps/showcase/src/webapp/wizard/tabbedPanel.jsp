<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<ww:head theme="ajax" />
<link rel="stylesheet" type="text/css" href="<ww:url value="/webwork/tabs.css"/>">
</head>
<body>

<ww:tabbedPanel id="tabpanel">
	<ww:panel id="one" tabName="One" theme="ajax" >
		<ww:form namespace="/wizard" method="post">
			<ww:textfield name="name" label="Name" />
			<ww:textfield name="age" label="Age" />
			<ww:submit action="saveTabOne" value="Save" />
			<ww:submit action="endTabbedWizard" value="End" />
		</ww:form>
	</ww:panel>
	<ww:panel id="two" tabName="Two" theme="ajax" >
		<ww:form namespace="/wizard" method="post">
			<ww:radio list="%{#{'MALE':'Male', 'FEMALE':'Female'}}" label="Gender" name="gender"  />
			<ww:select list="%{#{'RED':'Red', 'GREEN':'Green', 'BLUE':'Blue'}}" label="Favourite Color" name="favouriteColor"  />
			<ww:submit action="saveTabTwo" value="Save" />
			<ww:submit action="endTabbedWizard" value="End" />
		</ww:form>
	</ww:panel>
</ww:tabbedPanel>


</body>
</html>