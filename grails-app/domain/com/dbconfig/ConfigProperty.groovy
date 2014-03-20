package com.dbconfig

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.runtime.DefaultGroovyMethods


class ConfigProperty {

	String key
	String value
	String description
	
	ConfigProperty(String key, String value, String description) {
		this.key = key
		this.value = value
		this.description = description
	}
	
	static constraints = {
		key (
			blank: false,
			nullable: false,
			maxSize: 1000,
			unique: true
		)
		value (
			blank: false,
			nullable: false,
			maxSize: 2000
		)
		description (
			blank: true,
			nullable: true,
			maxSize: 2000
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
		//CH.config.remove(key)
	}

	def beforeInsert = {
		updateConfigMap()
	}

	def beforeUpdate = {
		updateConfigMap()
	}
	
	def updateConfigMap() {
		Boolean useQuotes = !(DefaultGroovyMethods.isNumber(value) || DefaultGroovyMethods.isFloat(value) || value in ['true', 'false'])
		String objectString
		if(key ==~ /(.*[^a-zA-Z0-9\.]+.*)/){
			objectString = "'''${key}=${value}'''"
		}
		else{
			objectString = useQuotes ? "${key}='''${value}'''" : "${key}=${value}"
		}
		
		ConfigObject configObject = new ConfigSlurper().parse(objectString)
		CH.config.merge(configObject)
	}
	
	def deleteConfigMap() {
		def previousValue = CH.flatConfig[key]?.toString()
		if(previousValue){
			Boolean useQuotes = !(DefaultGroovyMethods.isNumber(previousValue) || DefaultGroovyMethods.isFloat(previousValue) || previousValue in ['true', 'false'])
			String objectString
			if(key ==~ /(.*[^a-zA-Z0-9\.]+.*)/){
					objectString = "'''${key}=${previousValue}'''"
			}
			else{
				objectString = useQuotes ? "${key}='''${previousValue}'''" : "${key}=${previousValue}"
			}
				
			ConfigObject configObject = new ConfigSlurper().parse(objectString)
			CH.config.merge(configObject)
		}
		else{
			CH.config.remove(key)
		}
		
	}
}
