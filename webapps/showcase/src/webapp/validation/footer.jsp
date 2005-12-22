<%-- 
	footer.jsp
	
	@author tm_jee
	@version $Date$ $Id$
--%>


<%@taglib uri="/webwork" prefix="ww" %>

<hr/>

<ww:url id="backToValidationExamples" action="list" namespace="/validation" />
<ww:url id="backToShowCase" action="showcase" namespace="/" />
		
<ww:a href="%{backToValidationExamples}">Back To Validation Examples</ww:a>&nbsp;
<ww:a href="%{backToShowCase}">Back To Showcase</ww:a>

