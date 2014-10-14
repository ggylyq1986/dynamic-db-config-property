package com.dbconfig

import com.dbconfig.ConfigProperty

class ConfigPropertyService {

    def loadValues(String configKey, def value) {
		try {
			if (value?.toString() && !((value instanceof List) || (value instanceof Closure))) {
				ConfigProperty configProperty = ConfigProperty.findByConfigKey(configKey)
				if(!configProperty){// && !value?.toString()?.contains("'")
					new ConfigProperty(configKey: configKey, value: value.toString(), description:"").save()
				}
			}
		} catch (Exception e) {
			log.warn "Exception ${e.message} for " + value + " configKey : ${configKey}"
		}
	}
}
