package com.dbconfig

import org.springframework.dao.DataIntegrityViolationException
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import com.dbconfig.ConfigProperty;


class ConfigPropertyController {
	
	GrailsApplication grailsApplication

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
	def list(Integer max) {
		
		def fileProperties = grailsApplication.flatConfig
		
		def comparedProperties = fileProperties.collect {key, value ->
			def dbProperty = ConfigProperty.findByKey(key)
			def isInDb = dbProperty? true : false 
			def dbId = dbProperty? dbProperty.id : null
			def currentPro = dbProperty? dbProperty.value?.toString() :  grailsApplication.flatConfig[key]?.toString()
			
			new ComparedProperty(dbId, key.toString(), value.toString(), dbProperty?.value?.toString(), currentPro, isInDb)
		}
		
		ConfigProperty.list().each {
			
			if(!grailsApplication.flatConfig[it.key] && !"".equals(grailsApplication.flatConfig[it.key])){
				grailsApplication.flatConfig[it.key] = ""
				def comparedProperty = new ComparedProperty(it.id, it.key.toString(), null, it.value?.toString(), it.value?.toString(), true)
				if(!comparedProperties.contains(comparedProperty)){
					comparedProperties.add(comparedProperty)
				}
			}
		}
		
		[comparedProperties: comparedProperties, totalNum: comparedProperties.size()]
	}

    def create() {
        [configPropertyInstance: new ConfigProperty(params)]
    }

    def save() {
        def configPropertyInstance = new ConfigProperty(params)
        if (!configPropertyInstance.save(flush: true)) {
            render(view: "create", model: [configPropertyInstance: configPropertyInstance])
            return
        }
		
		def properties = grailsApplication.flatConfig
		session.setAttribute("DBCfgProperties", properties)

        flash.message = message(code: 'default.created.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), configPropertyInstance.id])
        redirect(action: "show", id: configPropertyInstance.id)
    }

    def show(Long id) {
        def configPropertyInstance = ConfigProperty.get(id)
        if (!configPropertyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), id])
            redirect(action: "list")
            return
        }

        [configPropertyInstance: configPropertyInstance]
    }

    def edit(Long id) {
        def configPropertyInstance = ConfigProperty.get(id)
        if (!configPropertyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), id])
            redirect(action: "list")
            return
        }

        [configPropertyInstance: configPropertyInstance]
    }

    def update(Long id, Long version) {
        def configPropertyInstance = ConfigProperty.get(id)
        if (!configPropertyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (configPropertyInstance.version > version) {
                configPropertyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'configProperty.label', default: 'ConfigProperty')] as Object[],
                          "Another user has updated this ConfigProperty while you were editing")
                render(view: "edit", model: [configPropertyInstance: configPropertyInstance])
                return
            }
        }

        configPropertyInstance.properties = params

        if (!configPropertyInstance.save(flush: true)) {
            render(view: "edit", model: [configPropertyInstance: configPropertyInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), configPropertyInstance.id])
        redirect(action: "show", id: configPropertyInstance.id)
    }

    def delete(Long id) {
        def configPropertyInstance = ConfigProperty.get(id)
        if (!configPropertyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), id])
            redirect(action: "list")
            return
        }

        try {
            configPropertyInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def addToFrequentlyUsedList() {
		
		def configProperty = new ConfigProperty(params.key?.toString(), params.value?.toString(), "")
		configProperty.save()
		
		render configProperty.id
	}
	
	def compare() {
		def fileProperties = grailsApplication.flatConfig
		def dbProperties = ConfigProperty.list()
		
		def comparedProperties = dbProperties.collect {
			def dbProperty = it.value
			def fileProperty = fileProperties[it.key]
			
			new ComparedProperty(it.key?.toString(), dbProperty?.toString(), fileProperty?.toString(), null)
		}
		
		[comparedProperties: comparedProperties]
	}
}

class ComparedProperty {
	def dbId
	String key
	String fileProperty
	String dbProperty
	String currentProperty
	boolean isInDb
	
	public ComparedProperty (def dbId, String key, String fileProperty, String dbProperty, String currentProperty, boolean isInDb) {
		this.dbId = dbId
		this.key = key
		this.fileProperty = fileProperty
		this.dbProperty = dbProperty
		this.currentProperty = currentProperty
		this.isInDb = isInDb
	}
}
