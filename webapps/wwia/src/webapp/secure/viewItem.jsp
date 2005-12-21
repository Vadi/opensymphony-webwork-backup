<%@ taglib uri="webwork" prefix="ww"%>
<html>
  <head>
    <title>View Item</title>
  </head>

  <body>
  <ww:push value="item">
  <ww:property value="%{top}"/>
  <ww:label label="Name" value="%{name}"/>
  <ww:label label="Description" value="%{description}"/>
  <ww:label label="Start Date" value="%{startDate}"/>
  <ww:label label="End Date" value="%{endDate}"/>
  <ww:label label="Initial Price" value="%{initialPrice}"/>

  </ww:push>
  </body>
</html>