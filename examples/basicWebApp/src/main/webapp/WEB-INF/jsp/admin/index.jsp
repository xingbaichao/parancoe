<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="../head.jspf" %>
</head>
<body>
<jsp:include page="../header.jsp"/>

<h1>Administration Console</h1>

   <ul>
        <li><a href="logs.html">Application logs</a></li>
        <li><a href="conf.html">App Configuration</a></li>
        <li><a href="spring.html">Spring Beans</a></li>
        <li><a href="system.html">System Properties</a></li>
        
        <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_PARANCOE">
            <li><a href="${cp}/logout.secure">Logout</a></li>
        </authz:authorize>  
    </ul>

<jsp:include page="../footer.jsp"/>

</body>
</html>