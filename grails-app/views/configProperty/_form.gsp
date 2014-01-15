<%@ page import="com.dbconfig.ConfigProperty" %>


<fieldset class="fieldcontain ${hasErrors(bean: configPropertyInstance, field: 'key', 'formError')} ">
<div class="fieldcontain ${hasErrors(bean: configPropertyInstance, field: 'key', 'error')} ">
	<label for="key">
		<g:message code="configProperty.key.label" default="Key" />
		
	</label>
	<g:textField class="form-text" name="key" value="${configPropertyInstance?.key}" />
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