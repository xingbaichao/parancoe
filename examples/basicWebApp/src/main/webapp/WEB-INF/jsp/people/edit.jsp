<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="../head.jspf" %>
</head>
<body>
<jsp:include page="../header.jsp"/>

<h1>People</h1>

<h2>Edit Person</h2>

<form:form commandName="person" method="POST" action="${cp}/people/edit.form">
    <form:hidden path="id"/>
    <table>
    <tr>
        <td>First Name</td>
        <td><form:input path="firstName"/> * required</td>
    </tr>
    <tr>
        <td>Last Name</td>
        <td><form:input path="lastName"/> * required </td>
    </tr>
    <tr>
        <td>Birth Date</td>
        <td><form:input path="birthDate"/> (dd/MM/yyyy)</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td><input type="submit" value="Submit"/><br/><br/>
        </td>
    </tr>       
    </table>
    <form:errors path="*" cssClass="errorBox"/>
</form:form>

<jsp:include page="../footer.jsp"/>

</body>
</html>