import org.codehaus.groovy.grails.commons.GrailsApplication

import com.dbconfig.ConfigProperty;
import com.dbconfig.ConfigPropertyService;

class DynamicConfigBootStrap {

	def configPropertyService
	def grailsApplication
	
    def init = {servletContext ->
        
        ConfigProperty.list()*.updateConfigMap()
    }

    def destroy = {
    }
}
