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
<%@ include file="common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="head.jspf" %>
</head>
<body>
<jsp:include page="header.jsp"/>

<h1><spring:message code="slogan"/></h1>
<h2>the easy to use web framework</h2>

<p>
Parancoe is a project aiming to simplify the release of web applications promoting the convention over configuration philosophy and the DRY principle. This project is promoted by the Jug Padova, and everybody can partecipate.
</p>

<p>Parancoe purpose is to give to developers a set of libraries ready to build standard web applications (which in most cases are just crud applications) without worrying of long and harmful configurations files. Parancoe will be composed of a full MVC stack.
</p>

<p>Parancoe is open source and uses ASL as licence.</p>

<p>The guidelines are very simple.</p>

<p>Are you interested in the parancoe word meaning? A "parancoa" is a scaffolding in the dialect of the Venice (Italy) area.
</p>

<jsp:include page="footer.jsp"/>

</body>
</html>