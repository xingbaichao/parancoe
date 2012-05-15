<%--

    Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>

    This file is part of Parancoe Plugin Spring Security Evolution.

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
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>

<html>
  <head>
    <% request.setAttribute("cp", "."); %>	
  </head>

  <body>
   
	
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
        <tr><td>User:</td><td><input type='text' name='j_username' autofocus="autofocus" /></td></tr>
        <tr><td>Password:</td><td><input type='password' name='j_password'  /></td></tr>
       
        <tr><td colspan='2'><input name="submit" type="submit"></td></tr>
        <tr><td colspan='2'><input name="reset" type="reset"></td></tr>
      </table>
    </form>
    
    
    <h1>Register with Google OpenID support</h1>
    <form action="j_spring_openid_security_check" method="post">
    	<input name="openid_identifier" size="50" maxlength="100" 
    		type="hidden" value=" https://www.google.com/accounts/o8/id" />
    	<input type="submit" value="Sign in with Google" />
    </form>
  
  </body>
</html>
