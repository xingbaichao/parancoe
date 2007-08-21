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
           
            
            <h4>Insert Password to enable <bold>${jugger.user.username}</bold> to jugevents</h4>
            
            <form  name="pwdform" method="post" action="${cp}/jugger/enableJugger.html">
                 <table>                     
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password"/></td>                    
                    </tr>
                    <tr>
                        <td>Confirm Password</td>
                        <td><input type="password" name="confpassword"/></td>                    
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="Submit"/><br/><br/>
                        </td>
                    </tr>       
                   </table>
                 <input type="hidden" name="confirmationCode" value="${jugger.confirmationCode}"/>
            </form>
        </div>
        <jsp:include page="../../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../../footer.jsp"/>
        
    </body>
</html>