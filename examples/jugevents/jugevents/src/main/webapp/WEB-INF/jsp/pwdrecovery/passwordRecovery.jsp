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
                    <p><spring:message code='passwordRecoveryMessage'/></p>
                    <form:form commandName="passwordRecovery" method="POST" action="${cp}/passwordRecovery.form">
                        <form:errors path="*" cssClass="errorBox"/>

                        <fieldset>
                            <legend><spring:message code='passwordRecovery'/></legend>
                            <dl>
                                <dt>e-mail</dt>
                                <dd><form:input path="email"/></dd>
                            </dl>
                        </fieldset>

                        <dl>
                            <dt>&nbsp;</dt><dd><input type="submit" value="<spring:message code='Recover'/>"/><br/><br/></dd>
                            <dd>&nbsp;</dd>
                        </dl>
                    </form:form>
                </div>

                <jsp:include page="../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </body>
</html>