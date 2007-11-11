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
                    
                    <div class="displaytag">
                    <display:table name="participants" id="participantList" sort="list" pagesize="20" defaultsort="3" defaultorder="ascending" requestURI="participants.html" export="true">
                        <display:column title="#">${participantList_rowNum}</display:column>
                        <display:column property="firstName" titleKey="first_name" sortable="true" headerClass="sortable"/>
                        <display:column property="lastName" titleKey="last_name" sortable="true" headerClass="sortable"/>
                        <display:column property="email" titleKey="Email" sortable="true" headerClass="sortable"/>
                        <display:column property="creationDate" titleKey="JoinedAt" sortable="true" headerClass="sortable"/>
                    </display:table>
                    </div>
                    <br/>
                </div>
                <jsp:include page="../menu.jsp"/>
            </div>            
        </div>
        <jsp:include page="../footer.jsp"/>        
    </body>
</html>