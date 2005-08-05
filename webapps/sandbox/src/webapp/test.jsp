<%@ taglib uri="webwork" prefix="ww" %>

<ww:if test="1 == 1">
    1 == 1, that is true.
</ww:if>

<%
    HttpSession s = request.getSession(false);
    if (s != null) {
        out.print(".. but that doesn't mean create a session!");
    }
%>