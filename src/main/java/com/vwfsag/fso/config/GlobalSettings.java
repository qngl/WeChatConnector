package com.vwfsag.fso.config;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author qngl
 *
 */
@Component
public class GlobalSettings {

	private static final Logger log = LoggerFactory.getLogger(GlobalSettings.class);

	private Properties props;
	
	public GlobalSettings() {
		props = new Properties();
		String propFile = "global.properties";
		String env = System.getProperty("runtime.env");
		if(env != null) {
			propFile = String.format("%s-%s", env, propFile);
		}
		try {
			props.load(GlobalSettings.class.getResourceAsStream(propFile));
		} catch (IOException e) {
			log.error("Cannot load the GlobalSettings from the properties file: " + propFile, e);
		}
	}

	public Properties getProps() {
		return props;
	}

	public String get(String key) {
		return props.getProperty(key);
	}

}
