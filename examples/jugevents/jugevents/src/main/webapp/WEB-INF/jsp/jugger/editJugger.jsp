<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
        <script src="${cp}/dwr/interface/juggerBo.js" type="text/javascript"></script>

    </head>
    <body>
        <div id="nonFooter">
            <jsp:include page="../header.jsp"/>
            <div id="content">
                <div id="content_main">


                    <h1><spring:message code="Edit-Jugger"/></h1>

                    <form:form commandName="jugger" method="POST" action="${cp}/jugger/edit.form">
                        <form:errors path="*" cssClass="errorBox"/>


                        <form:hidden path="jugger.user.username"/>

                        <fieldset>
                            <legend>Jugger</legend>
                            <dl>
                                <dt><form:label path="jugger.firstName"><spring:message code="juggerRegistrationFirstName"/> (*)</form:label></dt>
                                <dd><form:input path="jugger.firstName"/></dd>
                                <dt><form:label path="jugger.lastName"><spring:message code="juggerRegistrationLastName"/> (*)</form:label></dt>
                                <dd><form:input path="jugger.lastName"/></dd>
                                <dt><form:label path="jugger.email"><spring:message code="Email"/> (*)</form:label></dt>
                                <dd><form:input path="jugger.email" size="35"/></dd>
                                <dt><form:label path="password"><spring:message code="password"/></form:label></dt>
                                <dd><form:password path="password" /></dd>
                                <dt><form:label path="confirmPassword"><spring:message code="confirmPassword"/></form:label></dt>
                                <dd><form:password path="confirmPassword" /></dd>

                            </dl>
                        </fieldset>
                        <fieldset>
                            <legend>JUG</legend>
                            <dl>
                                <dt><form:label path="jugger.jug.name"><spring:message code="juggerRegistrationJUGName"/> (*)</form:label></dt>
                                <dd><form:input path="jugger.jug.name"/><div id="jugList" class="auto_complete"></div></dd>
                                <dt><form:label path="jugger.jug.country.englishName" ><spring:message code="juggerRegistrationCountry"/></form:label></dt>
                                <dd><form:input path="jugger.jug.country.englishName" /><div id="countryList" class="auto_complete"></div></dd>
                                <dt><form:label path="jugger.jug.webSite" ><spring:message code="juggerRegistrationWebSite"/></form:label></dt>
                                <dd><form:input path="jugger.jug.webSite" /></dd>
                                <dt><form:label path="jugger.jug.longitude" ><spring:message code="juggerRegistrationLongitude"/></form:label></dt>
                                <dd><form:input path="jugger.jug.longitude" /></dd>
                                <dt><form:label path="jugger.jug.latitude" ><spring:message code="juggerRegistrationLatitude"/></form:label></dt>
                                <dd><form:input path="jugger.jug.latitude" /></dd>
                                <dt>
                                    <form:label path="jugger.jug.infos"><spring:message code="juggerRegistrationJUGInfos"/></form:label>
                                </dt>
                                <dd><form:textarea path="jugger.jug.infos"  readonly="true" cols="30" rows="5" /></dd>
                            </dl>
                        </fieldset>
                        <dl>
                            <dt>&nbsp;</dt><dd><input type="submit" value="<spring:message code='Update'/>"/><br/><br/></dd>

                            <dt><spring:message code="juggerRegistrationRequired"/> (*)</dt>

                        </dl>
                    </form:form>
                </div>
                <jsp:include page="../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
        <script type="text/javascript">

dwr.util.setEscapeHtml(false);

new Autocompleter.DWR('jugger.jug.country.englishName', 'countryList', updateCountryList, { valueSelector: singleValueSelector, partialChars: 0, fullSearch: true });
new Autocompleter.DWR('jugger.jug.name', 'jugList', updateJUGNameList, { valueSelector: singleValueSelector, partialChars: 0, fullSearch: true, afterUpdateElement: populateJugFields });

function updateCountryList(autocompleter, token) {
    juggerBo.findPartialCountry(token, function(data) {
        autocompleter.setChoices(data)
    });
}

function updateJUGNameList(autocompleter, token) {
    juggerBo.findPartialJugNameWithCountry(token, $('jugger.jug.country.englishName').value, function(data) {
        autocompleter.setChoices(data)
    });
}

function populateJugFields(jugName, selectedElement) {
    juggerBo.populateJugFields(jugName.value);
}

 function singleValueSelector(tag) {
    return tag;
}
        </script>

    </body>
</html>