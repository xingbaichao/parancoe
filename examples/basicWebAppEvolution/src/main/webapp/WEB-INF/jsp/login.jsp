<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>

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
        Your login attempt was not successful, try again.<br />
        Reason: <%= session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION").toString() %>
        		
    </font>
</c:if>



    <form action="<c:url value='j_spring_security_check'/>" method="POST">
      <table>
        <tr><td>User:</td><td><input type='text' name='j_username' value=''  /></td></tr>
        <tr><td>Password:</td><td><input type='password' name='j_password' value=''/></td></tr>
       
        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      </table>

    </form>
  <jsp:include page="footer.jsp"/>
  </body>
</html>
