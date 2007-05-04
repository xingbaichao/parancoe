<%@ include file="common.jspf" %>
<div id="content_menu">
    <p class="menuTitle"><spring:message code="label_menu"/></p>
    <p class="menuLevel0"><a href="${cp}/"><spring:message code="menu_home"/></a></p>
    <p class="menuLevel0"><a href="${cp}/admin/index.html"><spring:message code="menu_administration"/></a></p>
    <p class="menuLevel0"><a href="${cp}/people/list.html"><spring:message code="menu_people"/></a></p>
    <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_PARANCOE">   			
        <p class="menuLevel0"><a href="${cp}/j_acegi_logout">Logout</a></p>
    </authz:authorize>         
</div>
