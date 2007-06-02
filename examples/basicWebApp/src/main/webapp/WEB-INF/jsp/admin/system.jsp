<%@ page import="org.parancoe.util.MemoryAppender" %>
<%@ page import="org.apache.commons.configuration.Configuration" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.parancoe.web.PluginManager" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang.SystemUtils" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.io.PrintWriter" %>
<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <%@ include file="../head.jspf" %>
</head>
<body>
<jsp:include page="../header.jsp"/>

<h1>Administration Console</h1>

<h2>System Properties:%></h2>

<pre xml:space="preserve">
  <% Properties props = System.getProperties();
     props.list(new PrintWriter(out)); %>
</pre>

<table>
  <tr>
    <td>JAVA_CLASS_PATH</td>
    <td><%= SystemUtils.JAVA_CLASS_PATH %></td>
  </tr>
</table>
<jsp:include page="../footer.jsp"/>

</body>
</html>