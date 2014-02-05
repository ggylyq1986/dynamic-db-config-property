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
				<li class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active">
					<g:link class="" controller="configProperty" action="Create">Create Property</g:link>
				</li>
				<li class="ui-state-default ui-corner-top">
					<g:link class="" controller="configProperty" action="Compare">Property Status</g:link>
				</li>
			</ul>
			<div id="create-configProperty" class="content scaffold-create" role="main">
				<g:if test="${flash.message}">
				<ul class="errors">
					<li>${flash.message}</li>
				</ul>
				</g:if>
				<g:hasErrors bean="${configPropertyInstance}">
				<ul class="errors" role="alert">
					<g:eachError bean="${configPropertyInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</g:hasErrors>
				<g:form action="save" class="columns" >
					<g:render template="form"/>
					<fieldset class="form-actions">
						<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
					</fieldset>
				</g:form>
			</div>
		</div>
	</body>
</html>
