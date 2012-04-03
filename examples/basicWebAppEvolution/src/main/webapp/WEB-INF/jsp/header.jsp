<%@ include file="common.jspf" %>
<div id="header">
    <span id="menu">
        <a href="${cp}">Home</a> |
        <a href="${cp}/admin/index.html">Admin</a> |
        <a href="${cpr}/person">People</a> |
        <a href="${cp}/italy/index.html">Italy Plugin</a> |
        <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_USER">
            <a href="${cp}/logout.html">Logout</a>
        </authz:authorize>    
         
    </span>
    <jsp:include page="language.jsp"/>
</div>
