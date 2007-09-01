<%@ include file="../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
        <script src="${cp}/dwr/interface/juggerBo.js" type="text/javascript"></script>
        <script src="${cp}/dwr/interface/eventBo.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="nonFooter">
            <jsp:include page="../header.jsp"/>
            <div id="content">
                <div id="content_main">
                    
                    
                    <h2><spring:message code="juggerRegistrationTitle"/></h2>
                    
                    <form:form commandName="jugger" method="POST" action="${cp}/jugger/registration.form">
                        <form:errors path="*" cssClass="errorBox"/>
                        
                        <form:hidden path="jugger.id"/>
                        
                        <fieldset>
                            <legend>Jugger</legend>                        
                            <dl>
                                <dt><form:label path="jugger.firstName"><spring:message code="juggerRegistrationFirstName"/> (*)</form:label></dt>
                                <dd><form:input path="jugger.firstName"/></dd>
                                <dt><form:label path="jugger.lastName"><spring:message code="juggerRegistrationLastName"/> (*)</form:label></dt>
                                <dd><form:input path="jugger.lastName"/></dd>
                                <dt><form:label path="jugger.email"><spring:message code="Email"/> (*)</form:label></dt>
                                <dd><form:input path="jugger.email"/></dd>
                                <dt><form:label path="jugger.user.username"><spring:message code="username"/> (*)</form:label></dt>
                                <dd><form:input path="jugger.user.username"/></dd>
                            </dl>
                        </fieldset>
                        <fieldset>
                            <legend>JUG</legend>                        
                            <dl>
                                <dt><form:label path="jugger.jug.name"><spring:message code="juggerRegistrationJUGName"/> (*)</form:label></dt>                            
                                <dd><form:input path="jugger.jug.name"/><div id="jugList" class="auto_complete"></div></dd>      
                                <dt><form:label path="jugger.jug.country.englishName"><spring:message code="juggerRegistrationCountry"/></form:label></dt>                            
                                <dd><form:input path="jugger.jug.country.englishName"/><div id="countryList" class="auto_complete"></div></dd>
                            </dl>
                        </fieldset>
                        <dl>
                            <dt><form:label path="captchaResponse"><spring:message code="InsertCharactersInTheImage"/></form:label></dt>
                            <dd style="margin-left: 210px;"><form:input path="captchaResponse"/><br/><img src="${cp}/jcaptcha/image.html" alt="Captcha Image"/></dd>
                            <dt>&nbsp;</dt>
                            <dd><input type="submit" value="Submit"/><br/><br/></dd>
                            <dt><spring:message code="juggerRegistrationRequired"/> (*)</dt>
                            <dd>&nbsp;</dd>
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
new Autocompleter.DWR('jugger.jug.name', 'jugList', updateJUGNameList, { valueSelector: singleValueSelector, partialChars: 0, fullSearch: true });

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

    
 function singleValueSelector(tag) {
    return tag;
}
        </script>
    </body>
</html>