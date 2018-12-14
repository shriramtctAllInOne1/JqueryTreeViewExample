package com.jquery.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

;

public class PropertyUtility {
	public String sDriverName;
	public String url;
	public String sUserName;
	public String sPassword;
	Properties propertyObj;
	private static Logger LOGGER = LoggerFactory
			.getLogger(PropertyUtility.class);

	public PropertyUtility() {
		LOGGER.info("configure the log message");
	}

	public void loadProperty() {
		LOGGER.info("load the property File");
		propertyObj = new Properties();
		try {
			InputStream file = this.getClass().getResourceAsStream(
					"/db.properties");
			propertyObj.load(file);
			sDriverName = propertyObj.getProperty("driver.name");
			url = propertyObj.getProperty("url");
			sUserName = propertyObj.getProperty("dbUser.name");
			sPassword = propertyObj.getProperty("dbUser.password");
			LOGGER.info("drivername :" + sDriverName);
			LOGGER.info("url :" + url);
			LOGGER.info("sUserName :" + sUserName);
			LOGGER.info("sPassword :" + sPassword);
		} catch (FileNotFoundException e) {

			LOGGER.info("loadProperty:" + e.getMessage());
		} catch (IOException e) {
			LOGGER.info("loadProperty:" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		PropertyUtility obj = new PropertyUtility();
		obj.loadProperty();
	}
}
