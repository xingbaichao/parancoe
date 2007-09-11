<%@ include file="common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="head.jspf" %>
    </head>
    <body>
        <div id="nonFooter">            
            <jsp:include page="header.jsp"/>
            <div id="content"> 
                <div id="content_main">
        <h2>
            <spring:message code="attention"/>
        </h2>

        <div id="errore">
            <h3>
                <spring:message code="error"/>
            </h3>

            <p>
                <spring:message code="${requestScope.messageCode}" text="${requestScope.messageCode}"/>
            </p>

            <!--
    	<%
    	Exception e = (Exception)request.getAttribute("exception");
	    if (e != null){
	      e.printStackTrace(new java.io.PrintWriter(out));
	    }
    	%>
           -->
        </div>
                </div>
                <jsp:include page="menu.jsp"/>
            </div>
        </div>
        <jsp:include page="footer.jsp"/>        
    </body>
</html>
