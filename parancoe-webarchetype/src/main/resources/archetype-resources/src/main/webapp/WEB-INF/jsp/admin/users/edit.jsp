#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="../../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../../head.jspf" %>
    </head>
    <body>
        <div id="nonFooter">            
            <jsp:include page="../../header.jsp"/>
            <div id="content"> 
                <div id="content_main">
                    <h1>Edit user</h1>
                    <form:form commandName="userBean" method="POST" action="${symbol_dollar}{cpr}/admin/users/${symbol_dollar}{user.id}">
                        <table>
                        <tr>
                            <td>Username:</td>
                            <td><form:input path="user.username"/></td>
                        </tr>
                        <tr>
                            <td>New password:</td>
                            <td><form:input path="newPassword"/></td>
                        </tr>
                        <tr>
                            <td>Confirm password:</td>
                            <td><form:input path="confirmPassword"/></td>
                        </tr>
                        <tr>
                            <td>Authorities:</td>
                            <td>&nbsp;</td>
                        </tr>
                        <c:forEach items="${symbol_dollar}{userBean.authorityCheckBoxes}" var="authorityCheckBox" varStatus="loopStatus">
                            <tr><td>&nbsp;</td>
                                <td>
                                <spring:bind path="userBean.authorityCheckBoxes[${symbol_dollar}{loopStatus.index}].checked"> 
                                    <input type="hidden" name="_<c:out value="${symbol_dollar}{status.expression}"/>">
                                    <input type="checkbox" name="<c:out value="${symbol_dollar}{status.expression}"/>" value="true" <c:if test="${symbol_dollar}{status.value}">checked</c:if>/> ${symbol_dollar}{authorityCheckBox.authority.role}
                                </spring:bind>
                                </td>
                            </tr>
                        </c:forEach>                               
                        <tr>
                            <td>&nbsp;</td>
                            <td><input type="submit" value="Submit"/><br/><br/></td>
                        </tr>
                        </table>
                        <form:errors path="*" cssClass="errorBox"/>
                    </form:form>
                </div>
                <jsp:include page="../../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../../footer.jsp"/>        
    </body>
</html>