<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
        <link href="${cp}/event/rss.html?continent=${eventSearch.continent}&country=${eventSearch.country}&jugName=${eventSearch.jugName}&pastEvents=${eventSearch.pastEvents}" rel="alternate" title="RSS" type="application/rss+xml" />
        <script src="${cp}/dwr/interface/juggerBo.js" type="text/javascript"></script>
        <script src="${cp}/dwr/interface/eventBo.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="nonFooter">
            <jsp:include page="../header.jsp"/>
            <div id="content">
                <div id="content_main">
                    
                    <h1>Search Events <a href="${cp}/event/rss.html?continent=${eventSearch.continent}&country=${eventSearch.country}&jugName=${eventSearch.jugName}&pastEvents=${eventSearch.pastEvents}"><img style="vertical-align: middle; border: none;" src="${cp}/images/feed-icon-14x14.png"></a></h1>
                    <a href="#" onclick="updateBadge(); $('webBadge').show(); new Effect.ScrollTo('webBadge', {offset: -24}); return false;"><spring:message code="GetBadgeLink"/></a>
                    <form:form commandName="eventSearch" method="POST" action="${cp}/event/search.form">
                        <fieldset>
                            <legend><spring:message code='Search'/></legend>
                            <dl>
                                <dt><form:label path="continent"><spring:message code="search.continent"/>:</form:label></dt>
                                <dd><form:input path="continent"/><div id="continentList" class="auto_complete"></div></dd>
                                <dt><form:label path="country"><spring:message code="search.country"/>:</form:label></dt>
                                <dd><form:input path="country"/><div id="countryList" class="auto_complete"></div></dd>
                                <dt><form:label path="jugName"><spring:message code="search.jugName"/>:</form:label></dt>
                                <dd><form:input path="jugName"/><div id="jugNameList" class="auto_complete"></div></dd>
                                <dt><form:label path="pastEvents"><spring:message code="search.pastEvents"/>:</form:label></dt>
                                <dd><form:checkbox path="pastEvents" id="pastEvents"/></dd>
                                <dt>&nbsp;</dt><dd><input type="submit" value="<spring:message code='Search'/>"/></dd>
                            </dl>
                        </fieldset>
                    </form:form>
                    
                    <c:choose>
                        <c:when test="${not empty events}">
                            <table class="dataList">
                                <thead>
                                    <tr>
                                        <th>JUG</th>
                                        <th>Event</th>
                                        <th>Start date</th>
                                        <th>#</th>
                                        <th style="width: 30px;">&nbsp;</th>
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
                                            <td>${event.owner.jug.name}</td>
                                            <td><a href="${cp}/event/show.html?id=${event.id}">${event.title}</a></td>
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
                            <c:if test="${not empty requestScope.showNoResultsMessage}">
                                <spring:message code="NoResults"/>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                    <br/>
                    <div id="webBadge" style="display: none;">
                        <fieldset>
                            <legend><spring:message code='WebBadge'/></legend>
                            <a href="#" onclick="$('webBadge').hide(); return false;"><spring:message code="CloseBadgePanel"/></a>
                            <dl>
                                <dt><label><spring:message code="IncludeTheDescription"/>:</label></dt>
                                <dd><input type="checkbox" name="badgeIncludeTheDescription" id="badgeIncludeTheDescription" onchange="updateBadge()"/></dd>
                                <dt><label><spring:message code="Style"/>:</label></dt>
                                <dd><input type="radio" name="badgeStyle" value="none" checked="true" onchange="updateBadge()"/><spring:message code='None'/>&nbsp;<input type="radio" name="badgeStyle" value="simple" onchange="updateBadge()"/><spring:message code='Simple'/></dd>
                                <dt><label><spring:message code="badgeCopyThisCode"/></label></dt>
                                <dd><textarea name="badgeCode" cols="40" rows="4" readonly="true">${badgeCode}</textarea></dd>
                                <dt><label><spring:message code="BadgePreview"/>:</label></dt>
                                <dd><div id="badgePreview" style="margin-left: 220px; width: 200px; border: 1px solid gray; padding: 4px;">${badgePreview}</div></dd>
                            </dl>
                        </fieldset>
                    </div>
                </div>
                <jsp:include page="../menu.jsp"/>
                <%@ include file="../about.jspf" %>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
        <script type="text/javascript">
            
dwr.util.setEscapeHtml(false);

new Autocompleter.DWR('continent', 'continentList', updateContinentList, { valueSelector: singleValueSelector, partialChars: 0, fullSearch: true });
new Autocompleter.DWR('country', 'countryList', updateCountryList, { valueSelector: singleValueSelector, partialChars: 0, fullSearch: true });
new Autocompleter.DWR('jugName', 'jugNameList', updateJugNameList, { valueSelector: singleValueSelector, partialChars: 0, fullSearch: true });

function updateContinentList(autocompleter, token) {
    juggerBo.findPartialContinent(token, function(data) {
        autocompleter.setChoices(data)
    });
}

function updateCountryList(autocompleter, token) {
    juggerBo.findPartialCountryWithContinent(token, $('continent').value, function(data) {
        autocompleter.setChoices(data)
    });
}

function updateJugNameList(autocompleter, token) {
    juggerBo.findPartialJugNameWithCountryAndContinent(token, $('country').value, $('continent').value, function(data) {
        autocompleter.setChoices(data)
    });
}

function singleValueSelector(tag) {
    return tag;
}

function updateBadge() {
    eventBo.updateBadgePanel($('continent').value, $('country').value, $('jugName').value, $('pastEvents').checked, $('badgeIncludeTheDescription').checked, $$('input[type=radio][name=badgeStyle]').find(function(el) { return el.checked }).value, '${requestScope.lang}');
}

        </script>
    </body>
</html>