<%@ page import="org.parancoe.util.MemoryAppender" %>
<%@ page import="org.apache.commons.configuration.Configuration" %>
<%@ page import="java.util.Iterator" %>
<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="../head.jspf" %>
</head>
<body>
<jsp:include page="../header.jsp"/>

<h1>People</h1>

<h2>List</h2>

<ol>
<c:forEach var="person" items="${people}">
     <li>${person}</li>
</c:forEach>
</ol>

<c:if test="${empty people}">
    Non ci sono persone a DB
</c:if>
<jsp:include page="../footer.jsp"/>

</body>
</html>