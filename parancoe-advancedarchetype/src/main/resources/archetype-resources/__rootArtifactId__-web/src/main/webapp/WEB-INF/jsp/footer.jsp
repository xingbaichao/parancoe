#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

<%@ include file="common.jspf"%>
<div id="footer">
    <div id="validators">&nbsp;</div>
    <div id="copyright">&${symbol_pound}169; 2010, ${symbol_dollar}{url}<br/><a href="mailto:info@${parentArtifactId}.com">info@${parentArtifactId}.com</a></div>
    <div id="references"><a href="http://wwww.parancoe.org"><img src="${symbol_dollar}{cp}/images/powered_parancoe.png" alt="Parancoe powered" border="0"/></a></div>
</div>

<% if (!BaseConf.isProduction()) { %>
<jsp:include page="debug.jsp" />
<% } %>