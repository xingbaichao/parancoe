<%@ include file="common.jspf" %>
<div id="content_menu">
    <p class="menuTitle"><spring:message code="label_menu"/></p>
    <p class="menuLevel0"><a href="${cp}/"><spring:message code="menu_home"/></a></p>
    <p class="menuLevel0"><a href="${cp}/event/list.html"><spring:message code="Events"/></a></p>
    <p class="menuLevel0"><a href="${cp}/event/edit.form"><spring:message code="NewEvent"/></a></p>
    <p class="menuLevel0"><a href="${cp}/admin/index.html"><spring:message code="menu_administration"/></a></p>
    <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_PARANCOE">   			
        <p class="menuLevel0"><a href="${cp}/logout.secure"><spring:message code="Logout"/></a></p>
    </authz:authorize>         
</div>
