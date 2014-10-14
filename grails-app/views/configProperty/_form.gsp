<%@ page import="com.dbconfig.ConfigProperty" %>


<fieldset class="fieldcontain ${hasErrors(bean: configPropertyInstance, field: 'configKey', 'formError')} ">
<div class="fieldcontain ${hasErrors(bean: configPropertyInstance, field: 'configKey', 'error')} ">
	<label for="configKey">
		<g:message code="configProperty.configKey.label" default="configKey" />
		
	</label>
	<g:textField class="form-text" name="configKey" value="${configPropertyInstance?.configKey}" />
</div>
</fieldset>

<fieldset class="fieldcontain ${hasErrors(bean: configPropertyInstance, field: 'value', 'formError')} ">
<div class="fieldcontain ${hasErrors(bean: configPropertyInstance, field: 'value', 'error')} ">
	<label for="value">
		<g:message code="configProperty.value.label" default="Value" />
		
	</label>
	<g:textField class="form-text" name="value" value="${configPropertyInstance?.value}" />
</div>
</fieldset>

<fieldset class="fieldcontain ${hasErrors(bean: configPropertyInstance, field: 'description', 'formError')} ">
<div class="fieldcontain ${hasErrors(bean: configPropertyInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="configProperty.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" value="${configPropertyInstance?.description}" />
</div>
</fieldset>