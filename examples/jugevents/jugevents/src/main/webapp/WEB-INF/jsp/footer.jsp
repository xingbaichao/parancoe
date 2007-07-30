<%@ include file="common.jspf"%>
<div id="footer">
    <div id="validators">&nbsp;</div>
    <div id="copyright">&#169; 2007, JUG Events Team<br/><a href="mailto:info@jugpadova.it">info@jugpadova.it</a></div>
    <div id="references"><a href="http://www.parancoe.org"><img src="${cp}/images/powered_parancoe.png" alt="Parancoe powered" border="0"/></a></div>
</div>

<% if (!BaseConf.isProduction()) { %>
<jsp:include page="debug.jsp" />
<% } %>