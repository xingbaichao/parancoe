<%@ page import="org.acegisecurity.context.SecurityContextHolder" %>
<%@ page import="org.acegisecurity.Authentication" %>
<%@ page import="org.acegisecurity.ui.AccessDeniedHandlerImpl" %>

<html>
  <head>   
    <% request.setAttribute("cp", ".."); %>
    <%@ include file="WEB-INF/jsp/head.jspf" %>
  </head>
  <body>
    <jsp:include page="WEB-INF/jsp/header.jsp"/>
    <br />
    <br />
	<h1>Sorry, access is denied</h1>
	
	<jsp:include page="WEB-INF/jsp/footer.jsp"/>
  </body>

</html>

