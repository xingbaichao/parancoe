#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ include file="common.jspf" %>
<div id="header">
    <div id="logo">
        <a href="${symbol_dollar}{cp}/"><img src="${symbol_dollar}{cp}/images/applicationLogo.png" alt="${artifactId} Logo" border="0" /></a>
    </div>
    <div id="motto"><spring:message code="slogan" text="?slogan?"/></div>
    <div id="languages"><jsp:include page="language.jsp"/></div>
</div>
