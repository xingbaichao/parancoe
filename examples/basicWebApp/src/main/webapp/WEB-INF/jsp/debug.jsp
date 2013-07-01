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
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="org.parancoe.util.MemoryAppender"%>
<a href="#debug_pane" name="debug_pane" onclick="Element.toggle('debug');"> debug </a>

<div id="debug" style="display: none;">
    <h2>Log Fragment</h2>
    <code><pre>
    <%= MemoryAppender.getLastNLines(100) %>
    </pre></code>    

    <h2>Request Params</h2>
    <table>
        <% for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            String value = request.getParameter(key);
        %>
        <tr>
            <td><%=key%>
            </td>
            <td><%=value%>
            </td>
        </tr>
        <%}%>
    </table>

    <h2>Request Attributes</h2>
    <table>
        <% for (Enumeration e = request.getAttributeNames(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            Object value = request.getAttribute(key);
        %>
        <tr>
            <td><%=key%>
            </td>
            <td><%=value%>
            </td>
        </tr>
        <%}%>
    </table>

    <h2>Session Attributes</h2>
    <table>
        <% for (Enumeration e = session.getAttributeNames(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            Object value = session.getAttribute(key);
        %>
        <tr>
            <td><%=key%>
            </td>
            <td><%=value%>
            </td>
        </tr>
        <%}%>
    </table>

    <h2>Headers</h2>
    <table><% for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();) {
        String name = (String) e.nextElement(); %>
        <tr>
            <td><%=name%></td><td><%=request.getHeader(name)%></td>
            </tr>
        <%}%>
    </table>
        <h2>Cookies</h2>
        <table>
            <% if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {%>
        <tr>
            <td><%=cookie.getName()%>
            </td>
            <td><%=cookie.getValue()%> <a href="javascript:readIt('<%=cookie.getName()%>');">read</a>
                <a href="javascript:eraseIt('<%=cookie.getName()%>');">erase</a>
            </td>
        </tr>

    <% }
    }
    %> </table>
</div>