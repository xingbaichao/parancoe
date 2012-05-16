#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

<%@ include file="common.jspf" %>
<ul id="nav">
    <li><a href="${symbol_dollar}{cp}/"><spring:message code="menu_home"/></a></li>
        <authz:authorize ifAnyGranted="ROLE_ADMIN">
        <li><a href="${symbol_dollar}{cp}/admin/index.html"><spring:message code="menu_administration" text="?menu_administration?"/></a></li>
        </authz:authorize>
</ul>
