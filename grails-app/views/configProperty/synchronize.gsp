
<%@ page import="com.dbconfig.ConfigProperty" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main.gsp">
		<g:set var="entityName" value="${message(code: 'configProperty.label', default: 'ConfigProperty')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<g:render template="head"/>
	</head>
	<body>
		<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all ui-resizable">
			<ul class="ui-tabs-nav ui-helper-clearfix ui-widget-header">
				<li class="ui-state-default ui-corner-top">
					<g:link class="icon icon_user" controller="configProperty" action="list">Manage Frequently-Used Property</g:link>
				</li>
				<li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active">
					<g:link class="icon icon_role" controller="configProperty" action="Synchronize">All Property</g:link>
				</li>
				<li class="ui-state-default ui-corner-top">
					<g:link class="icon icon_role" controller="configProperty" action="Create">Create Property</g:link>
				</li>
				<li class="ui-state-default ui-corner-top">
					<g:link class="icon icon_role" controller="configProperty" action="Compare">Property Status</g:link>
				</li>
			</ul>
			<div id="list-configProperty" class="content scaffold-list" role="main">
				<h2><font class="synpagecomment">* You could quickly tick boxes as frequently-used properties to "Manage Frequently-Used Property" Page. Then you should manage them on "Manage Property" Page. More importantly, only marked as frequently-used properties could be activated to work on the project. </font></h2>
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<table class="sortable">
					<thead>
						<tr>
							<th>&nbsp;*</th>
						
							<g:sortableColumn property="key" title="${message(code: 'configProperty.key.label', default: 'Key')}" />
						
							<g:sortableColumn property="value" title="${message(code: 'configProperty.value.label', default: 'Value')}" />
							
						</tr>
					</thead>
					<tbody>
					<g:each in="${configPropertyInstanceList}" status="i" var="configPropertyInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
							<g:if test="${configPropertyInstance.disable}">
							<td><input type="checkbox" name="id" value="${configPropertyInstance.id}" checked disabled /></td>
							</g:if>
							<g:else>
							<td><g:checkBox name='checkbox_${configPropertyInstance.id}' value="${configPropertyInstance.id}" checked="false" onclick="${remoteFunction(action:'addToFrequentlyUsedList', id:configPropertyInstance.id, onSuccess: "clicked(${configPropertyInstance.id})" )}" /></td>
							</g:else>
						
							<td><g:link action="show" id="${configPropertyInstance.id}">${fieldValue(bean: configPropertyInstance, field: "key")}</g:link></td>
						
							<td>${fieldValue(bean: configPropertyInstance, field: "value")}</td>
							
						</tr>
					</g:each>
					<tr>
						<td colspan="4">Total Records: ${configPropertyInstanceList.size()}</td>
					</tr>
					</tbody>
				</table>
				<g:javascript src="app.js" />
			</div>
		</div>
	</body>
</html>
