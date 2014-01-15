<%@ page import="com.dbconfig.ConfigProperty" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main.gsp">
		<g:set var="entityName" value="${message(code: 'configProperty.label', default: 'ConfigProperty')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<g:render template="head"/>
	</head>
	<body>
		<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all ui-resizable">
			<ul class="ui-tabs-nav ui-helper-clearfix ui-widget-header">
				<li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active">
					<g:link class="icon icon_user" controller="configProperty" action="list">Manage Frequently-Used Property</g:link>
				</li>
				<li class="ui-state-default ui-corner-top">
					<g:link class="icon icon_role" controller="configProperty" action="Synchronize">All Property</g:link>
				</li>
				<li class="ui-state-default ui-corner-top">
					<g:link class="icon icon_role" controller="configProperty" action="Create">Create Property</g:link>
				</li>
				<li class="ui-state-default ui-corner-top">
					<g:link class="icon icon_role" controller="configProperty" action="Compare">Compare Property</g:link>
				</li>
			</ul>
			<div id="edit-configProperty" class="content scaffold-create" role="main">
				<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
				</g:if>
				<g:hasErrors bean="${configPropertyInstance}">
				<ul class="errors" role="alert">
					<g:eachError bean="${configPropertyInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</g:hasErrors>
				<g:form method="post" class="columns" >
					<g:hiddenField name="id" value="${configPropertyInstance?.id}" />
					<g:hiddenField name="version" value="${configPropertyInstance?.version}" />
					<g:render template="form"/>
					<fieldset class="form-actions">
						<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</fieldset>
				</g:form>
			</div>
		</div>
	</body>
</html>
