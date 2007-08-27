<%@ include file="common.jspf"%>
<script language="JavaScript">
function Open_Credits(xxx){
			window.open(xxx, "", "toolbar=no, location=no, directories=no, status=no, menubar=no, resizable=no, scrollbars=no, left=50, top=50, height=200, width=400");
}
</script>
<div id="footer">
    <div id="validators">&nbsp;</div>
    <div id="copyright">&#169; 2007, <a href="javascript:Open_Credits('${cp}/credits.jsp')">JUG Events Team</a> <br/><a href="mailto:info@jugpadova.it">info@jugpadova.it</a></div>
    <div id="references"><a href="http://www.parancoe.org"><img src="${cp}/images/powered_parancoe.png" alt="Parancoe powered" border="0"/></a></div>
</div>

<% if (!BaseConf.isProduction()) { %>
<jsp:include page="debug.jsp" />
<% } %>