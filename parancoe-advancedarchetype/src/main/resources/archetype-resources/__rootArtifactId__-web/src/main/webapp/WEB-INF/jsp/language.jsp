#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

<%@ include file="common.jspf" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.parancoe.util.Utils" %>
<%
Map paramMap = request.getParameterMap();
Map tmp = new HashMap(paramMap);
tmp.remove("language");
tmp.put("null", "null");
List<String> paramKeyValuesList = Utils.convertToNameValueList(tmp, true);
String queryString = "?" + StringUtils.join(paramKeyValuesList.iterator(), "&amp;");
%>
<span id="language">
    <!-- lang: ${symbol_dollar}{requestScope.lang} -->
    <!-- locale: ${symbol_dollar}{requestScope.requestContext.locale} -->
    <c:forEach var="supportedLanguage" items="${symbol_dollar}{conf.supportedLanguages}" varStatus="status">
        <a href="<%=queryString%>&amp;language=${symbol_dollar}{supportedLanguage}" title="${symbol_dollar}{supportedLanguage}">
            <c:choose>
                <c:when test="${symbol_dollar}{requestScope.requestContext.locale eq supportedLanguage}">
                    <b><spring:message code="${symbol_dollar}{supportedLanguage}" text="?${symbol_dollar}{supportedLanguage}?"/></b>                
                </c:when>
                <c:otherwise>
                    <spring:message code="${symbol_dollar}{supportedLanguage}" text="?${symbol_dollar}{supportedLanguage}?"/>                
                </c:otherwise>
            </c:choose>
        </a>
        <c:if test="${symbol_dollar}{status.count % 2 == 0}"><br/></c:if>
    </c:forEach>
</span>
