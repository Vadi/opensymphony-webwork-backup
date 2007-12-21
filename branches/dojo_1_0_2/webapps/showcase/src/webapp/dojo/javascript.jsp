
alert('helo world, time now = <%=System.currentTimeMillis() %>'+
<% if (request.getParameter("name") != null) { %>
 '\nName=<%=request.getParameter("name") %>'+
<% } %>
<% if(request.getParameter("comment") != null) { %>
'\nComment=<%=request.getParameter("comment") %>'+
<%} %>
'');

