package com.dbconfig

import com.dbconfig.ConfigProperty

class ConfigPropertyService {

    def loadValues(String key, def value) {
		try {
			if (value?.toString() && !((value instanceof List) || (value instanceof Closure))) {
				ConfigProperty configProperty = ConfigProperty.findByKey(key)
				if(!configProperty){// && !value?.toString()?.contains("'")
					new ConfigProperty(key: key, value: value.toString(), description:"").save()
				}
			}
		} catch (Exception e) {
			log.warn "Exception ${e.message} for " + value + " key : ${key}"
		}
	}
}
