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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="common.jspf" %>
<%@ page import="java.io.PrintWriter" %>
<head>
    <%@ include file="head.jspf" %>
</head>
<body onload="new DepSelectOne(document.forms['cercacciaa'].elements['regione'], 'provincia');">
<div id="stage">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <h2>
            <spring:message code="attention"/>
        </h2>

        <div id="errore">
            <h3>
                <spring:message code="error"/>
            </h3>

            <p>
                <spring:message code="${requestScope.messageCode}" text="${requestScope.messageCode}"/>
            </p>

            <!--
    	<%
    	Exception e = (Exception)request.getAttribute("exception");
	    if (e != null){
	      e.printStackTrace(new PrintWriter(out));
	    }
    	%>
           -->
        </div>
    </div>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>

