<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
    </head>
    <body>
        <div id="nonFooter">            
            <jsp:include page="../header.jsp"/>
            <div id="content"> 
                <div id="content_main">
                    <c:choose>
                        <c:when test="${requestScope.lang eq 'it'}">                        
                            Profilo di <em>${jugger.user.username}</em> modificato con successo.                            
                        </c:when>
                        <c:otherwise>
                            Profile of <em>${jugger.user.username}</em> updated with success.                            
                        </c:otherwise>
                    </c:choose>
                </div>
                <jsp:include page="../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>        
    </body>
</html>