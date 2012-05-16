#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

<%@ include file="../common.jspf" %>
<h1>Administration Console</h1>

<ul>
    <li><a href="logs.html">Application logs</a></li>
    <li><a href="conf.html">App Configuration</a></li>
    <li><a href="spring.html">Spring Beans</a></li>
    <li><a href="${symbol_dollar}{cpr}/admin/users">Users</a></li>
</ul>
