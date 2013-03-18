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
<%@ include file="common.jspf" %>
<div id="header">
    <span id="menu">
        <a href="${cp}">Home</a> |
        <a href="${cp}/admin/index.html">Admin</a> |
        <a href="${cp}/people/list.html">People</a> |
        <a href="${cp}/italy/index.html">Italy Plugin</a> |
        <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_PARANCOE">
            <a href="${cp}/logout.secure">Logout</a>
        </authz:authorize>    
         
    </span>
    <jsp:include page="language.jsp"/>
</div>
