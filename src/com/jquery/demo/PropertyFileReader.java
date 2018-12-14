package com.jquery.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Class for Reading the values from property file.
 * @author 
 *
 */
public class PropertyFileReader {

	private static Properties props;
	public static final String PROPERTIES_FILE = "Demo.properties";
	private static final DemoLogger 
			LOGGER = DemoLogger.getLogger(PropertyFileReader.class);
	

	static {
		loadProperties();
	}

	public static void loadProperties() {
		LOGGER.debug("[PropertyUtil] Loading properties...");
		props = new Properties();
		File propsFile = new File(PROPERTIES_FILE);
		try {
			props.load(new FileInputStream(propsFile));
		} catch (FileNotFoundException e) {
			LOGGER.equals(e);
		} catch (IOException e) {
			LOGGER.equals(e);
		}
	}

	public static String getStringValue(String propertyKey){
		return props.getProperty(propertyKey);
	}

	public static int getIntValue(String propertyKey){
		return Integer.parseInt(props.getProperty(propertyKey));
	}

	
}

