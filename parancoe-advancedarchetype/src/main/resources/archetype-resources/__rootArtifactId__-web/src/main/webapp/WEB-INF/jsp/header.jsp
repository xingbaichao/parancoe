#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

<%@ include file="common.jspf" %>
<div style="float: right; height: auto!important; min-height: 79px;">
    <div id="languages"><jsp:include page="language.jsp"/></div>
</div>
<h1 id="logo"><a href="${symbol_dollar}{cp}/">${parentArtifactId}</a></h1>
<div style="clear: both; height: auto!important; min-height: 24px;">
<!--    <h3 style="float: left;"><spring:message code="slogan" text="?slogan?"/></h3> -->
    <div id="profile">
        <authz:authorize ifNotGranted="ROLE_ADMIN,ROLE_PARANCOE">
        <a href="${symbol_dollar}{cp}/login.secure"><spring:message code="Login" text="?Login?"/></a>
        </authz:authorize>
        <authz:authorize ifAnyGranted="ROLE_PARANCOE,ROLE_ADMIN">            
            <span class="strong"><authz:authentication property="principal.exposedName"/></span><br/>
            <span class="smallText"><a href="${symbol_dollar}{cpr}/profile/edit"><spring:message code="YourProfile" text="?YourProfile?"/></a></span> |
            <span class="smallText"><a href="${symbol_dollar}{cp}/logout.secure"><spring:message code="Logout" text="?Logout?"/></a></span>
        </authz:authorize>
    </div>
</div>
<div style="clear: both; line-height: 1px; font-size: 1px;">&nbsp;</div>
