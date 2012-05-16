#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

<%@ include file="common.jspf" %>
<%@page pageEncoding="UTF-8" %>

<h3><spring:message code="About" text="?About?"/></h3>

<p class="about">
    <c:choose>
        <c:when test="${symbol_dollar}{requestScope.lang eq 'it'}">
            Qui una descrizione dell'applicazione ${parentArtifactId}.
        </c:when>
        <c:otherwise>
            Here a description of the ${parentArtifactId} application.
        </c:otherwise>
    </c:choose>
</p>
