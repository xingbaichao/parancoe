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
List<String> paramKeyValuesList = Utils.convertToNameValueList(tmp, true);
String queryString = "?" + StringUtils.join(paramKeyValuesList.iterator(), "&");
%>
<span id="language">
    <!-- lang: ${requestScope.lang} -->
    <c:forEach var="supportedLanguage" items="${conf.supportedLanguages}">
        <a href="<%=queryString%>&language=${supportedLanguage}" title="${supportedLanguage}">
            <c:choose>
                <c:when test="${requestScope.lang eq supportedLanguage}">
                    <b><spring:message code="${supportedLanguage}"/></b>                
                </c:when>
                <c:otherwise>
                    <spring:message code="${supportedLanguage}"/>                
                </c:otherwise>
            </c:choose>
        </a>
    </c:forEach>
</span>