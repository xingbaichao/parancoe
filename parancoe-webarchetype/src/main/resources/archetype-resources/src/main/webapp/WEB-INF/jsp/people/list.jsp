<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
    </head>
    <body>
        <div id="nonFooter">    
            <jsp:include page="../header.jsp"/>
            
            <h1>People</h1>
            
            <h2>List</h2>
            
            <ul>
                <c:forEach var="person" items="${people}">
                    <li>${person}
                        <a href="show.html?id=${person.id}">show</a>
                        <a href="edit.form?id=${person.id}">edit</a>           
                    </li>
                </c:forEach>
            </ul>
            
            <c:if test="${empty people}">
                <spring:message code="people_empty_db"/>
            </c:if>
            <br/>
            
            <a href="${cp}/people/edit.form"><spring:message code="people_add_new"/></a>
            
            <a href="${cp}/people/populate.html"><spring:message code="people_populate"/></a>
        </div>
        <jsp:include page="../footer.jsp"/>
        
    </body>
</html>