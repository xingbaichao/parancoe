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
            
            <h2>Show Person</h2>
            
            <c:set var="person" value="${requestScope.person}"/>
            
            First Name: ${person.firstName} <br/>
            Last Name: ${person.lastName} <br/>
            Birth Date: ${person.birthDate} <br/>
        </div>
        <jsp:include page="../footer.jsp"/>
        
    </body>
</html>