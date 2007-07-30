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
                    
                    <h1><spring:message code="Events"/></h1>
                    
                    <h2><spring:message code="NewEditEvent"/></h2>
                    
                    <form:form commandName="event" method="POST" action="${cp}/event/edit.form">
                        <form:hidden path="id"/>
                        <dl>
                            <dt><spring:message code="event.title"/></dt>
                            <dd><form:input path="title"/></dd>
                            <dt><spring:message code="event.startDate"/></dt>
                            <dd><form:input path="startDate" maxlength="10" size="10"/>&nbsp;<img src="${cp}/images/calendar.gif" alt="Calendar icon" onclick="return showCalendar('startDate');"/></dd>
                            <dt><spring:message code="event.startTime"/></dt>
                            <dd>
                                <form:select path="startTime">
                                    <form:option value="00:00 AM" label="00:00 AM"/>
                                    <form:option value="00:30 AM" label="00:30 AM"/>
                                    <form:option value="01:00 AM" label="01:00 AM"/>
                                    <form:option value="01:30 AM" label="01:30 AM"/>
                                    <form:option value="02:00 AM" label="02:00 AM"/>
                                    <form:option value="02:30 AM" label="02:30 AM"/>
                                    <form:option value="03:00 AM" label="03:00 AM"/>
                                    <form:option value="03:30 AM" label="03:30 AM"/>
                                    <form:option value="04:00 AM" label="04:00 AM"/>
                                    <form:option value="04:30 AM" label="04:30 AM"/>
                                    <form:option value="05:00 AM" label="05:00 AM"/>
                                    <form:option value="05:30 AM" label="05:30 AM"/>
                                    <form:option value="06:00 AM" label="06:00 AM"/>
                                    <form:option value="06:30 AM" label="06:30 AM"/>
                                    <form:option value="07:00 AM" label="07:00 AM"/>
                                    <form:option value="07:30 AM" label="07:30 AM"/>
                                    <form:option value="08:00 AM" label="08:00 AM"/>
                                    <form:option value="08:30 AM" label="08:30 AM"/>
                                    <form:option value="09:00 AM" label="09:00 AM"/>
                                    <form:option value="09:30 AM" label="09:30 AM"/>
                                    <form:option value="10:00 AM" label="10:00 AM"/>
                                    <form:option value="10:30 AM" label="10:30 AM"/>
                                    <form:option value="11:00 AM" label="11:00 AM"/>
                                    <form:option value="11:30 AM" label="11:30 AM"/>
                                    <form:option value="12:00 AM" label="12:00 AM"/>
                                    <form:option value="12:30 AM" label="12:30 AM"/>
                                    <form:option value="01:00 PM" label="01:00 PM"/>
                                    <form:option value="01:30 PM" label="01:30 PM"/>
                                    <form:option value="02:00 PM" label="02:00 PM"/>
                                    <form:option value="02:30 PM" label="02:30 PM"/>
                                    <form:option value="03:00 PM" label="03:00 PM"/>
                                    <form:option value="03:30 PM" label="03:30 PM"/>
                                    <form:option value="04:00 PM" label="04:00 PM"/>
                                    <form:option value="04:30 PM" label="04:30 PM"/>
                                    <form:option value="05:00 PM" label="05:00 PM"/>
                                    <form:option value="05:30 PM" label="05:30 PM"/>
                                    <form:option value="06:00 PM" label="06:00 PM"/>
                                    <form:option value="06:30 PM" label="06:30 PM"/>
                                    <form:option value="07:00 PM" label="07:00 PM"/>
                                    <form:option value="07:30 PM" label="07:30 PM"/>
                                    <form:option value="08:00 PM" label="08:00 PM"/>
                                    <form:option value="08:30 PM" label="08:30 PM"/>
                                    <form:option value="09:00 PM" label="09:00 PM"/>
                                    <form:option value="09:30 PM" label="09:30 PM"/>
                                    <form:option value="10:00 PM" label="10:00 PM"/>
                                    <form:option value="10:30 PM" label="10:30 PM"/>
                                    <form:option value="11:00 PM" label="11:00 PM"/>
                                    <form:option value="11:30 PM" label="11:30 PM"/>
                                </form:select>
                            </dd>
                            <dt><spring:message code="event.endDate"/></dt>
                            <dd><form:input path="endDate" maxlength="10" size="10"/>&nbsp;<img src="${cp}/images/calendar.gif" alt="Calendar icon" onclick="return showCalendar('endDate');"/></dd>
                            <dt><spring:message code="event.endTime"/></dt>
                            <dd>
                                <form:select path="endTime">
                                    <form:option value="00:00 AM" label="00:00 AM"/>
                                    <form:option value="00:30 AM" label="00:30 AM"/>
                                    <form:option value="01:00 AM" label="01:00 AM"/>
                                    <form:option value="01:30 AM" label="01:30 AM"/>
                                    <form:option value="02:00 AM" label="02:00 AM"/>
                                    <form:option value="02:30 AM" label="02:30 AM"/>
                                    <form:option value="03:00 AM" label="03:00 AM"/>
                                    <form:option value="03:30 AM" label="03:30 AM"/>
                                    <form:option value="04:00 AM" label="04:00 AM"/>
                                    <form:option value="04:30 AM" label="04:30 AM"/>
                                    <form:option value="05:00 AM" label="05:00 AM"/>
                                    <form:option value="05:30 AM" label="05:30 AM"/>
                                    <form:option value="06:00 AM" label="06:00 AM"/>
                                    <form:option value="06:30 AM" label="06:30 AM"/>
                                    <form:option value="07:00 AM" label="07:00 AM"/>
                                    <form:option value="07:30 AM" label="07:30 AM"/>
                                    <form:option value="08:00 AM" label="08:00 AM"/>
                                    <form:option value="08:30 AM" label="08:30 AM"/>
                                    <form:option value="09:00 AM" label="09:00 AM"/>
                                    <form:option value="09:30 AM" label="09:30 AM"/>
                                    <form:option value="10:00 AM" label="10:00 AM"/>
                                    <form:option value="10:30 AM" label="10:30 AM"/>
                                    <form:option value="11:00 AM" label="11:00 AM"/>
                                    <form:option value="11:30 AM" label="11:30 AM"/>
                                    <form:option value="12:00 AM" label="12:00 AM"/>
                                    <form:option value="12:30 AM" label="12:30 AM"/>
                                    <form:option value="01:00 PM" label="01:00 PM"/>
                                    <form:option value="01:30 PM" label="01:30 PM"/>
                                    <form:option value="02:00 PM" label="02:00 PM"/>
                                    <form:option value="02:30 PM" label="02:30 PM"/>
                                    <form:option value="03:00 PM" label="03:00 PM"/>
                                    <form:option value="03:30 PM" label="03:30 PM"/>
                                    <form:option value="04:00 PM" label="04:00 PM"/>
                                    <form:option value="04:30 PM" label="04:30 PM"/>
                                    <form:option value="05:00 PM" label="05:00 PM"/>
                                    <form:option value="05:30 PM" label="05:30 PM"/>
                                    <form:option value="06:00 PM" label="06:00 PM"/>
                                    <form:option value="06:30 PM" label="06:30 PM"/>
                                    <form:option value="07:00 PM" label="07:00 PM"/>
                                    <form:option value="07:30 PM" label="07:30 PM"/>
                                    <form:option value="08:00 PM" label="08:00 PM"/>
                                    <form:option value="08:30 PM" label="08:30 PM"/>
                                    <form:option value="09:00 PM" label="09:00 PM"/>
                                    <form:option value="09:30 PM" label="09:30 PM"/>
                                    <form:option value="10:00 PM" label="10:00 PM"/>
                                    <form:option value="10:30 PM" label="10:30 PM"/>
                                    <form:option value="11:00 PM" label="11:00 PM"/>
                                    <form:option value="11:30 PM" label="11:30 PM"/>
                                </form:select>
                            </dd>
                            <dt><spring:message code="event.location"/></dt>
                            <dd><form:input path="location"/></dd>
                            <dt><spring:message code="event.description"/></dt>
                            <dd><form:textarea path="description" cols="40" rows="5"/></dd>
                            <dt>&nbsp;</dt>
                            <dd><input type="submit" value="<spring:message code='Submit'/>"/><br/><br/></dd>
                        </dl>
                        <form:errors path="*" cssClass="errorBox"/>
                    </form:form>
                </div>
                <jsp:include page="../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </body>
</html>