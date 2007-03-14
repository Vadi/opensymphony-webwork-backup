<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="ww" uri="/webwork" %>
    
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

Hello <%=request.getParameter("name") %> <br/>
You clicked on <ww:property value="%{buttonName}" /><br/>
The current time is <%=System.currentTimeMillis() %>    
    