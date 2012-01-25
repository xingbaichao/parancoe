#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/WEB-INF/jsp/common.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <link rel="stylesheet" href="${symbol_dollar}{cp}/templates/clarity/css/1.css" type="text/css" media="screen,projection" />
        <link rel="stylesheet" href="${symbol_dollar}{cp}/templates/clarity/sifr/sifr.css" type="text/css" media="screen,projection" />
        <%@ include file="/WEB-INF/jsp/head.jspf" %>
        <script src="${symbol_dollar}{cp}/templates/clarity/sifr/sifr.js" type="text/javascript"></script>
        <script src="${symbol_dollar}{cp}/templates/clarity/sifr/sifr-config.js" type="text/javascript"></script>
    </head>

    <body>

        <div id="header">
            <tiles:insertAttribute name="header"/>
            <tiles:insertAttribute name="menu"/>
        </div>

        <div id="container">

            <div id="content">
                <p:flash type="notice"/>
                <p:flash type="error"/>
                <tiles:insertAttribute name="main"/>
            </div>
            <div id="sidebar">
                <tiles:insertAttribute name="sidebar"/>
            </div>
        </div>

        <div id="footer">
            <p class="validate"><a href="http://validator.w3.org/check?uri=referer">XHTML</a> | <a href="http://jigsaw.w3.org/css-validator/">CSS</a><br /><a href="${symbol_pound}">Top</a></p>

            <!-- Please leave this line intact -->
            <p>Template design by <a href="http://www.sixshootermedia.com">Six Shooter Media</a>.<br />
                <!-- you can delete below here -->
                <a href="http://wwww.parancoe.org"><img src="${symbol_dollar}{cp}/images/powered_parancoe.png" alt="Parancoe powered" style="border: 0"/></a> &copy; <a href="http://www.${parentArtifactId}.com">${parentArtifactId}</a></p>
        </div>

        <% if (!BaseConf.isProduction()) {%>
        <jsp:include page="/WEB-INF/jsp/debug.jsp" />
        <% }%>

    </body>
</html>