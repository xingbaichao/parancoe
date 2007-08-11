<%@ include file="../../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../../head.jspf" %>
    </head>
    <body>
        <div id="nonFooter">            
            <jsp:include page="../../header.jsp"/>
            <div id="content"> 
                <div id="content_main">
                    <c:choose>
                        <c:when test="${requestScope.lang eq 'it'}">
                            Grazie per esserti registrato all'evento &quot;${participant.event.title}&quot;.<br/>
                            <h2>Per completare la registrazione, devi confermarla!</h2>
                            Ti &egrave; stata spedita una mail all'indirizzo ${participant.email} con
                            le istruzioni per confermare la registrazione.
                        </c:when>
                        <c:otherwise>
                            Thank you for registering to the event &quot;${participant.event.title}&quot;.<br/>
                            <h2>For completing your registration, You have to confirm it.</h2>
                            An e-mail has been sent to your address ${participant.email} with the
                            instructions for confirming your registration.
                        </c:otherwise>
                    </c:choose>
                </div>
                <jsp:include page="../../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../../footer.jsp"/>        
    </body>
</html>