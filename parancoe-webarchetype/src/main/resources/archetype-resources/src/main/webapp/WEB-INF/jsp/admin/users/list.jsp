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
                    <h1>Users</h1>
                    
                    <table>
                        <c:forEach var="user" items="${symbol_dollar}{users}">
                            <tr>
                                <td>${symbol_dollar}{user.username}</td>
                                <td>
                                    <a href="${symbol_dollar}{cpr}/admin/users/${symbol_dollar}{user.id}/edit">Edit</a>
                                    <a href="${symbol_dollar}{cpr}/admin/users/${symbol_dollar}{user.id}?_method=DELETE">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <a href="${symbol_dollar}{cpr}/admin/users/new">New</a>
                    
                    <c:if test="${symbol_dollar}{empty users}">
                        No users in the DB
                    </c:if>
                </div>
                <jsp:include page="../../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../../footer.jsp"/>        
    </body>
</html>