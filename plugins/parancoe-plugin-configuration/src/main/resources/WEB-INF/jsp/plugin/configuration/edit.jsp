<%--

    Copyright (C) 2006-2012 The Parancoe Team <info@parancoe.org>

    This file is part of Parancoe Plugin Configuration.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="page-header">
    <h1><spring:message code="PluginConfiguration_EditProperty"/></h1>
</div>
<div><a href=".." class="action action-back"><i class="icon-arrow-left"></i> <spring:message code="PluginConfiguration_Back"/></a></div>
<form:form commandName="pluginConfigurationProperty" method="POST" action="../${pluginConfigurationProperty.id}/store" cssClass="form-horizontal">
    <div class="control-group">
        <label class="control-label"><spring:message code="PluginConfiguration_Category"/>:</label>
        <div class="controls" style="padding-top: 5px;"><spring:message code="${pluginConfigurationProperty.category.name}"/></div>
    </div>
    <div class="control-group">
        <label class="control-label"><spring:message code="PluginConfiguration_PropertyName"/>:</label>
        <div class="controls" style="padding-top: 5px;">${pluginConfigurationProperty.name}</div>
    </div>
    <div class="control-group">
        <label class="control-label"><spring:message code="PluginConfiguration_PropertyDescription"/>:</label>
        <div class="controls" style="padding-top: 5px;">${pluginConfigurationProperty.description}</div>
    </div>
    <c:choose>
        <c:when test="${pluginConfigurationProperty.type == 'TEXT'}">
            <div class="control-group">
                <form:label path="value" cssClass="control-label"><spring:message code="PluginConfiguration_PropertyValue"/></form:label>
                    <div class="controls">
                        <form:textarea path="value" cssClass="span5" rows="5"/>
                    <form:errors path="value" cssClass="help-inline error" cssStyle="color: #B94A48;"/>
                </div>
            </div>
        </c:when>
        <c:when test="${pluginConfigurationProperty.type == 'BOOLEAN'}">
            <div class="control-group">
                <form:label path="value" cssClass="control-label"><spring:message code="PluginConfiguration_PropertyValue"/></form:label>
                    <div class="controls">
                    <spring:message code='PluginConfiguration_False' var="FalseLabel"/>
                    <spring:message code='PluginConfiguration_True' var="TrueLabel"/>
                    <form:select path="value">
                        <form:option label="${FalseLabel}" value="false"/>
                        <form:option label="${TrueLabel}" value="true"/>
                    </form:select>
                    <form:errors path="value" cssClass="help-inline error" cssStyle="color: #B94A48;"/>
                </div>
            </div>
        </c:when>
        <c:when test="${pluginConfigurationProperty.type == 'INTEGER' || pluginConfigurationProperty.type == 'REAL'}">
            <div class="control-group">
                <form:label path="value" cssClass="control-label"><spring:message code="PluginConfiguration_PropertyValue"/></form:label>
                    <div class="controls">
                    <form:input path="value" cssClass="span2" maxlength="255"/>
                    <form:errors path="value" cssClass="help-inline error" cssStyle="color: #B94A48;"/>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="control-group">
                <form:label path="value" cssClass="control-label"><spring:message code="PluginConfiguration_PropertyValue"/></form:label>
                    <div class="controls">
                    <form:input path="value" cssClass="span4" maxlength="2255"/>
                    <form:errors path="value" cssClass="help-inline error" cssStyle="color: #B94A48;"/>
                </div>
            </div>
        </c:otherwise>
    </c:choose>    
    <div class="form-actions">
        <input type="submit" value="<spring:message code="PluginConfiguration_Save"/>" class="btn btn-primary"/>
    </div>
</form:form>
