#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="common.jspf" %>
<div id="content_menu">
    <p class="menuTitle"><spring:message code="label_menu"/></p>
    <p class="menuLevel0"><a href="${symbol_dollar}{cp}/"><spring:message code="menu_home"/></a></p>
    <p class="menuLevel0"><a href="${symbol_dollar}{cp}/admin/index.html"><spring:message code="menu_administration"/></a></p>
    <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_PARANCOE">   			
        <p class="menuLevel0"><a href="${symbol_dollar}{cp}/logout.secure">Logout</a></p>
    </authz:authorize>         
</div>
