import org.codehaus.groovy.grails.commons.GrailsApplication

import com.dbconfig.ConfigProperty;
import com.dbconfig.ConfigPropertyService;

class DynamicConfigBootStrap {

	def configPropertyService
	def grailsApplication
	
    def init = {servletContext ->
		
		grailsApplication.flatConfig.each {key, value ->
			configPropertyService.loadValues(key, value)
		}
        
        ConfigProperty.findAllByDisable(true)*.updateConfigMap()
    }

    def destroy = {
    }
}
