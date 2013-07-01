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
<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>

        <script src="${cp}/dwr/interface/personBo.js" type="text/javascript"></script>
    </head>
    <body onload="parancoe.util.initDWR()">
        <jsp:include page="../header.jsp"/>
        <p:flash type="notice" />

        <h1><spring:message code="People" text="?People?"/></h1>

        <h2>List</h2>

        <ul>
            <c:forEach var="person" items="${people}">
                <li>${person.firstName} ${person.lastName}, <fmt:formatDate value="${person.birthDate}"/>
                    <a href="${cpr}/person/${person.id}">show</a>
                    <a href="${cpr}/person/${person.id}/edit">edit</a>
                    <a href="${cpr}/person/${person.id}?_method=DELETE" id="deletePerson">delete</a>
                    <a href="javascript: personBo.showPerson(${person.id})">Ajax show</a>
                </li>
            </c:forEach>
        </ul>

        <c:if test="${empty people}">
            <spring:message code="NoPerson" text="?NoPerson?"/>
        </c:if>
        <br/>

        <div id="personData" style="display: none;">
            <b><spring:message code="first_name" text="?first_name?"/>: </b><span id="firstName"></span><br/>
            <b><spring:message code="last_name" text="?last_name?"/>: </b><span id="lastName"></span><br/>
            <b><spring:message code="birth_date" text="?birth_date?"/>: </b><span id="birthDate"></span><br/>
        </div>

        <a href="${cpr}/person/new"><spring:message code="AddNewPerson" text="?AddNewPerson?"/></a>

        <a href="${cpr}/person/populate"><spring:message code="PopulateTheDatabase" text="?PopulateTheDatabase?"/></a>

        <jsp:include page="../footer.jsp"/>

    </body>
</html>