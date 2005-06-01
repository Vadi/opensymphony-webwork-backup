<%
if (request.getParameter("sleep") != null) { 
	Thread.sleep(Long.valueOf(request.getParameter("sleep")).longValue());
}
%>
<%=new java.util.Date()%>