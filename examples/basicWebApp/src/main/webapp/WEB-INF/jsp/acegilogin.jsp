<%--

    Copyright (C) 2006-2013 The Parancoe Team <info@parancoe.org>

    This file is part of Parancoe Example - Basic WebApp.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter" %>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.acegisecurity.AuthenticationException" %>

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
        Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
      </font>
    </c:if>

    <form action="<c:url value='securityCheck.secure'/>" method="POST">
      <table>
        <tr><td>User:</td><td><input type='text' name='j_username' <c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'</c:if>></td></tr>
        <tr><td>Password:</td><td><input type='password' name='j_password'></td></tr>
        <tr><td><input type="checkbox" name="_acegi_security_remember_me"></td><td>Don't ask for my password for two weeks</td></tr>

        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      </table>

    </form>
  <jsp:include page="footer.jsp"/>
  </body>
</html>
