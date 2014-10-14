package com.dbconfig

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.runtime.DefaultGroovyMethods


class ConfigProperty {

	String configKey
	String value
	String description
	
	ConfigProperty(String configKey, String value, String description) {
		this.configKey = configKey
		this.value = value
		this.description = description
	}
	
	static constraints = {
		configKey (
			blank: false,
			nullable: false,
			maxSize: 512,
			unique: true
		)
		value (
			blank: false,
			nullable: false,
			maxSize: 1024
		)
		description (
			blank: true,
			nullable: true,
			maxSize: 512
		)
	}

	static mapping = {
		//id generator: 'sequence', params: [sequence: 'config_seq']
	}
	
	String toString() {
		value
	}
	
	def beforeDelete = {
		deleteConfigMap()
		//CH.config.remove(configKey)
	}

	def beforeInsert = {
		updateConfigMap()
	}

	def beforeUpdate = {
		updateConfigMap()
	}
	
	def updateConfigMap() {
		Boolean useQuotes = !(value instanceof ArrayList || DefaultGroovyMethods.isNumber(value) || DefaultGroovyMethods.isFloat(value) || value in ['true', 'false'])
		String objectString
		//the cases below are used by grails as well - it seems to get around some issues with parsing values
		if(configKey ==~ /(.*[^a-zA-Z0-9\.]+.*)/){
			objectString = "'''${configKey}=${value}'''"
		} else if (isList(value) || DefaultGroovyMethods.isNumber(value) || DefaultGroovyMethods.isFloat(value) || value in ['true', 'false']) {
			objectString = "${configKey}=" + new GroovyShell().evaluate(value)
		} else {
			objectString = "${configKey}='''${value}'''"
		}
		
		ConfigObject configObject  = new ConfigSlurper().parse(objectString)
		CH.config.merge(configObject)
	}
	
	def deleteConfigMap() {
		def previousValue = CH.flatConfig[configKey]?.toString()
		if(previousValue){
			String objectString
			if(configKey ==~ /(.*[^a-zA-Z0-9\.]+.*)/){
				objectString = "'''${configKey}=${value}'''"
			} else if (isList(previousValue) || DefaultGroovyMethods.isNumber(previousValue) || DefaultGroovyMethods.isFloat(previousValue) || previousValue in ['true', 'false']) {
				objectString = "${configKey}=" + new GroovyShell().evaluate(previousValue)
			} else {
				objectString = "${configKey}='''${value}'''"
			}
				
			ConfigObject configObject = new ConfigSlurper().parse(objectString)
			CH.config.merge(configObject)
		}
		else{
			CH.config.remove(configKey)
		}
	}
	
	def isList(def value){
		if(value.contains("[") && value.contains("]")){
			return true
		}
		return false
	}
	
	def tryParseToList(def value){
		value = value.toString()
		if(value.contains("[") && value.contains("]")){
			def valueList = []
			value.replaceAll("\\[","").replaceAll("]","").split(",").each{
				valueList << it.trim()
			}
			println "----||" + valueList
			println "----||" + valueList.getClass()
			return [valueList].flatten()
		}
		value
	}
}
