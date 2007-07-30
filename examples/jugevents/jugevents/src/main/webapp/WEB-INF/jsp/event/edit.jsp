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
                    
                    <h1>Events</h1>
                    
                    <h2>New/Edit Event</h2>
                    
                    <form:form commandName="event" method="POST" action="${cp}/event/edit.form">
                        <form:hidden path="id"/>
                        <table>
                            <tr>
                                <td>Title</td>
                                <td><form:input path="title"/></td>
                            </tr>
                            <tr>
                                <td>Start date (dd/MM/yyyy)</td>
                                <td><form:input path="startDate"/>&nbsp;<img src="${cp}/images/calendar.gif" alt="Calendar icon" onclick="return showCalendar('startDate');"/></td>
                            </tr>
                            <tr>
                                <td>Start time</td>
                                <td><form:input path="startTime"/></td>
                            </tr>
                            <tr>
                                <td>Start beer date (dd/MM/yyyy)</td>
                                <td><form:input path="endDate"/>&nbsp;<img src="${cp}/images/calendar.gif" alt="Calendar icon" onclick="return showCalendar('endDate');"/></td>
                            </tr>
                            <tr>
                                <td>Start beer time</td>
                                <td><form:input path="endTime"/></td>
                            </tr>
                            <tr>
                                <td>Location</td>
                                <td><form:input path="location"/></td>
                            </tr>
                            <tr>
                                <td>Description</td>
                                <td><form:textarea path="description" cols="40" rows="5"/></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td><input type="submit" value="Submit"/><br/><br/></td>
                            </tr>       
                        </table>
                        <form:errors path="*" cssClass="errorBox"/>
                    </form:form>
                </div>
                <jsp:include page="../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>        
    </body>
</html>