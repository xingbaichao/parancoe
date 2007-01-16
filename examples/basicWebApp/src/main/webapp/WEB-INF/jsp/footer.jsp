<%@ include file="common.jspf"%>
<div id="footer">
	<a href="https://parancoe.dev.java.net">https://parancoe.dev.java.net</a> 
    <a href="http://www.jugpadova.it">http://www.jugpadova.it</a>

</div>

<% if (!Conf.isProduction()) { %>
    <jsp:include page="debug.jsp" />
<% } %>