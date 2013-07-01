<%--

    Copyright (C) 2006-2013 The Parancoe Team <info@parancoe.org>

    This file is part of Parancoe Example - Basic WebApp Evolution.

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
<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="../head.jspf" %>
</head>
<body>
<jsp:include page="../header.jsp"/>

<h1>Administration Console</h1>

<h2>Spring Beans</h2>

<ul>
    <%  String[] names = ctx.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
    %> <li><%= name%></li>
    <% } %>
</ul>
<jsp:include page="../footer.jsp"/>

</body>
</html>