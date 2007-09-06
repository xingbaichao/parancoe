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
                        
                        <form:hidden path="id"/>
                        <form:hidden path="user.username"/>
                        
                        <fieldset>
                            <legend>Jugger</legend>                        
                            <dl>
                                <dt><form:label path="firstName"><spring:message code="juggerRegistrationFirstName"/> (*)</form:label></dt>
                                <dd><form:input path="firstName"/></dd>
                                <dt><form:label path="lastName"><spring:message code="juggerRegistrationLastName"/> (*)</form:label></dt>
                                <dd><form:input path="lastName"/></dd>
                                <dt><form:label path="email"><spring:message code="Email"/> (*)</form:label></dt>
                                <dd><form:input path="email"/></dd>
                                <dt><form:label path="user.password"><spring:message code="password"/> (*)</form:label></dt>
                                <dd><form:password path="user.password" showPassword="true"/></dd>
                                
                            </dl>
                        </fieldset>
                        
                        <dl>
                            
                            <dd><input type="submit" value="<spring:message code='Update'/>"/><br/><br/></dd>
                            <dt><spring:message code="juggerRegistrationRequired"/> (*)</dt>
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