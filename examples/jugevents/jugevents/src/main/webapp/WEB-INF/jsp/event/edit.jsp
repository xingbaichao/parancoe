<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
        <script src="${cp}/dwr/interface/eventBo.js" type="text/javascript"></script>
        <script src="${cp}/dwr/interface/filterBo.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="nonFooter">
            <jsp:include page="../header.jsp"/>
            <div id="content">
                <div id="content_main">
                    <c:choose>
                        <c:when test="${empty event.id}">
                            <h1><spring:message code="NewEvent"/></h1>
                        </c:when>
                        <c:otherwise>
                            <h1><spring:message code="EditEvent"/></h1>                            
                        </c:otherwise>
                    </c:choose>
                    <form:form commandName="event" method="POST" action="${cp}/event/edit.form">
                        <form:errors path="*" cssClass="errorBox"/>
                        <form:hidden path="id"/>
                        <dl>
                            <dt><form:label path="title"><spring:message code="event.title"/></form:label></dt>
                            <dd><form:input path="title"/></dd>
                            <dt><form:label path="location"><spring:message code="event.location"/></form:label></dt>
                            <dd>
                                <form:input path="location"/><div id="locationList" class="auto_complete"></div>                                
                            </dd>
                            <dt>
                                <form:label path="directions"><spring:message code="event.directions"/></form:label><br/>
                                (<a href="http://hobix.com/textile/" target="Filter">Textile</a>)
                            </dt>
                            <dd><form:textarea path="directions" cols="38" rows="5" onkeyup="filterBo.populatePreview($(directions).value, 'Textile', 'directionsPreview')"/></dd>
                            <dt>&nbsp;</dt>
                            <dd><div id="directionsPreview" class="preview">${requestScope.event.filteredDirections}&nbsp;</div></dd>
                            <dt><form:label path="startDate"><spring:message code="event.startDate"/></form:label></dt>
                            <dd><form:input path="startDate" maxlength="10" size="10"/>&nbsp;<img src="${cp}/images/calendar.gif" alt="Calendar icon" onclick="return showCalendar('startDate');"/>&nbsp;(dd/MM/yyyy)</dd>
                            <dt><form:label path="startTime"><spring:message code="event.startTime"/></form:label></dt>
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
                            <dt><form:label path="endDate"><spring:message code="event.endDate"/></form:label></dt>
                            <dd><form:input path="endDate" maxlength="10" size="10"/>&nbsp;<img src="${cp}/images/calendar.gif" alt="Calendar icon" onclick="return showCalendar('endDate');"/>&nbsp;(dd/MM/yyyy)</dd>
                            <dt><form:label path="endTime"><spring:message code="event.endTime"/></form:label></dt>
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
                            <dt>
                                <form:label path="description"><spring:message code="event.description"/></form:label><br/>
                                (<a href="http://hobix.com/textile/" target="Filter">Textile</a>)
                            </dt>
                            <dd><form:textarea path="description" cols="38" rows="8" onkeyup="filterBo.populatePreview($(description).value, 'Textile', 'descriptionPreview')"/></dd>
                            <dt>&nbsp;</dt>
                            <dd><div id="descriptionPreview" class="preview">${requestScope.event.filteredDescription}&nbsp;</div></dd>
                            <dt>&nbsp;</dt>
                            <dd><input type="submit" value="<spring:message code='Submit'/>"/><br/><br/></dd>
                        </dl>
                    </form:form>
                </div>
                <jsp:include page="../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
        
        <script type="text/javascript">
dwr.util.setEscapeHtml(false);
            
new Autocompleter.DWR('location', 'locationList', updateLocationList, { partialChars: 0, fullSearch: true, updateElement: populateDirections });

function updateLocationList(autocompleter, token) {
    eventBo.findPartialLocation(token, '<authz:authentication operation="username"/>', function(data) {
        autocompleter.setChoices(data)
    });
}

function populateDirections(selectedElement) {
    eventBo.copyDirectionsFromEvent(selectedElement.childNodes[3].childNodes[0].nodeValue);
}
        </script>
    </body>
</html>