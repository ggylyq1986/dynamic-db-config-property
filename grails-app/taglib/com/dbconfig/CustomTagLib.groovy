package com.dbconfig
import org.springframework.context.i18n.LocaleContextHolder as LCH

class CustomTagLib {
	def isInConfigFile = { attrs ->
		
		def isInConfigFile = false
		attrs.properties.each {configKey, value ->
			if(attrs.pConfigKey.equals(configKey)){
				isInConfigFile = true
				return
			}
		}
		out << '<td>' + (isInConfigFile? 'Yes' : 'No') + '</td>'
	}
}
