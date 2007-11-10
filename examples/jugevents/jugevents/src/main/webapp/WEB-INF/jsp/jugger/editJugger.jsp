<%@ include file="../common.jspf" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="../head.jspf" %>
        <script src="${cp}/dwr/interface/juggerBo.js" type="text/javascript"></script>
        <script type="text/javascript">        
            function require()
            {     
            if($('requireReliability.requireReliability1').checked)
            {
            $('hcomment').show(); return false;
            }
            else
            {
            $('hcomment').hide(); return false;
            }   
            }  
        </script>
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
                            <fieldset><legend><spring:message
                                    code="Reliability" /></legend>
                                <dl>
                                    <dt><form:label path="requireReliability.requireReliability">
                                            <spring:message code="requireReliability" />
                                    </form:label></dt>
                                    <dd><form:checkbox path="requireReliability.requireReliability" value='false'
                                                       onclick="javascript:require();" />&nbsp;<img id="tip_reliability" src="${cp}/images/question16x16.png" /></dd>
                                </dl>
                                <div id="hcomment" style="display: none;">
                                    <dl>
                                        <dt><spring:message code="commentReliability" /></dt>
                                        <dd><form:textarea path="requireReliability.comment" cols="35" rows="5" /></dd>
                                    </dl>
                                </div>
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
            var s = document.getElementById('jugger.jug.name');      
            var k = document.getElementById('reliable');    
            juggerBo.readOnlyJugFields(s.value, k.value);
            }
        </script>
        
    </body>
</html>