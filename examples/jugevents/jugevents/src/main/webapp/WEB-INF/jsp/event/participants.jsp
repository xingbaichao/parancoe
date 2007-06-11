<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
        <script src="${cp}/dwr/interface/personBO.js" type="text/javascript"></script>
    </head>
    <body onload="parancoe.util.initDWR()">
        <div id="nonFooter">    
            <jsp:include page="../header.jsp"/>
            <div id="content"> 
                <div id="content_main">
                    
                    <h1>Event</h1>
                    
                    <h2>List of Participants</h2>
                    <strong>Title:</strong> ${event.title}<br/>
                    <strong>Start date:</strong> <fmt:formatDate value="${event.startDate}" />
                    <strong>Start time:</strong> ${event.startTime}<br/>
                    <strong>Start beer:</strong> ${event.endDate} (${event.endTime})<br/>
                    <br/>
                    ${event.description}<br/>
                    <br/>
                    
                    <c:choose>
                        <c:when test="${not empty event.participants}">
                            <table class="dataList">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>E-Mail</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="participant" items="${event.participants}" varStatus="status">
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
                            No participants
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