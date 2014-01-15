package com.dbconfig

import org.springframework.dao.DataIntegrityViolationException
import org.codehaus.groovy.grails.commons.GrailsApplication

import com.dbconfig.ConfigProperty;


class ConfigPropertyController {
	
	GrailsApplication grailsApplication

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		def properties = session["DBCfgProperties"]
		if(!properties){
			properties = grailsApplication.flatConfig
			session.setAttribute("DBCfgProperties", properties)
		}
        [configPropertyInstanceList: ConfigProperty.findAllByDisable(true), configPropertyInstanceTotal: ConfigProperty.count(), properties : grailsApplication.flatConfig]
    }
	
	def synchronize(Integer max) {
		def properties = session["DBCfgProperties"]
		if(!properties){
			properties = grailsApplication.flatConfig
			session.setAttribute("DBCfgProperties", properties)
		}
		[configPropertyInstanceList: ConfigProperty.list(params), configPropertyInstanceTotal: ConfigProperty.count(), properties : grailsApplication.flatConfig]
	}

    def create() {
        [configPropertyInstance: new ConfigProperty(params)]
    }

    def save() {
        def configPropertyInstance = new ConfigProperty(params)
		configPropertyInstance.disable = true
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
		configPropertyInstance.disable = true
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
		def configProperty = ConfigProperty.get(params.id)
		configProperty.disable = true
		configProperty.save()
		
		render "ok"
	}
	
	def compare() {
		def fileProperties = grailsApplication.flatConfig
		def dbProperties = ConfigProperty.findAllByDisable(true)
		
		def comparedProperties = dbProperties.collect {
			def dbProperty = it.value
			def fileProperty = fileProperties[it.key]
			
			new ComparedProperty(it.key?.toString(), dbProperty?.toString(), fileProperty?.toString())
		}
		
		[comparedProperties: comparedProperties]
	}
}

class ComparedProperty {
	String key
	String dbProperty
	String fileProperty
	
	public ComparedProperty (String key, String dbProperty, String fileProperty) {
		this.key = key
		this.dbProperty = dbProperty
		this.fileProperty = fileProperty
	}
}
