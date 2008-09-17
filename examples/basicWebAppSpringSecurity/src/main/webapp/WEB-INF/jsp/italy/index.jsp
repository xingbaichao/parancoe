<%@ page import="java.io.PrintWriter" %>
<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <%@ include file="../head.jspf" %>
</head>
<body>
<jsp:include page="../header.jsp"/>
<% try { %>
ComuneDao: <%= ctx.getBean("comuneDao") %>
<% } catch (Exception e) { %>
<pre xml:space="preserve">
    <% e.printStackTrace(new PrintWriter(out)); %>
</pre>
<% } %>
</body>
</html>