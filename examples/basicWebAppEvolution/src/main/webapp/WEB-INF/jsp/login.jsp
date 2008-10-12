<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<html>
  <head>
    <% request.setAttribute("cp", "."); %>
	<%@ include file="head.jspf" %>
  </head>

  <body>
    <jsp:include page="header.jsp"/>
    
    <h1>Parancoe Login</h1>

	<P>Valid users:
	<P>
	<P>username <b>admin</b>, password <b>admin</b> (supervisor)
	<P>username <b>parancoe</b>, password <b>parancoe</b> (normal user)
	
	<p>
	
    <%-- this form-login-page form is also used as the 
         form-error-page to ask for a login again.
         --%>
    <c:if test="${not empty param.login_error}">
      <font color="red">
        Your login attempt was not successful, try again.<BR><BR>
        Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
      </font>
    </c:if>

    <form action="<c:url value='securityCheck.secure'/>" method="POST">
      <table>
        <tr><td>User:</td><td><input type='text' name='j_username' <c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY) %>'</c:if>></td></tr>
        <tr><td>Password:</td><td><input type='password' name='j_password'></td></tr>
        <tr><td><input type="checkbox" name="_acegi_security_remember_me"></td><td>Don't ask for my password for two weeks</td></tr>

        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      </table>

    </form>
  <jsp:include page="footer.jsp"/>
  </body>
</html>
