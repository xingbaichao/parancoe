<%@ include file="../common.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
        <script src="${cp}/dwr/interface/juggerBo.js" type="text/javascript"></script>
		<script src="${cp}/dwr/interface/servicesBo.js" type="text/javascript"></script>
        <script src="${cp}/javascripts/modal.js" type="text/javascript"></script>
        <style type="text/css">

#mww
{
    position: fixed;    
    z-index: 10;
	background-color: white;
	// display: none;
	width: 	18em;
	height: 8em;
	border: 2px solid blue;
	padding: 2em 2em 2em 2em;	
	
}

#mbg
{
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    
	z-index: 9;
	background-color:#222222;
	display: none;
	opacity: 0.50;
	filter: alpha(opacity=40)
}
</style>

<!--[if gte IE 5.5]>
<![if lt IE 7]>
<style>
#mbg, #modalIframe
{    
	position: absolute;
    left: expression(ignoreMe = document.documentElement.scrollLeft + "px");
    top: expression(ignoreMe = document.documentElement.scrollTop + "px");
    width: expression(document.documentElement.clientWidth + "px");
    height: expression(document.documentElement.clientHeight + "px");
}

#modalIframe
{
	filter: alpha(opacity=0);
	z-index: 8;
}

#mww
{
	position: absolute;
    left: expression(ignoreMe = document.documentElement.scrollLeft + "px");
    top: expression(ignoreMe = document.documentElement.scrollTop + "px");
}
</style>
<![endif]>
<![endif]-->
        
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
                        <form:hidden path="reliable" />
                        
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
                        
                        <c:if test="${!jugger.reliable}">                            
                            <fieldset>
                            	<legend><spring:message  code="Reliability" /></legend>       
                            	<a href="javascript:require();"><spring:message code="requireReliability"/></a>                
                                &nbsp;<img id="tip_reliability" src="${cp}/images/question16x16.png" />          
                                
                               
								<div id="confirmMSGOK" style="display: none; color: #41AF0A"></div>
								
                               
								
                            </fieldset>      
						 <script type="text/javascript">
                                new Tip($('tip_reliability'), '<spring:message code="tip.reliability"/>', {title: 'Reliability', effect: 'appear'});
                            </script>                      
                        </c:if>
                        
                        <fieldset><legend>JUG</legend>
                            <dl>
                                <dt><form:label path="jugger.jug.name">
                                <spring:message code="juggerRegistrationJUGName" /> (*)</form:label></dt>
                                <dd><form:input path="jugger.jug.name" 
                                                    onblur="javascript:disableJugFields();" />
                                    <div id="jugList" class="auto_complete"></div>
                                </dd>
                                <dt><form:label path="jugger.jug.country.englishName">
                                        <spring:message code="juggerRegistrationCountry" />
                                </form:label></dt>
                                <dd><form:input path="jugger.jug.country.englishName" readonly="${!jugger.reliable}" disabled="${!jugger.reliable}" />
                                    <div id="countryList" class="auto_complete"></div>
                                </dd>
                                <dt><form:label path="jugger.jug.webSite">
                                        <spring:message code="juggerRegistrationWebSite" />
                                </form:label></dt>
                                <dd><form:input path="jugger.jug.webSite" readonly="${!jugger.reliable}" disabled="${!jugger.reliable}"/></dd>
                                <dt><form:label path="jugger.jug.longitude">
                                        <spring:message code="juggerRegistrationLongitude" />
                                </form:label></dt>
                                <dd><form:input path="jugger.jug.longitude" readonly="${!jugger.reliable}" disabled="${!jugger.reliable}"/></dd>
                                <dt><form:label path="jugger.jug.latitude">
                                        <spring:message code="juggerRegistrationLatitude" />
                                </form:label></dt>
                                <dd><form:input path="jugger.jug.latitude" readonly="${!jugger.reliable}" disabled="${!jugger.reliable}"/></dd>
                                <dt><form:label path="jugger.jug.infos">
                                        <spring:message code="juggerRegistrationJUGInfos" />
                                </form:label></dt>
                                <dd><form:textarea path="jugger.jug.infos" cols="30" rows="5" readonly="${!jugger.reliable}" disabled="${!jugger.reliable}"/></dd>
                            </dl>
                        </fieldset>
                        <dl>
                            <dt>&nbsp;</dt>
                            <dd><input type="submit" value="<spring:message code='Update'/>" /><br/><br/></dd>                            
                            <dt><spring:message code="juggerRegistrationRequired" /> (*)</dt><dd>&nbsp;</dd>                            
                        </dl>
                        
                    </form:form>
                </div>
                <jsp:include page="../menu.jsp"/>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
        
       <div id="mww">    
			<table width="60%" align="center" valign="center">
			<tr><td colspan="2"><spring:message code="commentReliability" /></td></tr>
			<tr><td colspan="2" ><textarea id="commentr"  ></textarea></td></tr>
			<tr>
				<td align="center"><input value="Send" type="button" onclick="javascript:requireReliability();"/></td>
				<td align="center"> <input value="Cancel" type="button" onclick="javascript:comeBack();"/></td>
			</tr>
			</table>
		</div>
	    <div id="mbg"></div>
        
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
            
            function disableJugFields() {
            // var s = document.getElementById('jugger.jug.name');      
            // var k = document.getElementById('reliable');    
            juggerBo.readOnlyJugFields($('jugger.jug.name').value, $('reliable').value);
            }
            
            function requireReliability() {			 
			  // var s = document.getElementById('jugger.email');      
			  // var k = document.getElementById('commentr'); 		
              comeBack();			  
              servicesBo.requireReliabilityOnExistingJugger($('jugger.email').value, $('commentr').value, function(data) 
	              {				    
				    if(data == 'true')
					{
					    
						dwr.util.setValue("confirmMSGOK", "<spring:message code="confirmMSG.RequireReliability.OK" />");
						
					}
					else
					{    
						 dwr.util.setValue("confirmMSGOK", "<spring:message code="confirmMSG.RequireReliability.KO" />");
						
					}   
				$('confirmMSGOK').show(); 
	              });		 
            }
        </script>
       
        
    </body>
</html>