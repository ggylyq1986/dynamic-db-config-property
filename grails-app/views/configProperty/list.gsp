
<%@ page import="com.dbconfig.ConfigProperty" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main.gsp">
		<g:set var="entityName" value="${message(code: 'configProperty.label', default: 'ConfigProperty')}" />
		<g:render template="head"/>
		<style type="text/css">
		td, tr{
			font-size: 12px;
		}
		table{
			table-layout: fixed;
		}
		</style>
		<script type="text/javascript">
		function clicked(objId, key, value, data){
			$("#"+objId).prop('checked', true)
			$("#"+objId).prop('disabled', true)
			$("#" + objId).parent().parent().find('td:nth-child(4)').text(value)
			var key = $("#" + objId).parent().parent().find('td:nth-child(2)').text()
			var link = '<a href="${request.contextPath}/configProperty/show/' + data + '">' + key + '</a>'
			$("#" + objId).parent().parent().find('td:nth-child(2)').get(0).innerHTML = link
		}
		</script>
	</head>
	<body>
		<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all ui-resizable">
			<ul class="ui-tabs-nav ui-helper-clearfix ui-widget-header">
				<li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active">
					<g:link class="" controller="configProperty" action="list">Property Status</g:link>
				</li>
				<li class="ui-state-default ui-corner-top">
					<g:link class="" controller="configProperty" action="create">Create Property</g:link>
				</li>
			</ul>
			<div id="list-configProperty" class="content scaffold-list" role="main">
				<h2>&nbsp;</h2>
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<table class="sortable">
					<thead>
						<tr>
							<th style="width:4%">&nbsp;</th>
							
							<th style="width:30%"><g:message code="configProperty.config.name.label" default="Config Name" /></th>
						
							<th style="width:22%"><g:message code="configProperty.config.file.value.label" default="Config File Value" /></th>
							
							<th style="width:22%"><g:message code="configProperty.db.value.label" default="Database Value" /></th>
							
							<th style="width:22%"><g:message code="configProperty.current.value.label" default="Current Value" /></th>
							
						</tr>
					</thead>
					<tbody>
					<g:each in="${comparedProperties}" status="i" var="comparedProperty">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
							<g:if test="${isInDb || comparedProperty.dbProperty}">
							<td><input type="checkbox" id="checkbox_${i}" name="id" value="${comparedProperty.key}" checked disabled /></td>
							</g:if>
							<g:else>
								<td><g:checkBox name='checkbox_${i}' 
									value="${comparedProperty.key}" checked="false" 
									onclick="${remoteFunction(action:'addToFrequentlyUsedList', params: [key:comparedProperty.key, value:comparedProperty.fileProperty], onSuccess: "clicked('checkbox_${i}', '${comparedProperty.key}', '${comparedProperty.fileProperty}', data)" )}" /></td>
							</g:else>
						
							<g:if test="${comparedProperty.dbId != null}">
								<td><g:link action="show" id="${comparedProperty.dbId}">${fieldValue(bean: comparedProperty, field: "key")}</g:link></td>
							</g:if>
							<g:else>
								<td>${fieldValue(bean: comparedProperty, field: "key")}</td>
							</g:else>
						
							<td>${fieldValue(bean: comparedProperty, field: "fileProperty")}</td>
							
							<td>${fieldValue(bean: comparedProperty, field: "dbProperty")}</td>
							
							<td>${fieldValue(bean: comparedProperty, field: "currentProperty")}</td>
							
						</tr>
					</g:each>
					<tr>
						<td colspan="5">Total Records: ${totalNum}</td>
					</tr>
					</tbody>
				</table>
				<g:javascript src="app.js" />
			</div>
		</div>
	</body>
</html>
