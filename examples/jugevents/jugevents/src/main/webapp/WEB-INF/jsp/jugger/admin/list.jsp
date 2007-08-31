<%@ include file="../../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../../head.jspf" %>
    </head>
    <body>
        <div id="nonFooter">    
            <jsp:include page="../header.jsp"/>
            <div id="content"> 
                <div id="content_main">
                    
                    <h1>Juggers List</h1>     
                   
                    
                    <c:choose>
                        <c:when test="${not empty juggers}">
                            <table class="dataList">
                                <thead>
                                    <tr>
                                        <th>username</th>                                        
                                        <th>country</th>
                                        <th>jugName</th>
                                        <th>enabled/disabled</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="jugger" items="${juggers}" varStatus="status">
                                        <c:choose>
                                            <c:when test="${status.count % 2 == 0}">
                                                <c:set var="rowStyle" value="evenRow"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="rowStyle" value="oddRow"/>                                            
                                            </c:otherwise>
                                        </c:choose>
                                        <tr class="${rowStyle}">
                                            <td><a href="${cp}/adminjugger/viewJugger.html?username=${jugger.user.username}">${jugger.user.username}</a></td>
                                            <td>${jugger.jug.country.englishName}</td>
                                            <td>${jugger.jug.name}</td>
                                            <td>
                                            <c:choose>
                                             <c:when test="${jugger.user.enabled}">
                                             <a href="${cp}/adminjugger/disableJugger.html?username=${jugger.user.username}">disable</a>
                                             </c:when>
                                             <c:otherwise>
                                              <a href="${cp}/adminjugger/enableJugger.html?username=${jugger.user.username}">enable</a>
                                             </c:otherwise>     
                                             </c:choose>      
                                            </td>             
                                         </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            No active Juggers
                        </c:otherwise>
                    </c:choose>
                    <br/>
                </div>
                <jsp:include page="../../menu.jsp"/>
            </div>            
        </div>
        <jsp:include page="../../footer.jsp"/>        
    </body>
</html>
