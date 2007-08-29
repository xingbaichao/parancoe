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


                    <h2><spring:message code="juggerRegistrationTitle"/></h2>

                    <form:form commandName="jugger" method="POST" action="${cp}/jugger/registration.form">

                        <form:hidden path="jugger.id"/>

                        <dl>
                            <dt><form:label path="jugger.firstName"><spring:message code="juggerRegistrationFirstName"/> (*)</form:label></dt>
                            <dd><form:input path="jugger.firstName"/></dd>
                            <dt><form:label path="jugger.lastName"><spring:message code="juggerRegistrationLastName"/> (*)</form:label></dt>
                            <dd><form:input path="jugger.lastName"/></dd>
                            <dt><form:label path="jugger.email"><spring:message code="Email"/> (*)</form:label></dt>
                            <dd><form:input path="jugger.email"/></dd>
                            <dt><form:label path="jugger.user.username"><spring:message code="username"/> (*)</form:label></dt>
                            <dd><form:input path="jugger.user.username"/></dd>
                            <dt><form:label path="jugger.country.isoCode"><spring:message code="juggerRegistrationCountry"/> (*)</form:label></dt>
                            <dd>
                                <form:select path="jugger.country.isoCode">
                                    <form:option value="" label="--Please Select"/>
                                    <form:options items="${countries}" itemValue="isoCode" itemLabel="englishName"/>
                                </form:select>

                            </dd>
                            <dt><form:label path="jugger.jug.name"><spring:message code="juggerRegistrationJUGName"/> (*)</form:label></dt>
                            <dd><form:input path="jugger.jug.name"/></dd>
                            <dt><form:label path="captchaResponse"><spring:message code="InsertCharactersInTheImage"/></form:label></dt>
                            <dd style="margin-left: 210px;"><form:input path="captchaResponse"/><br/><img src="${cp}/jcaptcha/image.html" alt="Captcha Image"/></dd>
                            <dt>&nbsp;</dt>
                            <dd><input type="submit" value="Submit"/><br/><br/></dd>
                            <dt><spring:message code="juggerRegistrationRequired"/> (*)</dt>
                            <dd>&nbsp;</dd>
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