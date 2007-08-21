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
                
                
                <table>
                    <tr>
                        <td><spring:message code="juggerRegistrationFirstName"/> (*)</td>
                        <td><form:input path="jugger.firstName"/></td>
                       
                    </tr>
                    <tr>
                        <td><spring:message code="juggerRegistrationLastName"/> (*)</td>
                        <td><form:input path="jugger.lastName"/></td>
                        
                    </tr>
                    <tr>
                        <td>email (*)</td>
                        <td><form:input path="jugger.email"/></td>
                       
                    </tr>
                    
                    <tr>
                       <td>username (*)</td>
                        <td><form:input path="jugger.user.username"/></td>    						 
                    </tr>
                    
                    <tr>
                        <td><spring:message code="juggerRegistrationCountry"/> (*)</td>                        
                        <td>
                        <select name="jugger.country.isoCode">
                         <c:forEach var="c" items="${countries}" >
                         <option value="${c.isoCode}">${c.englishName}</option>
                         </c:forEach>
                        </select>                        
						</td>
						
                        
                    </tr>
                      <tr>
                        <td><spring:message code="juggerRegistrationJUGName"/> (*)</td>
                        <td><form:input path="jugger.jugName"/></td>
                        
                    </tr>
                     <tr>
                                <td><img src="${cp}/jcaptcha/image.html" alt="Captcha Image"/></td>
                                <td><form:input path="captchaResponse"/></td>
                     </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="Submit"/><br/><br/>
                        </td>
                    </tr>       
                    <tr>
                        <td><spring:message code="juggerRegistrationRequired"/> (*)</td>                        
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