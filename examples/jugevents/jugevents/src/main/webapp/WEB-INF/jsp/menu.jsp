<%@ include file="common.jspf" %>
<div id="content_menu">
    <p class="menuTitle"><spring:message code="label_menu"/></p>
    <p class="menuLevel0"><a href="${cp}/event/search.form"><spring:message code="Search"/></a></p>
    <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_JUGGER">   			
        <p class="menuLevel0"><a href="${cp}/event/edit.form"><spring:message code="NewEvent"/></a></p>
    </authz:authorize>
    <authz:authorize ifAnyGranted="ROLE_ADMIN">   			
        <p class="menuLevel0"><a href="${cp}/admin/index.html"><spring:message code="menu_administration"/></a></p>
    </authz:authorize>         
    <authz:authorize ifAnyGranted="ROLE_ADMIN">   			
        <p class="menuLevel0"><a href="${cp}/adminjugger/list.html">Juggers List</a></p>
    </authz:authorize>
    <authz:authorize ifNotGranted="ROLE_ADMIN,ROLE_PARANCOE,ROLE_JUGGER">
        <p class="menuLevel0"><a href="${cp}/jugger/registration.form"><spring:message code="newjugger"/></a></p>
    </authz:authorize>
    <authz:authorize ifNotGranted="ROLE_ADMIN,ROLE_PARANCOE,ROLE_JUGGER">
        <p class="menuLevel0"><a href="${cp}/login.secure"><spring:message code="Login"/></a></p>
    </authz:authorize>
    <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_PARANCOE,ROLE_JUGGER">   			
        <p class="menuLevel0"><a href="${cp}/logout.secure">Logout</a></p>
        <br/>
        <spring:message code="Welcome"/> <b><authz:authentication operation="username"/></b>
    </authz:authorize>         
</div>
