
<%@ page import="com.dbconfig.ConfigProperty" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main.gsp">
		<g:set var="entityName" value="${message(code: 'configProperty.label', default: 'ConfigProperty')}" />
		<g:render template="head"/>
	</head>
	<body>
		<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all ui-resizable">
			<ul class="ui-tabs-nav ui-helper-clearfix ui-widget-header">
				<li class="ui-state-default ui-corner-top">
					<g:link class="" controller="configProperty" action="list">Manage Frequently-Used Property</g:link>
				</li>
				<li class="ui-state-default ui-corner-top">
					<g:link class="" controller="configProperty" action="Synchronize">All Property</g:link>
				</li>
				<li class="ui-state-default ui-corner-top">
					<g:link class="" controller="configProperty" action="Create">Create Property</g:link>
				</li>
				<li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active">
					<g:link class="" controller="configProperty" action="Compare">Property Status</g:link>
				</li>
			</ul>
			<div id="list-configProperty" class="content scaffold-list" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<table class="sortable">
					<thead>
						<tr>
							<th><g:message code="default.Property.Key" default="Property Key" /></th>
							
							<th><g:message code="default.Property.Default.Value" default="Default Value" /></th>
						
							<th><g:message code="default.Property.Current.Value" default="Current Value" /></th>
							
						</tr>
					</thead>
					<tbody>
					<g:each in="${comparedProperties}" status="i" var="comparedPropertie">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<td>${fieldValue(bean: comparedPropertie, field: "key")}</td>
							
							<td>${fieldValue(bean: comparedPropertie, field: "fileProperty")}</td>
							
							<td>${fieldValue(bean: comparedPropertie, field: "dbProperty")}</td>
						</tr>
					</g:each>
					<tr>
						<td colspan="4">Total Records: ${comparedProperties.size()}</td>
					</tr>
					</tbody>
				</table>
				<g:javascript src="app.js" />
			</div>
		</div>
	</body>
</html>
