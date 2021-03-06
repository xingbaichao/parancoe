#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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
                    <c:choose>
                        <c:when test="${symbol_dollar}{requestScope.lang eq 'it'}">
                            Questa &egrave; l'applicazione template per il framework
                            <a href="http://wwww.parancoe.org">Parancoe</a>.<br/>
                            <br/>
                            Da questo punto di partenza puoi iniziare a costruire la
                            tua nuova applicazione, sfruttando tutti i benefici che
                            derivano dall'uso di Parancoe.<br/>
                            <br/>
                            Per maggiori informazioni visita il sito di Parancoe:<br/>
                            <br/>
                            <a href="http://wwww.parancoe.org">http://wwww.parancoe.org</a>.<br/>
                        </c:when>
                        <c:otherwise>
                            This is the template application of the
                            <a href="http://wwww.parancoe.org">Parancoe</a> framework.<br/>
                            <br/>
                            From this starting point you can build your own application,
                            with all benefits of the using of the Parancoe Framework.<br/>
                            <br/>
                            For more infos, visit the Parancoe framework Web site:<br/>
                            <br/>
                            <a href="http://wwww.parancoe.org">http://wwww.parancoe.org</a>.<br/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <jsp:include page="menu.jsp"/>
            </div>
        </div>
        <jsp:include page="footer.jsp"/>        
    </body>
</html>