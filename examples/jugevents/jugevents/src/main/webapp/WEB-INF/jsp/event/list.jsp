<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
        <link href="${cp}/event/rss.html" rel="alternate" title="RSS" type="application/rss+xml" />
    </head>
    <body>
        <div id="nonFooter">    
            <jsp:include page="../header.jsp"/>
            <div id="content"> 
                <div id="content_main">
                    
                    <h1>Event</h1>
                    
                    <h2>List <a href="${cp}/event/rss.html"><img style="vertical-align: middle; border: none;" src="${cp}/images/feed-icon-14x14.png"></a></h2>
                    
                    <c:choose>
                        <c:when test="${not empty events}">
                            <table class="dataList">
                                <thead>
                                    <tr>
                                        <th>JUG</th>
                                        <th>Event</th>
                                        <th>Start date</th>
                                        <th># Participants</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="event" items="${events}" varStatus="status">
                                        <c:choose>
                                            <c:when test="${status.count % 2 == 0}">
                                                <c:set var="rowStyle" value="evenRow"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="rowStyle" value="oddRow"/>                                            
                                            </c:otherwise>
                                        </c:choose>
                                        <tr class="${rowStyle}">
                                            <td>${event.owner.jugName}</td>
                                            <td>${event.title}</td>
                                            <td><fmt:formatDate value="${event.startDate}" /></td>
                                            <td>${event.numberOfParticipants}</td>
                                            <td class="actionColumn">
                                                <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_JUGGER">
                                                    <c:if test="${event.owner.user.username == authentication.name || authentication.authorities[0] == 'ROLE_ADMIN'}">
                                                        <a href="edit.form?id=${event.id}">edit</a>
                                                    </c:if>
                                                </authz:authorize>
                                                <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_JUGGER">   			
                                                    <c:if test="${event.owner.user.username == authentication.name || authentication.authorities[0] == 'ROLE_ADMIN'}">
                                                        <a href="delete.html?id=${event.id}">delete</a>
                                                    </c:if>
                                                </authz:authorize>
                                                <a href="registration.form?event.id=${event.id}">register</a>
                                                <authz:authorize ifAnyGranted="ROLE_ADMIN,ROLE_JUGGER">   			
                                                    <c:if test="${event.owner.user.username == authentication.name || authentication.authorities[0] == 'ROLE_ADMIN'}">
                                                        <a href="participants.html?id=${event.id}">participants</a>
                                                    </c:if>
                                                </authz:authorize>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            No active events
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