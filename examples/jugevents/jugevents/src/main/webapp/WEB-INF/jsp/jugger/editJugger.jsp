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
                                <dt><form:label path="jugger.user.password"><spring:message code="password"/> (*)</form:label></dt>
                                <dd><form:password path="jugger.user.password" /></dd>
                                <dt><form:label path="confirmPassword"><spring:message code="confirmPassword"/> (*)</form:label></dt>
                                <dd><form:password path="confirmPassword" /></dd>
                                <dt>&nbsp;</dt><dd><input type="submit" value="<spring:message code='Update'/>"/><br/><br/></dd>
                                <dt><spring:message code="juggerRegistrationRequired"/> (*)</dt>
                            </dl>
                        </fieldset>
                        
                       
                    </form:form>
                </div>
                <jsp:include page="../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
        
       
    </body>
</html>