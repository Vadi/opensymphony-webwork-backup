<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>    

<%
	if (request.getParameter("name") != null) {
%>
	Name = <%=request.getParameter("name") %><br/>
<%
	}
	if (request.getParameter("comment") != null) {
%>
	Comment = <%=request.getParameter("comment") %><br/>
<%
	}
%>
Current Time = <%=System.currentTimeMillis() %>
