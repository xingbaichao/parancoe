<%@ include file="../../common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../../head.jspf" %>
    </head>
    <body>
        <div id="nonFooter">    
            <jsp:include page="../../header.jsp"/>
             <div id="content">              
               <div id="content_main">
           
            
            <h2>Details Jugger</h2>
                
                
                <table>
                    <tr>
                        <td><spring:message code="juggerRegistrationFirstName"/></td>
                        <td>${jugger.firstName}</td>
                       
                    </tr>
                    <tr>
                        <td><spring:message code="juggerRegistrationLastName"/></td>
                        <td>${jugger.lastName}</td>
                        
                    </tr>
                    <tr>
                        <td>email</td>
                        <td>${jugger.email}</td>
                       
                    </tr>
                    
                    <tr>
                       <td>username</td>
                        <td>${jugger.user.username}</td>    						 
                    </tr>
                    
                    <tr>
                        <td><spring:message code="juggerRegistrationCountry"/></td>                        
                        <td>${jugger.country.englishName}</td>    
	               </tr>
                   <tr>
                     <td><spring:message code="juggerRegistrationJUGName"/></td>
                     <td>${jugger.jugName}</td>                        
                  </tr>     
                  <tr>
                     <td>enabled</td>
                     <td>${jugger.user.enabled}</td>                        
                  </tr>                    
                </table>
               
        </div>
        <jsp:include page="../../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../../footer.jsp"/>
        
    </body>
</html>