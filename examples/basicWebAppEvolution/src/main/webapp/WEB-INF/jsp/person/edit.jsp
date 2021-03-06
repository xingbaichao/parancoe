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
<%--    <script type='text/javascript' src='${cp}/dwr/interface/dwrvalidator.js'></script> --%>
</head>
<body onload="parancoe.util.initDWR()">
<jsp:include page="../header.jsp"/>


<h1><spring:message code="People" text="?People?"/></h1>

<h2>Edit Person</h2>

<form:form commandName="person" method="POST" action="${cpr}/person/${person.id}">
    <form:hidden path="id"/>
    <table>
    <tr>
        <td><spring:message code="first_name"/> (*)</td>
        <td><form:input path="firstName"/></td>
<%-- TODO: DWR validation
        <td><form:input path="firstName" onchange="javascript: dwrvalidator.validateDWR('peopleEditController','firstName',firstName.value, function(str) { if(str!=null) alert(str); });"/></td>
        <td><form:errors path="firstName" cssClass="errorBox"/></td>
--%>        
        <!-- <td><input type="button" value="validazione dwr" name="validazione_dwr" onclick="javascript: dwrvalidator.validateDWR('peopleEditController','firstName',firstName.value, function(str) { alert(str); });"/></td> -->
    </tr>
    <tr>
        <td><spring:message code="last_name"/> (*)</td>
        <td><form:input path="lastName"/></td>
<%-- TODO: DWR validation
        <td><form:input path="lastName" onchange="javascript: dwrvalidator.validateDWR('peopleEditController','lastName',lastName.value, function(str) { if(str!=null) alert(str); });"/></td>
        <td><form:errors path="lastName" cssClass="errorBox"/></td>
--%>        
        <!-- <td><input type="button" value="validazione dwr" name="validazione_dwr" onclick="javascript: dwrvalidator.validateDWR('peopleEditController','lastName',lastName.value, function(str) { alert(str); });"/></td> -->
    </tr>
    <tr>
        <td><spring:message code="birth_date"/> (**)</td>
        <td><form:input path="birthDate"/></td>
<%-- TODO: DWR validation
        <td><form:input path="birthDate" onchange="javascript: dwrvalidator.validateDWR('peopleEditController','birthDate',birthDate.value, function(str) { if(str!=null) alert(str); });"/></td>
        <td><form:errors path="birthDate" cssClass="errorBox"/></td>
--%>        
        <!-- <td><input type="button" value="validazione dwr" name="validazione_dwr" onclick="javascript: dwrvalidator.validateDWR('peopleEditController','birthDate',birthDate.value, function(str) { alert(str); });"/></td> -->
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td><input type="submit" value="Submit"/><br/><br/>
        </td>
    </tr>       
    <tr>
        <td>(*): required</td>
        <td>(**): format dd/MM/yyyy</td>
    </tr>       
       
    </table>
    <form:errors path="*" cssClass="errorBox"/>
</form:form>

<jsp:include page="../footer.jsp"/>

</body>
</html>