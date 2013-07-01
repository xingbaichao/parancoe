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
<%@ page import="org.parancoe.util.MemoryAppender" %>
<%@ page import="org.apache.commons.configuration.Configuration" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.parancoe.web.PluginManager" %>
<%@ page import="java.util.List" %>
<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <%@ include file="../head.jspf" %>
</head>
<body>
<jsp:include page="../header.jsp"/>

<h1>Administration Console</h1>

<h2>Configuration: <%= Conf.getEnv() %>
</h2>

<table>
  <tr>
    <th>key</th>
    <th>value</th>
  </tr>
  <% Configuration c = conf.getConfiguration();
    Iterator i = c.getKeys();
    while (i.hasNext()) {
      String key = (String) i.next();
      Object value = conf.getConfiguration().getProperty(key);
  %>
  <tr>
    <td><%=key%>
    </td>
    <td><%=value%>
    </td>
  </tr>
  <% } %>
</table>

<h2>Loaded Plugins</h2>
<ol>
  <% List<String> plugins = PluginManager.availablePlugins(getServletConfig().getServletContext());
    for (String plugin : plugins) {%>
  <li><%=plugin%></li>
  <% } %>
</ol>
<jsp:include page="../footer.jsp"/>

</body>
</html>