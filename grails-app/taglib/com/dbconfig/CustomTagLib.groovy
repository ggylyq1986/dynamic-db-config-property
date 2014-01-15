package com.dbconfig
import org.springframework.context.i18n.LocaleContextHolder as LCH

class CustomTagLib {
	def isInConfigFile = { attrs ->
		
		def isInConfigFile = false
		attrs.properties.each {key, value ->
			if(attrs.pKey.equals(key)){
				isInConfigFile = true
				return
			}
		}
		out << '<td>' + (isInConfigFile? 'Yes' : 'No') + '</td>'
	}
}
