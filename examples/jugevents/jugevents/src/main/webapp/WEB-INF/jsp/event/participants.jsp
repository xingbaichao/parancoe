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
                    
                    <h1><spring:message code='Event'/></h1>
                    <%@ include file="show.jspf" %>
                    
                    <h2><spring:message code='ParticipantList'/></h2>
                    
                    <c:choose>
                        <c:when test="${not empty participants}">
                            <table class="dataList">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th><spring:message code='first_name'/></th>
                                        <th><spring:message code='last_name'/></th>
                                        <th><spring:message code='Email'/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="participant" items="${participants}" varStatus="status">
                                        <c:choose>
                                            <c:when test="${status.count % 2 == 0}">
                                                <c:set var="rowStyle" value="evenRow"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="rowStyle" value="oddRow"/>                                            
                                            </c:otherwise>
                                        </c:choose>
                                        <tr class="${rowStyle}">
                                            <td>${status.count}</td>
                                            <td>${participant.firstName}</td>
                                            <td>${participant.lastName}</td>
                                            <td>${participant.email}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <spring:message code='NoParticipants'/>
                        </c:otherwise>
                    </c:choose>
                    <br/>
                </div>
                <jsp:include page="../menu.jsp"/>
            </div>            
        </div>
        <jsp:include page="../footer.jsp"/>        
    </body>
</html>