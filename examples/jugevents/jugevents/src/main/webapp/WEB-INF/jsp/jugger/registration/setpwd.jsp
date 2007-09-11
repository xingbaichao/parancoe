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
           
            
            <h4><spring:message code='passwordInsert'/></h4>
            
            <form:form  commandName="enablejugger"  method="post" action="${cp}/jugger/enable.form">
                 <table>                     
                    <tr>
                        <td>Password</td>
                        <td><form:password   path="password"/></td>                    
                    </tr>
                    <tr>
                        <td><spring:message code='confirmPassword'/></td>
                        <td><form:password  path="confirmPassword"/></td>                    
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="Submit"/><br/><br/>
                        </td>
                    </tr>       
                   </table>
                   <form:errors path="*" cssClass="errorBox"/>
                 <input type="hidden" name="code" value="${jugger.confirmationCode}"/>
                 <input type="hidden" name="username" value="${jugger.user.username}"/>
            </form:form>
        </div>
        <jsp:include page="../../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../../footer.jsp"/>
        
    </body>
</html>