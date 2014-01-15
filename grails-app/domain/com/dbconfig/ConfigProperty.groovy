package com.dbconfig

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.runtime.DefaultGroovyMethods

class ConfigProperty {

	String key
	String value
    String description
	boolean disable
	
	ConfigProperty(String key, String value, String description, boolean disable) {
		this.key = key
		this.value = value
		this.description = description
		this.disable = disable
	}
    
    static constraints = {
        key (
            blank: false,
            nullable: false,
            maxSize: 100,
			unique: true
        )
		value (
			blank: false,
			nullable: false,
			maxSize: 100
		)
		description (
			blank: true,
			nullable: true,
			maxSize: 255
		)
		disable (
			blank: false,
			nullable: false
		)
    }

    static mapping = {
        //id generator: 'sequence', params: [sequence: 'config_seq']
    }
	
	String toString() {
		value
	}
	
	def beforeDelete = {
		CH.config.remove(key)
	}

	def beforeInsert = {
		updateConfigMap()
	}

	def beforeUpdate = {
		updateConfigMap()
	}
	
	def updateConfigMap() {
		Boolean useQuotes = !(((value ==~ /\[.*]/)) || DefaultGroovyMethods.isNumber(value) || DefaultGroovyMethods.isFloat(value) || value in ['true', 'false'])
		String objectString = useQuotes ? "${key}='''${value}'''" : "${key}=${value}"
        ConfigObject configObject = new ConfigSlurper().parse(objectString)
        CH.config.merge(configObject)
    }
}
