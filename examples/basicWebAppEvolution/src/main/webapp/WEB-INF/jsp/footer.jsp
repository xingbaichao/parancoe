<%@ include file="common.jspf"%>
<div id="footer">
	<a href="http://wwww.parancoe.org">http://wwww.parancoe.org</a> 
    <a href="http://www.jugpadova.it">http://www.jugpadova.it</a>

</div>

<% if (!Conf.isProduction()) { %>
    <jsp:include page="debug.jsp" />
<% } %>