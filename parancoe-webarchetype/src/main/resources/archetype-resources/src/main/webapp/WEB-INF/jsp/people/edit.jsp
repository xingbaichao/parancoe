<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
    </head>
    <body>
        <div id="nonFooter">    
            <jsp:include page="../header.jsp"/>
            
            <h1>People</h1>
            
            <h2>Edit Person</h2>
            
            <form:form commandName="person" method="POST" action="${cp}/people/edit.form">
                <form:hidden path="id"/>
                <table>
                    <tr>
                        <td>First Name (*)</td>
                        <td><form:input path="firstName"/></td>
                        <td><form:errors path="firstName" cssClass="errorBox"/></td>
                    </tr>
                    <tr>
                        <td>Last Name (*)</td>
                        <td><form:input path="lastName"/></td>
                        <td><form:errors path="lastName" cssClass="errorBox"/></td>
                    </tr>
                    <tr>
                        <td>Birth Date (**)</td>
                        <td><form:input path="birthDate"/></td>
                        <td><form:errors path="birthDate" cssClass="errorBox"/></td>
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
                <!-- <form:errors path="*" cssClass="errorBox"/> -->
            </form:form>
        </div>
        <jsp:include page="../footer.jsp"/>
        
    </body>
</html>