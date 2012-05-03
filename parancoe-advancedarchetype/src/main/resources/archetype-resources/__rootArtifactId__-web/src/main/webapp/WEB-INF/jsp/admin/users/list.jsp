#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

<%@ include file="../../common.jspf" %>
<h1><spring:message code="users" text="?users?"/></h1>
<div><a href="${symbol_dollar}{cpr}/admin/users/new" class="action action-add"><spring:message code="newUser" text="?newUser?"/></a></div>
<div class="displaytag">
    <display:table  id="user"  name="users" sort="list" pagesize="20" defaultsort="5" defaultorder="ascending" requestURI="" >
        <!--  name="sessionScope.juggers" -->
        <display:column titleKey="username" sortable="true">
            <a	href="${symbol_dollar}{cpr}/admin/users/${symbol_dollar}{user.id}/edit">${symbol_dollar}{user.username}</a>
        </display:column>
        <display:column titleKey="lastName" sortable="true">
            <c:out value="${symbol_dollar}{user.lastName}" />
        </display:column>
        <display:column titleKey="firstName" sortable="true">
            <c:out value="${symbol_dollar}{user.firstName}" />
        </display:column>
        <display:column titleKey="actions" class="actionColumn" >
            <a href="${symbol_dollar}{cpr}/admin/users/${symbol_dollar}{user.id}/edit" class="action action-edit"><spring:message code="edit" text="?edit?"/></a>
            <spring:message code='confirmDeleteUser' text="?confirmDeleteUser?" var="confirmDeleteUserMessage"/>
            <a href="${symbol_dollar}{cpr}/admin/users/${symbol_dollar}{user.id}?_method=DELETE" class="action action-delete" onclick="return confirm('${symbol_dollar}{confirmDeleteUserMessage}')"><spring:message code="delete" text="?delete?"/></a>
        </display:column>

    </display:table>
</div>
